package com.stileeducation.markr.dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Objects;

@XmlRootElement(name = "mcq-test-result")
public class MCQTestResultDTO {

  private String scannedOn;
  private String firstName;
  private String lastName;
  private String studentNumber;
  private String testId;
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
