package com.stileeducation.markr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AggregateResponseDTO {

  private double mean = 0.0;
  private double stddev = 0.0;
  private double min = 0.0;
  private double max = 0.0;
  private double p25 = 0.0;
  private double p50 = 0.0;
  private double p75 = 0.0;
  private int count = 0;

  // Getters and Setters
  public double getMean() {
    return mean;
  }

  public void setMean(double mean) {
    this.mean = mean;
  }

  public double getStddev() {
    return stddev;
  }

  public void setStddev(double stddev) {
    this.stddev = stddev;
  }

  public double getMin() {
    return min;
  }

  public void setMin(double min) {
    this.min = min;
  }

  public double getMax() {
    return max;
  }

  public void setMax(double max) {
    this.max = max;
  }

  public double getP25() {
    return p25;
  }

  public void setP25(double p25) {
    this.p25 = p25;
  }

  public double getP50() {
    return p50;
  }

  public void setP50(double p50) {
    this.p50 = p50;
  }

  public double getP75() {
    return p75;
  }

  public void setP75(double p75) {
    this.p75 = p75;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AggregateResponseDTO that = (AggregateResponseDTO) o;
    return Double.compare(mean, that.mean) == 0
        && Double.compare(stddev, that.stddev) == 0
        && Double.compare(min, that.min) == 0
        && Double.compare(max, that.max) == 0
        && Double.compare(p25, that.p25) == 0
        && Double.compare(p50, that.p50) == 0
        && Double.compare(p75, that.p75) == 0
        && count == that.count;
  }

  @Override
  public int hashCode() {
    return Objects.hash(mean, stddev, min, max, p25, p50, p75, count);
  }

  @Override
  public String toString() {
    return "AggregatedTestResultsDTO{" +
        "mean=" + mean +
        ", stddev=" + stddev +
        ", min=" + min +
        ", max=" + max +
        ", p25=" + p25 +
        ", p50=" + p50 +
        ", p75=" + p75 +
        ", count=" + count +
        '}';
  }
}
