package com.stileeducation.markr.controller;

import com.stileeducation.markr.MarkrApplication;
import com.stileeducation.markr.dto.AggregateResponseDTO;
import com.stileeducation.markr.exception.TestNotFoundException;
import com.stileeducation.markr.service.TestResultsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = MarkrApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AggregateEndpointTests {

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private TestResultsService testResultsService;

  @Test
  public void testGetAggregatedResults_notFound() throws Exception {
    String testId = "1234";
    given(testResultsService.aggregateTestResults(testId))
        .willThrow(new TestNotFoundException("Test with ID " + testId + " not found"));

    String url = "/results/" + testId + "/aggregate";
    ResponseEntity<AggregateResponseDTO> response = restTemplate.getForEntity(url, AggregateResponseDTO.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody());
  }


  @Test
  public void testGetAggregatedResults() throws Exception {
    String testId = "test123";
    AggregateResponseDTO mockResponse = new AggregateResponseDTO();
    mockResponse.setMin(10.0);
    mockResponse.setMax(80.0);
    mockResponse.setMean(45.0);
    mockResponse.setStddev(25.0);
    mockResponse.setP25(17.5);
    mockResponse.setP50(25.0);
    mockResponse.setP75(72.5);
    mockResponse.setCount(6);

    given(testResultsService.aggregateTestResults(testId)).willReturn(mockResponse);

    String url = "/results/" + testId + "/aggregate";
    ResponseEntity<AggregateResponseDTO> response = restTemplate.getForEntity(url, AggregateResponseDTO.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockResponse.getMin(), response.getBody().getMin());
    assertEquals(mockResponse.getMax(), response.getBody().getMax());
    assertEquals(mockResponse.getMean(), response.getBody().getMean());
    assertEquals(mockResponse.getStddev(), response.getBody().getStddev());
    assertEquals(mockResponse.getP25(), response.getBody().getP25());
    assertEquals(mockResponse.getP50(), response.getBody().getP50());
    assertEquals(mockResponse.getP75(), response.getBody().getP75());
    assertEquals(mockResponse.getCount(), response.getBody().getCount());
  }
}
