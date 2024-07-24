package com.stileeducation.markr.util;

import com.stileeducation.markr.entity.Student;
import com.stileeducation.markr.entity.TestResult;

import java.util.HashSet;
import java.util.Set;

public class StudentBuilder {
  private Long id;
  private String firstName;
  private String lastName;
  private String studentNumber;
  private Set<TestResult> testResults = new HashSet<>();

  public StudentBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public StudentBuilder withFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public StudentBuilder withLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public StudentBuilder withStudentNumber(String studentNumber) {
    this.studentNumber = studentNumber;
    return this;
  }

  public StudentBuilder withTestResults(Set<TestResult> testResults) {
    this.testResults = testResults;
    return this;
  }

  public Student build() {
    Student student = new Student();
    student.setId(id);
    student.setFirstName(firstName);
    student.setLastName(lastName);
    student.setStudentNumber(studentNumber);
    student.setTestResults(testResults);
    return student;
  }
}