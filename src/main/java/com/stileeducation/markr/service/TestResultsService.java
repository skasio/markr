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
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public class TestResultsService {

  public static final boolean IS_BIAS_CORRECTED = false;

  private final TestResultRepository testResultRepository;

  private final StudentService studentService;

  private final TestService testService;

  public TestResultsService(TestResultRepository testResultRepository, StudentService studentService, TestService testService) {
    this.testResultRepository = testResultRepository;
    this.studentService = studentService;
    this.testService = testService;
  }

  private static double[] calculatePercentages(List<TestResult> results, double totalMarksAvailable) {
    return results.stream()
        .mapToDouble(result -> (double) result.getMarksAwarded() / totalMarksAvailable * 100)
        .toArray();
  }

  public AggregateResponseDTO aggregateTestResults(String testId) {
    AggregateResponseDTO aggregateResponseDTO = new AggregateResponseDTO();

    testService.findTest(testId).ifPresent(test -> {
      List<TestResult> testResults = findAllByTestId(testId);
      if (!testResults.isEmpty()) {
        populateAggregateResponse(aggregateResponseDTO, test, testResults);
      }
    });

    return aggregateResponseDTO;
  }

  public ImportResponseDTO processTestResults(MCQTestResultsDTO testResults) {
    ImportResponseDTO.ImportData importData = new ImportResponseDTO.ImportData();
    boolean isValid = true;

    for (MCQTestResultDTO mcqTestResult : testResults.getMcqTestResults()) {
      try {
        processTestResult(mcqTestResult, importData);
      } catch (Exception e) {
        isValid = false;
      }
    }

    return createImportResponse(importData, isValid);
  }

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

  public double calculateMeanOfTestResults(Test test, List<TestResult> results) {
    double totalMarksAvailable = test.getMarksAvailable();

    if (totalMarksAvailable <= 0) {
      return 0.0;
    }

    OptionalDouble meanPercentage = Arrays.stream(calculatePercentages(results, totalMarksAvailable)).average();

    return meanPercentage.orElse(0.0);
  }

  public double calculateMinOfTestResults(Test test, List<TestResult> results) {
    double totalMarksAvailable = test.getMarksAvailable();

    if (totalMarksAvailable <= 0) {
      return 0.0;
    }

    OptionalDouble minPercentage = Arrays.stream(calculatePercentages(results, totalMarksAvailable)).min();

    return minPercentage.orElse(0.0);
  }

  public double calculateMaxOfTestResults(Test test, List<TestResult> results) {
    double totalMarksAvailable = test.getMarksAvailable();

    if (totalMarksAvailable <= 0) {
      return 0.0;
    }

    OptionalDouble maxPercentage = Arrays.stream(calculatePercentages(results, totalMarksAvailable)).max();

    return maxPercentage.orElse(0.0);
  }

  public double calculateStandardDeviationOfTestResults(Test test, List<TestResult> results) {
    double totalMarksAvailable = test.getMarksAvailable();

    if (totalMarksAvailable <= 0) {
      return 0.0;
    }

    StandardDeviation standardDeviation = new StandardDeviation(IS_BIAS_CORRECTED);
    return standardDeviation.evaluate(calculatePercentages(results, totalMarksAvailable));
  }

  public double calculate25thPercentile(Test test, List<TestResult> results) {
    double totalMarksAvailable = test.getMarksAvailable();

    if (totalMarksAvailable <= 0) {
      return 0.0;
    }

    return new Percentile().evaluate(calculatePercentages(results, totalMarksAvailable), 25.0);
  }

  public double calculate50thPercentile(Test test, List<TestResult> results) {
    double totalMarksAvailable = test.getMarksAvailable();

    if (totalMarksAvailable <= 0) {
      return 0.0;
    }

    return new Percentile().evaluate(calculatePercentages(results, totalMarksAvailable), 50.0);
  }

  public double calculate75thPercentile(Test test, List<TestResult> results) {
    double totalMarksAvailable = test.getMarksAvailable();

    if (totalMarksAvailable <= 0) {
      return 0.0;
    }

    double[] percentages = calculatePercentages(results, totalMarksAvailable);

    return new Percentile().evaluate(percentages, 75.0);
  }

  private void populateAggregateResponse(AggregateResponseDTO dto, Test test, List<TestResult> results) {
    dto.setMean(calculateMeanOfTestResults(test, results));
    dto.setStddev(calculateStandardDeviationOfTestResults(test, results));
    dto.setMin(calculateMinOfTestResults(test, results));
    dto.setMax(calculateMaxOfTestResults(test, results));
    dto.setP25(calculate25thPercentile(test, results));
    dto.setP50(calculate50thPercentile(test, results));
    dto.setP75(calculate75thPercentile(test, results));
    dto.setCount(results.size());
  }

  private void processTestResult(MCQTestResultDTO mcqTestResult, ImportResponseDTO.ImportData importData) {
    Student student =
        studentService
            .findOrCreateStudent(
                mcqTestResult.getFirstName(),
                mcqTestResult.getLastName(),
                mcqTestResult.getStudentNumber());

    incrementIfTrue(student.isCreated(), importData::incrementStudentsCreated);
    incrementIfTrue(student.isUpdated(), importData::incrementStudentsUpdated);

    Test test =
        testService
            .findOrCreateTest(
                mcqTestResult.getTestId(),
                mcqTestResult.getSummaryMarks().getAvailable());

    incrementIfTrue(test.isCreated(), importData::incrementTestsCreated);
    incrementIfTrue(test.isUpdated(), importData::incrementTestsUpdated);

    TestResult testResult =
        findOrCreateTestResult(
            student,
            test,
            mcqTestResult.getSummaryMarks().getObtained());

    incrementIfTrue(testResult.isCreated(), importData::incrementTestResultsCreated);
    incrementIfTrue(testResult.isUpdated(), importData::incrementTestResultsUpdated);
  }

  private void incrementIfTrue(boolean condition, Runnable action) {
    if (condition) {
      action.run();
    }
  }

  private ImportResponseDTO createImportResponse(ImportResponseDTO.ImportData importData, boolean isValid) {
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