package com.stileeducation.markr.controller;

import com.stileeducation.markr.dto.MCQTestResultsDTO;
import com.stileeducation.markr.service.TestResultsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestResultsController {

  public static final String IMPORT_ENDPOINT = "/import";

  private final TestResultsService testResultsService;

  public TestResultsController(TestResultsService testResultsService) {
    this.testResultsService = testResultsService;
  }

  // TODO - update to consume text/xml+markr, consider return value
  @PostMapping(value = IMPORT_ENDPOINT, consumes = "application/xml", produces = "application/json")
  public ResponseEntity<Void> handleXmlRequest(@RequestBody MCQTestResultsDTO testResults) {
    testResultsService.importTestResults(testResults);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
