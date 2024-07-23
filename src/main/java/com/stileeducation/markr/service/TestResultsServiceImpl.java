package com.stileeducation.markr.service;

import com.stileeducation.markr.dto.MCQTestResultsDTO;
import org.springframework.stereotype.Service;

@Service
public class TestResultsServiceImpl implements TestResultsService {

  @Override
  public MCQTestResultsDTO importTestResults(MCQTestResultsDTO testResults) {
    System.out.println(testResults);
    return testResults;
  }

}