package com.stileeducation.markr.controller;

import com.stileeducation.markr.dto.AggregatedTestResultsDTO;
import com.stileeducation.markr.dto.MCQTestResultDTO;
import com.stileeducation.markr.dto.MCQTestResultsDTO;
import com.stileeducation.markr.entity.Student;
import com.stileeducation.markr.entity.Test;
import com.stileeducation.markr.entity.TestResult;
import com.stileeducation.markr.repository.TestRepository;
import com.stileeducation.markr.repository.TestResultRepository;
import com.stileeducation.markr.service.StudentService;
import com.stileeducation.markr.service.TestResultsService;
import com.stileeducation.markr.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class TestResultsController {

    public static final String IMPORT_ENDPOINT = "/import";
    public static final String AGGREGATE_ENDPOINT = "/results/{test-id}/aggregate";

    @Autowired
    private StudentService studentService;

    @Autowired
    private TestService testService;

    @Autowired
    private TestResultsService testResultsService;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    public TestResultsController(TestResultsService testResultsService) {
        this.testResultsService = testResultsService;
    }

    // TODO consider return value
    @PostMapping(
            value = IMPORT_ENDPOINT,
            consumes = "text/xml+markr",
            produces = "application/json")
    public ResponseEntity<Void> handleXmlRequest(@RequestBody MCQTestResultsDTO testResults) {

        for (MCQTestResultDTO mcqTestResult : testResults.getMcqTestResults()) {
            Student student = studentService
                    .findOrCreateStudent(
                            mcqTestResult.getFirstName(),
                            mcqTestResult.getLastName(),
                            mcqTestResult.getStudentNumber());

            Test test = testService
                    .findOrCreateTest(
                            mcqTestResult.getTestId(),
                            mcqTestResult.getSummaryMarks().getAvailable());

            if (test.getMarksAvailable() < mcqTestResult.getSummaryMarks().getAvailable()) {
                test.setMarksAvailable(mcqTestResult.getSummaryMarks().getAvailable());
                testRepository.save(test);
            }

            // Some edge cases to consider
            // obtained is higher than available (assumption?)

            TestResult testResult = testResultsService
                    .findOrCreateTestResult(
                            student.getId(),
                            test.getId(),
                            mcqTestResult.getSummaryMarks().getObtained());

            if (testResult.getMarksAwarded() < mcqTestResult.getSummaryMarks().getObtained()) {
                testResult.setMarksAwarded(mcqTestResult.getSummaryMarks().getObtained());
                testResultRepository.save(testResult);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(
            value = AGGREGATE_ENDPOINT,
            produces = "application/json")
    public AggregatedTestResultsDTO getAggregatedResults(@PathVariable("test-id") String testId) {
        return testResultsService.aggregateTestResults(testId);
    }

}
