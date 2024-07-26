package com.stileeducation.markr.util;

import com.stileeducation.markr.entity.Student;

public class StudentBuilder {
  private Long id;
  private String firstName;
  private String lastName;
  private String studentNumber;


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

  public Student build() {
    Student student = new Student();
    student.setId(id);
    student.setFirstName(firstName);
    student.setLastName(lastName);
    student.setStudentNumber(studentNumber);
    return student;
  }
}