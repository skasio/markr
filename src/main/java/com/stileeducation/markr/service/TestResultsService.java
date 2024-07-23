package com.stileeducation.markr.service;

import com.stileeducation.markr.dto.AggregatedTestResultsDTO;
import com.stileeducation.markr.dto.MCQTestResultsDTO;

public interface TestResultsService {
  MCQTestResultsDTO importTestResults(MCQTestResultsDTO mcqTestResults);

  AggregatedTestResultsDTO aggregateTestResults(String testId);
}
