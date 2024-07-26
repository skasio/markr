package com.stileeducation.markr.util;

import com.stileeducation.markr.entity.Test;

public class TestBuilder {
  private Long id;
  private String testId;
  private Integer marksAvailable;

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

  public Test build() {
    Test test = new Test();
    test.setId(id);
    test.setTestId(testId);
    test.setMarksAvailable(marksAvailable);
    return test;
  }
}
