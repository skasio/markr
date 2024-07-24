package com.stileeducation.markr.service;

import com.stileeducation.markr.dto.AggregatedTestResultsDTO;
import com.stileeducation.markr.entity.Student;
import com.stileeducation.markr.entity.Test;
import com.stileeducation.markr.entity.TestResult;
import com.stileeducation.markr.repository.StudentRepository;
import com.stileeducation.markr.repository.TestRepository;
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
  private StudentRepository studentRepository;

  @Autowired
  private TestRepository testRepository;

  public TestResult findOrCreateTestResult(Long studentId, Long testId, Integer marksAwarded) {
    Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
    Test test = testRepository.findById(testId).orElseThrow(() -> new RuntimeException("Test not found"));

    Optional<TestResult> optionalTestResult = testResultRepository.findByStudentAndTest(student, test);
    if (optionalTestResult.isPresent()) {
      return optionalTestResult.get();
    } else {
      TestResult testResult = new TestResult();
      testResult.setStudent(student);
      testResult.setTest(test);
      testResult.setMarksAwarded(marksAwarded);
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

  public AggregatedTestResultsDTO aggregateTestResults(String testId) {
    List<TestResult> testResults = findAllByTestId(testId);

    AggregatedTestResultsDTO results = new AggregatedTestResultsDTO();

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

}