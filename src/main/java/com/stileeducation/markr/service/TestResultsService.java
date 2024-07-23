package com.stileeducation.markr.service;

import com.stileeducation.markr.dto.AggregatedTestResultsDTO;
import com.stileeducation.markr.entity.Student;
import com.stileeducation.markr.entity.Test;
import com.stileeducation.markr.entity.TestResult;
import com.stileeducation.markr.repository.StudentRepository;
import com.stileeducation.markr.repository.TestRepository;
import com.stileeducation.markr.repository.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestResultsService {

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

  public double calculateStandardDeviationOfTestResults(List<TestResult> results, double mean) {
    if (results.isEmpty()) {
      return 0.0; // or throw an exception if no results are found
    }

    // Calculate the variance
    double variance = results.stream()
        .mapToDouble(result -> Math.pow(result.getMarksAwarded() - mean, 2))
        .average()
        .orElse(0.0);

    // Return the standard deviation
    return Math.sqrt(variance);
  }

  public double calculatePercentile(List<Integer> sortedMarks, double percentile) {
    if (sortedMarks.isEmpty()) {
      return 0.0;
    }
    int index = (int) Math.floor(percentile / 100.0 * sortedMarks.size()) - 1;
    return sortedMarks.get(Math.min(index, sortedMarks.size() - 1));
  }

  public List<Integer> getSortedMarks(List<TestResult> testResults) {
    return testResults.stream()
        .map(TestResult::getMarksAwarded)
        .sorted()
        .collect(Collectors.toList());
  }

  public double calculate25thPercentile(List<Integer> sortedMarks) {
    return calculatePercentile(sortedMarks, 25.0);
  }

  public double calculate50thPercentile(List<Integer> sortedMarks) {
    return calculatePercentile(sortedMarks, 50.0);
  }

  public double calculate75thPercentile(List<Integer> sortedMarks) {
    return calculatePercentile(sortedMarks, 75.0);
  }

  public AggregatedTestResultsDTO aggregateTestResults(String testId) {
    List<TestResult> testResults = findAllByTestId(testId);
    List<Integer> sortedMarks = getSortedMarks(testResults);

    AggregatedTestResultsDTO results = new AggregatedTestResultsDTO();

    results.setMean(calculateMeanOfTestResults(testResults));

    results.setStddev(calculateStandardDeviationOfTestResults(testResults, results.getMean()));

    results.setMin(calculateMinOfTestResults(testResults));

    results.setMax(calculateMaxOfTestResults(testResults));

    results.setP25(calculate25thPercentile(sortedMarks));

    results.setP50(calculate50thPercentile(sortedMarks));

    results.setP75(calculate75thPercentile(sortedMarks));

    results.setCount(testResults.size());

    return results;
  }

}