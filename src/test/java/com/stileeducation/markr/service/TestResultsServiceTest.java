package com.stileeducation.markr.service;

import com.stileeducation.markr.entity.Student;
import com.stileeducation.markr.entity.TestResult;
import com.stileeducation.markr.repository.StudentRepository;
import com.stileeducation.markr.repository.TestRepository;
import com.stileeducation.markr.repository.TestResultRepository;
import com.stileeducation.markr.util.StudentBuilder;
import com.stileeducation.markr.util.TestBuilder;
import com.stileeducation.markr.util.TestResultBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TestResultsServiceTest {

  private static Student student1;
  private static Student student2;
  private static Student student3;
  private static Student student4;

  private static com.stileeducation.markr.entity.Test test1;

  private static TestResult testResult1;
  private static TestResult testResult2;
  private static TestResult testResult3;
  private static TestResult testResult4;
  private static TestResult testResult5;
  private static TestResult testResult6;

  private static List<TestResult> test1Results;

  @Mock
  private TestResultRepository testResultRepository;

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private TestRepository testRepository;

  @InjectMocks
  private TestResultsService testResultsService;

  @BeforeAll
  static void setupTestData() {
    student1 = new StudentBuilder()
        .withId(1L)
        .withStudentNumber("555123")
        .withFirstName("Emily")
        .withLastName("Clark")
        .build();

    student2 = new StudentBuilder()
        .withId(2L)
        .withStudentNumber("555456")
        .withFirstName("James")
        .withLastName("Taylor")
        .build();

    student3 = new StudentBuilder()
        .withId(3L)
        .withStudentNumber("555789")
        .withFirstName("Sophia")
        .withLastName("Martinez")
        .build();

    student4 = new StudentBuilder()
        .withId(4L)
        .withStudentNumber("555012")
        .withFirstName("Liam")
        .withLastName("Anderson")
        .build();

    test1 = new TestBuilder()
        .withId(1L)
        .withTestId("1234")
        .withMarksAvailable(100)
        .build();

    testResult1 = new TestResultBuilder()
        .withStudent(student1)
        .withTest(test1)
        .withMarksAwarded(10)
        .build();

    testResult2 = new TestResultBuilder()
        .withStudent(student2)
        .withTest(test1)
        .withMarksAwarded(20)
        .build();

    testResult3 = new TestResultBuilder()
        .withStudent(student3)
        .withTest(test1)
        .withMarksAwarded(40)
        .build();

    testResult4 = new TestResultBuilder()
        .withStudent(student4)
        .withTest(test1)
        .withMarksAwarded(50)
        .build();

    testResult5 = new TestResultBuilder()
        .withStudent(student3)
        .withTest(test1)
        .withMarksAwarded(70)
        .build();

    testResult6 = new TestResultBuilder()
        .withStudent(student4)
        .withTest(test1)
        .withMarksAwarded(80)
        .build();

    test1Results = List.of(testResult1, testResult2, testResult3, testResult4, testResult5, testResult6);
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void calculateMeanOfTestResults() {
    double mean = testResultsService.calculateMeanOfTestResults(test1, test1Results);
    assertEquals(45.0, mean, 0.01);
  }

  @Test
  void calculateMinOfTestResults() {
    double min = testResultsService.calculateMinOfTestResults(test1, test1Results);
    assertEquals(10.0, min, 0.01);
  }

  @Test
  void calculateMaxOfTestResults() {
    double max = testResultsService.calculateMaxOfTestResults(test1, test1Results);
    assertEquals(80.0, max, 0.01);
  }

  @Test
  void calculateStandardDeviationOfTestResults() {
    double standardDeviationOfTestResults = testResultsService.calculateStandardDeviationOfTestResults(test1, test1Results);
    assertEquals(25.0, standardDeviationOfTestResults, 0.01);
  }

  @Test
  void calculate25thPercentile() {
    double percentile = testResultsService.calculate25thPercentile(test1, test1Results);
    assertEquals(17.5, percentile, 0.01);
  }

  @Test
  void calculate50thPercentile() {
    double percentile = testResultsService.calculate50thPercentile(test1, test1Results);
    assertEquals(45.0, percentile, 0.01);
  }

  @Test
  void calculate75thPercentile() {
    double percentile = testResultsService.calculate75thPercentile(test1, test1Results);
    assertEquals(72.5, percentile, 0.01);
  }

}