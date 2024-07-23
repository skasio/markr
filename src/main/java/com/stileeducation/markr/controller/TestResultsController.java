package com.stileeducation.markr.controller;

import com.stileeducation.markr.dto.AggregatedTestResultsDTO;
import com.stileeducation.markr.dto.MCQTestResultsDTO;
import com.stileeducation.markr.service.TestResultsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class TestResultsController {

  public static final String IMPORT_ENDPOINT = "/import";
  public static final String AGGREGATE_ENDPOINT = "/results/{test-id}/aggregate";

  private final TestResultsService testResultsService;

  public TestResultsController(TestResultsService testResultsService) {
    this.testResultsService = testResultsService;
  }

  // TODO consider return value
  @PostMapping(value = IMPORT_ENDPOINT, consumes = "text/xml+markr", produces = "application/json")
  public ResponseEntity<Void> handleXmlRequest(@RequestBody MCQTestResultsDTO testResults) {
    testResultsService.importTestResults(testResults);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(value = AGGREGATE_ENDPOINT, produces = "application/json")
  public AggregatedTestResultsDTO getAggregatedResults(@PathVariable("test-id") String testId) {
    return testResultsService.aggregateTestResults(testId);
  }

}
