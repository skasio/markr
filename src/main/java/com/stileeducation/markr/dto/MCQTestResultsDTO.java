package com.stileeducation.markr.dto;

import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "mcq-test-results")
public class MCQTestResultsDTO {

  @Valid
  private List<MCQTestResultDTO> mcqTestResults;

  @XmlElement(name = "mcq-test-result")
  public List<MCQTestResultDTO> getMcqTestResults() {
    return mcqTestResults;
  }

  public void setMcqTestResults(List<MCQTestResultDTO> mcqTestResults) {
    this.mcqTestResults = mcqTestResults;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MCQTestResultsDTO that = (MCQTestResultsDTO) o;
    return Objects.equals(mcqTestResults, that.mcqTestResults);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(mcqTestResults);
  }

  @Override
  public String toString() {
    return "MCQTestResultsDTO{" +
        "mcqTestResults=" + mcqTestResults +
        '}';
  }
}

