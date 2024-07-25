package com.stileeducation.markr.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Objects;

@XmlRootElement(name = "summary-marks")
public class SummaryMarksDTO {

  @NotNull(message = "summary-marks available must not be null")
  @Min(value = 0, message = "summary-marks available must be non-negative")
  private Integer available;

  @NotNull(message = "summary-marks obtained must not be null")
  @Min(value = 0, message = "summary-marks obtained must be non-negative")
  private Integer obtained;

  @XmlAttribute(name = "available")
  public int getAvailable() {
    return available;
  }

  public void setAvailable(int available) {
    this.available = available;
  }

  @XmlAttribute(name = "obtained")
  public int getObtained() {
    return obtained;
  }

  public void setObtained(int obtained) {
    this.obtained = obtained;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SummaryMarksDTO that = (SummaryMarksDTO) o;
    return Objects.equals(available, that.available)
        && Objects.equals(obtained, that.obtained);
  }

  @Override
  public int hashCode() {
    return Objects.hash(available, obtained);
  }

  @Override
  public String toString() {
    return "SummaryMarksDTO{" +
        "available=" + available +
        ", obtained=" + obtained +
        '}';
  }
}
