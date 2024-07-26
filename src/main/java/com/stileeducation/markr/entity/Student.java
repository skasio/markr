package com.stileeducation.markr.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "students")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "student_number", nullable = false, unique = true)
  private String studentNumber;

  @Transient
  private boolean created = false;

  @Transient
  private boolean updated = false;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getStudentNumber() {
    return studentNumber;
  }

  public void setStudentNumber(String studentNumber) {
    this.studentNumber = studentNumber;
  }

  public boolean isCreated() {
    return created;
  }

  public void setCreated(boolean created) {
    this.created = created;
  }

  public boolean isUpdated() {
    return updated;
  }

  public void setUpdated(boolean updated) {
    this.updated = updated;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Student student = (Student) o;
    return Objects.equals(id, student.id) &&
        Objects.equals(firstName, student.firstName) &&
        Objects.equals(lastName, student.lastName) &&
        Objects.equals(studentNumber, student.studentNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, studentNumber);
  }

  @Override
  public String toString() {
    return "Student{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", studentNumber='" + studentNumber + '\'' +
        ", created=" + created +
        ", updated=" + updated +
        '}';
  }
}