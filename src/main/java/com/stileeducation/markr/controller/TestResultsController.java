package com.stileeducation.markr.controller;

import com.stileeducation.markr.dto.AggregateResponseDTO;
import com.stileeducation.markr.dto.ImportResponseDTO;
import com.stileeducation.markr.dto.MCQTestResultsDTO;
import com.stileeducation.markr.service.TestResultsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

  @PostMapping(value = IMPORT_ENDPOINT, consumes = "text/xml+markr", produces = "application/json")
  public ResponseEntity<ImportResponseDTO> postTestResults(@Validated @RequestBody MCQTestResultsDTO testResults) {
    ImportResponseDTO response = testResultsService.processTestResults(testResults);
    if ("failure".equals(response.getStatus())) {
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping(value = AGGREGATE_ENDPOINT, produces = "application/json")
  public AggregateResponseDTO getAggregatedResults(@PathVariable("test-id") String testId) {
    return testResultsService.aggregateTestResults(testId);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ImportResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    ImportResponseDTO response = new ImportResponseDTO();
    response.setStatus("error");
    response.setMessage("Invalid payload");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ImportResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    ImportResponseDTO response = new ImportResponseDTO();
    response.setStatus("error");
    response.setMessage("Invalid XML payload");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ImportResponseDTO> handleGenericException(Exception ex) {
    ImportResponseDTO response = new ImportResponseDTO();
    response.setStatus("error");
    response.setMessage("An unexpected error occurred");
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
