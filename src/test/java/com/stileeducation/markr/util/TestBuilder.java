package com.stileeducation.markr.util;

import com.stileeducation.markr.entity.Test;
import com.stileeducation.markr.entity.TestResult;

import java.util.HashSet;
import java.util.Set;

public class TestBuilder {
  private Long id;
  private String testId;
  private Integer marksAvailable;
  private Set<TestResult> testResults = new HashSet<>();

  public TestBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public TestBuilder withTestId(String testId) {
    this.testId = testId;
    return this;
  }

  public TestBuilder withMarksAvailable(Integer marksAvailable) {
    this.marksAvailable = marksAvailable;
    return this;
  }

  public TestBuilder withTestResults(Set<TestResult> testResults) {
    this.testResults = testResults;
    return this;
  }

  public Test build() {
    Test test = new Test();
    test.setId(id);
    test.setTestId(testId);
    test.setMarksAvailable(marksAvailable);
    test.setTestResults(testResults);
    return test;
  }
}
