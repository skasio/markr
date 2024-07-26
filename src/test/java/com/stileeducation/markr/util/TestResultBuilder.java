package com.stileeducation.markr.util;

import com.stileeducation.markr.entity.Student;
import com.stileeducation.markr.entity.Test;
import com.stileeducation.markr.entity.TestResult;

public class TestResultBuilder {
  private Long id;
  private Student student;
  private Test test;
  private Integer marksObtained;

  public TestResultBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public TestResultBuilder withStudent(Student student) {
    this.student = student;
    return this;
  }

  public TestResultBuilder withTest(Test test) {
    this.test = test;
    return this;
  }

  public TestResultBuilder withMarksObtained(Integer marksObtained) {
    this.marksObtained = marksObtained;
    return this;
  }

  public TestResult build() {
    return new TestResult(id, student, test, marksObtained);
  }
}