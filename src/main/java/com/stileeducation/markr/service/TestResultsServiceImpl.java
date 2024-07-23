package com.stileeducation.markr.service;

import com.stileeducation.markr.dto.AggregatedTestResultsDTO;
import com.stileeducation.markr.dto.MCQTestResultsDTO;
import org.springframework.stereotype.Service;

@Service
public class TestResultsServiceImpl implements TestResultsService {

  @Override
  public MCQTestResultsDTO importTestResults(MCQTestResultsDTO testResults) {
    return testResults;
  }

  @Override
  public AggregatedTestResultsDTO aggregateTestResults(String testId) {
    AggregatedTestResultsDTO results = new AggregatedTestResultsDTO();
    results.setMean(65.0);
    results.setStddev(0.0);
    results.setMin(65.0);
    results.setMax(65.0);
    results.setP25(65.0);
    results.setP50(65.0);
    results.setP75(65.0);
    results.setCount(1);
    return results;
  }

}