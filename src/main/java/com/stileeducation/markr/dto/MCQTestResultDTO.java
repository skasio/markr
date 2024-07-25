package com.stileeducation.markr.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Objects;

@XmlRootElement(name = "mcq-test-result")
public class MCQTestResultDTO {

  @NotBlank(message = "scanned-on is mandatory")
  private String scannedOn;

  @NotBlank(message = "first-name is mandatory")
  private String firstName;

  @NotBlank(message = "last-name is mandatory")
  private String lastName;

  @NotBlank(message = "student-number is mandatory")
  private String studentNumber;

  @NotBlank(message = "test-id is mandatory")
  private String testId;

  @Valid
  @NotNull(message = "summary-marks is mandatory")
  private SummaryMarksDTO summaryMarks;

  @XmlAttribute(name = "scanned-on")
  public String getScannedOn() {
    return scannedOn;
  }

  public void setScannedOn(String scannedOn) {
    this.scannedOn = scannedOn;
  }

  @XmlElement(name = "first-name")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @XmlElement(name = "last-name")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @XmlElement(name = "student-number")
  public String getStudentNumber() {
    return studentNumber;
  }

  public void setStudentNumber(String studentNumber) {
    this.studentNumber = studentNumber;
  }

  @XmlElement(name = "test-id")
  public String getTestId() {
    return testId;
  }

  public void setTestId(String testId) {
    this.testId = testId;
  }

  @XmlElement(name = "summary-marks")
  public SummaryMarksDTO getSummaryMarks() {
    return summaryMarks;
  }

  public void setSummaryMarks(SummaryMarksDTO summaryMarks) {
    this.summaryMarks = summaryMarks;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MCQTestResultDTO that = (MCQTestResultDTO) o;
    return Objects.equals(scannedOn, that.scannedOn)
        && Objects.equals(firstName, that.firstName)
        && Objects.equals(lastName, that.lastName)
        && Objects.equals(studentNumber, that.studentNumber)
        && Objects.equals(testId, that.testId)
        && Objects.equals(summaryMarks, that.summaryMarks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scannedOn, firstName, lastName, studentNumber, testId, summaryMarks);
  }

  @Override
  public String toString() {
    return "MCQTestResultDTO{" +
        "scannedOn='" + scannedOn + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", studentNumber='" + studentNumber + '\'' +
        ", testId='" + testId + '\'' +
        ", summaryMarks=" + summaryMarks +
        '}';
  }
}
