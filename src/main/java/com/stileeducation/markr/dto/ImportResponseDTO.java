package com.stileeducation.markr.dto;

import java.util.Objects;

public class ImportResponseDTO {

  private String status;
  private String message;
  private ImportData data;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ImportData getData() {
    return data;
  }

  public void setData(ImportData data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ImportResponseDTO that = (ImportResponseDTO) o;
    return Objects.equals(status, that.status) &&
        Objects.equals(message, that.message) &&
        Objects.equals(data, that.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, message, data);
  }

  @Override
  public String toString() {
    return "ImportResponseDTO{" +
        "status='" + status + '\'' +
        ", message='" + message + '\'' +
        ", data=" + data +
        '}';
  }

  public static class ImportData {
    private int studentsCreated = 0;
    private int studentsUpdated = 0;

    private int testsCreated = 0;
    private int testsUpdated = 0;

    private int testResultsCreated = 0;
    private int testResultsUpdated = 0;

    public int getStudentsCreated() {
      return studentsCreated;
    }

    public void setStudentsCreated(int studentsCreated) {
      this.studentsCreated = studentsCreated;
    }

    public int getStudentsUpdated() {
      return studentsUpdated;
    }

    public void setStudentsUpdated(int studentsUpdated) {
      this.studentsUpdated = studentsUpdated;
    }

    public int getTestsCreated() {
      return testsCreated;
    }

    public void setTestsCreated(int testsCreated) {
      this.testsCreated = testsCreated;
    }

    public int getTestsUpdated() {
      return testsUpdated;
    }

    public void setTestsUpdated(int testsUpdated) {
      this.testsUpdated = testsUpdated;
    }

    public int getTestResultsCreated() {
      return testResultsCreated;
    }

    public void setTestResultsCreated(int testResultsCreated) {
      this.testResultsCreated = testResultsCreated;
    }

    public int getTestResultsUpdated() {
      return testResultsUpdated;
    }

    public void setTestResultsUpdated(int testResultsUpdated) {
      this.testResultsUpdated = testResultsUpdated;
    }

    public void incrementStudentsCreated() {
      this.studentsCreated++;
    }

    public void incrementStudentsUpdated() {
      this.studentsUpdated++;
    }

    public void incrementTestsCreated() {
      this.testsCreated++;
    }

    public void incrementTestsUpdated() {
      this.testsUpdated++;
    }

    public void incrementTestResultsCreated() {
      this.testResultsCreated++;
    }

    public void incrementTestResultsUpdated() {
      this.testResultsUpdated++;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ImportData that = (ImportData) o;
      return studentsCreated == that.studentsCreated &&
          studentsUpdated == that.studentsUpdated &&
          testsCreated == that.testsCreated &&
          testsUpdated == that.testsUpdated &&
          testResultsCreated == that.testResultsCreated &&
          testResultsUpdated == that.testResultsUpdated;
    }

    @Override
    public int hashCode() {
      return Objects.hash(studentsCreated, studentsUpdated, testsCreated, testsUpdated, testResultsCreated, testResultsUpdated);
    }

    @Override
    public String toString() {
      return "ImportData{" +
          "studentsCreated=" + studentsCreated +
          ", studentsUpdated=" + studentsUpdated +
          ", testsCreated=" + testsCreated +
          ", testsUpdated=" + testsUpdated +
          ", testResultsCreated=" + testResultsCreated +
          ", testResultsUpdated=" + testResultsUpdated +
          '}';
    }
  }
}
