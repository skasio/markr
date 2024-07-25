package com.stileeducation.markr.service;

import com.stileeducation.markr.dto.AggregateResponseDTO;
import com.stileeducation.markr.dto.ImportResponseDTO;
import com.stileeducation.markr.dto.MCQTestResultDTO;
import com.stileeducation.markr.dto.MCQTestResultsDTO;
import com.stileeducation.markr.entity.Student;
import com.stileeducation.markr.entity.Test;
import com.stileeducation.markr.entity.TestResult;
import com.stileeducation.markr.repository.TestResultRepository;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestResultsService {

  public static final boolean IS_BIAS_CORRECTED = false;

  @Autowired
  private TestResultRepository testResultRepository;

  @Autowired
  private StudentService studentService;

  @Autowired
  private TestService testService;

  public TestResult findOrCreateTestResult(Student student, Test test, Integer marksAwarded) {
    Optional<TestResult> optionalTestResult = testResultRepository.findByStudentAndTest(student, test);
    if (optionalTestResult.isPresent()) {
      TestResult testResult = optionalTestResult.get();
      if (marksAwarded > testResult.getMarksAwarded()) {
        testResult.setMarksAwarded(marksAwarded);
        testResult.setUpdated(true);
        testResultRepository.save(testResult);
      } else {
        testResult.setUpdated(false);
      }
      return testResult;
    } else {
      TestResult testResult = new TestResult();
      testResult.setStudent(student);
      testResult.setTest(test);
      testResult.setMarksAwarded(marksAwarded);
      testResult.setCreated(true);
      return testResultRepository.save(testResult);
    }
  }

  public List<TestResult> findAllByTestId(String testId) {
    return testResultRepository.findAllByTestId(testId);
  }

  public double calculateMeanOfTestResults(List<TestResult> results) {
    if (results.isEmpty()) {
      return 0.0;
    }
    return results.stream()
        .mapToInt(TestResult::getMarksAwarded)
        .average()
        .orElse(0.0);
  }

  public double calculateMinOfTestResults(List<TestResult> results) {
    if (results.isEmpty()) {
      return 0.0;
    }
    return results
        .stream()
        .mapToDouble(TestResult::getMarksAwarded)
        .min()
        .orElse(0.0);
  }

  public double calculateMaxOfTestResults(List<TestResult> results) {
    if (results.isEmpty()) {
      return 0.0;
    }
    return results
        .stream()
        .mapToDouble(TestResult::getMarksAwarded)
        .max()
        .orElse(0.0);
  }

  public double calculateStandardDeviationOfTestResults(List<TestResult> results) {
    if (results.isEmpty()) {
      return 0.0;
    }
    double[] marks =
        results.stream()
            .mapToDouble(TestResult::getMarksAwarded)
            .toArray();

    StandardDeviation standardDeviation = new StandardDeviation(IS_BIAS_CORRECTED);
    return standardDeviation.evaluate(marks);
  }

  public double calculate25thPercentile(List<TestResult> results) {
    if (results.isEmpty()) {
      return 0.0;
    }
    double[] marks =
        results.stream()
            .mapToDouble(TestResult::getMarksAwarded)
            .toArray();
    return new Percentile().evaluate(marks, 25.0);
  }

  public double calculate50thPercentile(List<TestResult> results) {
    if (results.isEmpty()) {
      return 0.0;
    }
    double[] marks =
        results.stream()
            .mapToDouble(TestResult::getMarksAwarded)
            .toArray();
    return new Percentile().evaluate(marks, 50.0);
  }

  public double calculate75thPercentile(List<TestResult> results) {
    if (results.isEmpty()) {
      return 0.0;
    }
    double[] marks =
        results.stream()
            .mapToDouble(TestResult::getMarksAwarded)
            .toArray();
    return new Percentile().evaluate(marks, 75.0);
  }

  public AggregateResponseDTO aggregateTestResults(String testId) {
    List<TestResult> testResults = findAllByTestId(testId);

    AggregateResponseDTO results = new AggregateResponseDTO();

    results.setMean(calculateMeanOfTestResults(testResults));
    results.setStddev(calculateStandardDeviationOfTestResults(testResults));
    results.setMin(calculateMinOfTestResults(testResults));
    results.setMax(calculateMaxOfTestResults(testResults));
    results.setP25(calculate25thPercentile(testResults));
    results.setP50(calculate50thPercentile(testResults));
    results.setP75(calculate75thPercentile(testResults));
    results.setCount(testResults.size());

    return results;
  }

  public ImportResponseDTO processTestResults(MCQTestResultsDTO testResults) {
    ImportResponseDTO.ImportData importData = new ImportResponseDTO.ImportData();
    boolean isValid = true;

    for (MCQTestResultDTO mcqTestResult : testResults.getMcqTestResults()) {
      try {

        Student student = studentService
            .findOrCreateStudent(
                mcqTestResult.getFirstName(),
                mcqTestResult.getLastName(),
                mcqTestResult.getStudentNumber());

        if (student.isCreated()) {
          importData.incrementStudentsCreated();
        }
        if (student.isUpdated()) {
          importData.incrementStudentsUpdated();
        }

        Test test = testService
            .findOrCreateTest(
                mcqTestResult.getTestId(),
                mcqTestResult.getSummaryMarks().getAvailable());

        if (test.isCreated()) {
          importData.incrementTestsCreated();
        }
        if (test.isUpdated()) {
          importData.incrementTestsUpdated();
        }

        TestResult testResult =
            findOrCreateTestResult(
                student,
                test,
                mcqTestResult.getSummaryMarks().getObtained());

        if (testResult.isCreated()) {
          importData.incrementTestResultsCreated();
        }
        if (testResult.isUpdated()) {
          importData.incrementTestResultsUpdated();
        }
      } catch (Exception e) {
        isValid = false;
      }
    }

    ImportResponseDTO response = new ImportResponseDTO();
    response.setData(importData);

    if (isValid) {
      response.setStatus("success");
      response.setMessage("Import operation completed successfully.");
    } else {
      response.setStatus("failure");
      response.setMessage("Data was invalid or processing failed.");
    }

    return response;
  }
}