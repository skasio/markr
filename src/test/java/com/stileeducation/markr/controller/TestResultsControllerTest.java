package com.stileeducation.markr.controller;

import com.stileeducation.markr.MarkrApplication;
import com.stileeducation.markr.converter.XmlMarkrMessageConverter;
import com.stileeducation.markr.dto.ImportResponseDTO;
import com.stileeducation.markr.dto.MCQTestResultsDTO;
import com.stileeducation.markr.repository.TestResultRepository;
import com.stileeducation.markr.service.TestService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.io.StringWriter;

import static com.stileeducation.markr.controller.TestResultsController.IMPORT_ENDPOINT;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MarkrApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TestResultsControllerTest {

  private static String validPayload;
  private static String invalidPayload;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private TestResultRepository testResultRepository;
  @Autowired
  private TestService testService;

  @BeforeAll
  static void setup() throws IOException {
    validPayload = new String(new ClassPathResource("sample-results.xml").getInputStream().readAllBytes(), UTF_8);
    invalidPayload = new String(new ClassPathResource("invalid-payload.xml").getInputStream().readAllBytes(), UTF_8);
  }

  private static String toXmlString(MCQTestResultsDTO mcqTestResultsDTO) throws JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance(MCQTestResultsDTO.class);
    Marshaller marshaller = jaxbContext.createMarshaller();
    StringWriter stringWriter = new StringWriter();
    marshaller.marshal(mcqTestResultsDTO, stringWriter);
    return stringWriter.toString();
  }


  @Test
  void testSupportedMediaType() throws Exception {
    // Given
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(XmlMarkrMessageConverter.MEDIA_TYPE);
    HttpEntity<String> entity = new HttpEntity<>(validPayload, headers);

    // When
    ResponseEntity<String> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, String.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void testUnsupportedMediaType() throws Exception {
    // Given
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_XML);
    HttpEntity<String> entity = new HttpEntity<>(validPayload, headers);

    // When
    ResponseEntity<String> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, String.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @Test
  void testInvalidImport() throws Exception {
    // Given
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(XmlMarkrMessageConverter.MEDIA_TYPE);
    HttpEntity<String> entity = new HttpEntity<>(invalidPayload, headers);

    // When
    ResponseEntity<String> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, String.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void testInvalidImport_MissingFirstName() throws Exception {
    // Given
    String invalidPayload = """
            <mcq-test-results>
                <mcq-test-result scanned-on="2024-07-22T12:12:10+11:00">
                    <first-name></first-name>
                    <last-name>Szukalski</last-name>
                    <student-number>001122</student-number>
                    <test-id>2024001</test-id>
                    <summary-marks available="20" obtained="13"/>
                </mcq-test-result>
            </mcq-test-results>
        """;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(XmlMarkrMessageConverter.MEDIA_TYPE);
    HttpEntity<String> entity = new HttpEntity<>(invalidPayload, headers);

    // When
    ResponseEntity<ImportResponseDTO> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, ImportResponseDTO.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Invalid payload");
  }

  @Test
  void testInvalidImport_MissingLastName() throws Exception {
    // Given
    String invalidPayload = """
            <mcq-test-results>
                <mcq-test-result scanned-on="2024-07-22T12:12:10+11:00">
                    <first-name>Szymon</first-name>
                    <last-name></last-name>
                    <student-number>001122</student-number>
                    <test-id>2024001</test-id>
                    <summary-marks available="20" obtained="13"/>
                </mcq-test-result>
            </mcq-test-results>
        """;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(XmlMarkrMessageConverter.MEDIA_TYPE);
    HttpEntity<String> entity = new HttpEntity<>(invalidPayload, headers);

    // When
    ResponseEntity<ImportResponseDTO> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, ImportResponseDTO.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Invalid payload");
  }

  @Test
  void testInvalidImport_MissingStudentNumber() throws Exception {
    // Given
    String invalidPayload = """
            <mcq-test-results>
                <mcq-test-result scanned-on="2024-07-22T12:12:10+11:00">
                    <first-name>Szymon</first-name>
                    <last-name>Szukalski</last-name>
                    <student-number></student-number>
                    <test-id>2024001</test-id>
                    <summary-marks available="20" obtained="13"/>
                </mcq-test-result>
            </mcq-test-results>
        """;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(XmlMarkrMessageConverter.MEDIA_TYPE);
    HttpEntity<String> entity = new HttpEntity<>(invalidPayload, headers);

    // When
    ResponseEntity<ImportResponseDTO> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, ImportResponseDTO.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Invalid payload");
  }

  @Test
  void testInvalidImport_MissingTestId() throws Exception {
    // Given
    String invalidPayload = """
            <mcq-test-results>
                <mcq-test-result scanned-on="2024-07-22T12:12:10+11:00">
                    <first-name>Szymon</first-name>
                    <last-name>Szukalski</last-name>
                    <student-number>2024001</student-number>
                    <test-id></test-id>
                    <summary-marks available="20" obtained="13"/>
                </mcq-test-result>
            </mcq-test-results>
        """;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(XmlMarkrMessageConverter.MEDIA_TYPE);
    HttpEntity<String> entity = new HttpEntity<>(invalidPayload, headers);

    // When
    ResponseEntity<ImportResponseDTO> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, ImportResponseDTO.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Invalid payload");
  }

  @Test
  void testInvalidImport_MissingSummaryMarks() throws Exception {
    // Given
    String invalidPayload = """
            <mcq-test-results>
                <mcq-test-result scanned-on="2024-07-22T12:12:10+11:00">
                    <first-name>Szymon</first-name>
                    <last-name>Szukalski</last-name>
                    <student-number>001122</student-number>
                    <test-id>2024001</test-id>
                </mcq-test-result>
            </mcq-test-results>
        """;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(XmlMarkrMessageConverter.MEDIA_TYPE);
    HttpEntity<String> entity = new HttpEntity<>(invalidPayload, headers);

    // When
    ResponseEntity<ImportResponseDTO> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, ImportResponseDTO.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Invalid payload");
  }

  @Test
  void testInvalidImport_MissingSummaryMarksAvailable() throws Exception {
    // Given
    String invalidPayload = """
            <mcq-test-results>
                <mcq-test-result scanned-on="2024-07-22T12:12:10+11:00">
                    <first-name>Szymon</first-name>
                    <last-name>Szukalski</last-name>
                    <student-number>001122</student-number>
                    <test-id>2024001</test-id>
                    <summary-marks obtained="13"/>
                </mcq-test-result>
            </mcq-test-results>
        """;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(XmlMarkrMessageConverter.MEDIA_TYPE);
    HttpEntity<String> entity = new HttpEntity<>(invalidPayload, headers);

    // When
    ResponseEntity<ImportResponseDTO> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, ImportResponseDTO.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Invalid payload");
  }

  @Test
  void testInvalidImport_MissingSummaryMarksObtained() throws Exception {
    // Given
    String invalidPayload = """
            <mcq-test-results>
                <mcq-test-result scanned-on="2024-07-22T12:12:10+11:00">
                    <first-name>Szymon</first-name>
                    <last-name>Szukalski</last-name>
                    <student-number>001122</student-number>
                    <test-id>2024001</test-id>
                    <summary-marks available="20"/>
                </mcq-test-result>
            </mcq-test-results>
        """;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(XmlMarkrMessageConverter.MEDIA_TYPE);
    HttpEntity<String> entity = new HttpEntity<>(invalidPayload, headers);

    // When
    ResponseEntity<ImportResponseDTO> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, ImportResponseDTO.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Invalid payload");
  }

  @Test
  void testInvalidImport_InvalidXmlFormat() throws Exception {
    // Given
    String invalidPayload = """
            <mcq-test-results>
                <mcq-test-result scanned-on="2024-07-22T12:12:10+11:00">
                    <first-name>Szymon</first-name>
                    <last-name>Szukalski</last-name>
                    <student-number>001122</student-number>
                    <test-id>2024001</test-id>
                    <summary-marks available="20" obtained="1
                </mcq-test-result>
            </mcq-test-results>
        """;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(XmlMarkrMessageConverter.MEDIA_TYPE);
    HttpEntity<String> entity = new HttpEntity<>(invalidPayload, headers);

    // When
    ResponseEntity<ImportResponseDTO> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, ImportResponseDTO.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Invalid XML payload");
  }

  @Test
  void testInitialSubmitReportsCreatedEntities() throws Exception {
    // Given
    String validPayload = """
            <mcq-test-results>
                <mcq-test-result scanned-on="2024-07-22T12:12:10+11:00">
                    <first-name>John</first-name>
                    <last-name>Doe</last-name>
                    <student-number>002233</student-number>
                    <test-id>202402</test-id>
                    <summary-marks available="20" obtained="13"/>
                </mcq-test-result>
            </mcq-test-results>
        """;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(XmlMarkrMessageConverter.MEDIA_TYPE);
    HttpEntity<String> entity = new HttpEntity<>(validPayload, headers);

    // When
    ResponseEntity<ImportResponseDTO> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, ImportResponseDTO.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getStatus()).isEqualTo("success");
    assertThat(response.getBody().getMessage()).isEqualTo("Import completed successfully");
    assertThat(response.getBody().getData().getStudentsCreated()).isEqualTo(1);
    assertThat(response.getBody().getData().getStudentsUpdated()).isEqualTo(0);
    assertThat(response.getBody().getData().getTestsCreated()).isEqualTo(1);
    assertThat(response.getBody().getData().getTestsUpdated()).isEqualTo(0);
    assertThat(response.getBody().getData().getTestResultsCreated()).isEqualTo(1);
    assertThat(response.getBody().getData().getTestResultsUpdated()).isEqualTo(0);
  }


  @Test
  void testSubsequentSubmitReportsNoCreatedOrUpdatedEntities() throws Exception {
    // Given
    String validPayload = """
            <mcq-test-results>
                <mcq-test-result scanned-on="2024-07-22T12:12:10+11:00">
                    <first-name>John</first-name>
                    <last-name>Doe</last-name>
                    <student-number>003344</student-number>
                    <test-id>202403</test-id>
                    <summary-marks available="20" obtained="13"/>
                </mcq-test-result>
                <mcq-test-result scanned-on="2024-07-22T12:12:10+11:00">
                    <first-name>John</first-name>
                    <last-name>Doe</last-name>
                    <student-number>003344</student-number>
                    <test-id>202403</test-id>
                    <summary-marks available="20" obtained="13"/>
                </mcq-test-result>
            </mcq-test-results>
        """;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(XmlMarkrMessageConverter.MEDIA_TYPE);
    HttpEntity<String> entity = new HttpEntity<>(validPayload, headers);

    // When
    ResponseEntity<ImportResponseDTO> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, ImportResponseDTO.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getStatus()).isEqualTo("success");
    assertThat(response.getBody().getMessage()).isEqualTo("Import completed successfully");
    assertThat(response.getBody().getData().getStudentsCreated()).isEqualTo(0);
    assertThat(response.getBody().getData().getStudentsUpdated()).isEqualTo(0);
    assertThat(response.getBody().getData().getTestsCreated()).isEqualTo(0);
    assertThat(response.getBody().getData().getTestsUpdated()).isEqualTo(0);
    assertThat(response.getBody().getData().getTestResultsCreated()).isEqualTo(0);
    assertThat(response.getBody().getData().getTestResultsUpdated()).isEqualTo(0);
  }


  @Test
  void testMarksAvailableAndObtainedAreUpdated() throws Exception {
    // Given
    String payload = """
            <mcq-test-results>
                <mcq-test-result scanned-on="2024-07-22T12:12:10+11:00">
                    <first-name>John</first-name>
                    <last-name>Doe</last-name>
                    <student-number>004455</student-number>
                    <test-id>202404</test-id>
                    <summary-marks available="20" obtained="13"/>
                </mcq-test-result>
                <mcq-test-result scanned-on="2024-07-22T12:12:10+11:00">
                    <first-name>John</first-name>
                    <last-name>Doe</last-name>
                    <student-number>004455</student-number>
                    <test-id>202404</test-id>
                    <summary-marks available="24" obtained="15"/>
                </mcq-test-result>
            </mcq-test-results>
        """;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(XmlMarkrMessageConverter.MEDIA_TYPE);
    HttpEntity<String> entity = new HttpEntity<>(payload, headers);

    // When
    ResponseEntity<ImportResponseDTO> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, ImportResponseDTO.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getStatus()).isEqualTo("success");
    assertThat(response.getBody().getMessage()).isEqualTo("Import completed successfully");

    assertThat(response.getBody().getData().getStudentsCreated()).isEqualTo(1);
    assertThat(response.getBody().getData().getStudentsUpdated()).isEqualTo(0);

    assertThat(response.getBody().getData().getTestsCreated()).isEqualTo(1);
    assertThat(response.getBody().getData().getTestsUpdated()).isEqualTo(1);

    assertThat(response.getBody().getData().getTestResultsCreated()).isEqualTo(1);
    assertThat(response.getBody().getData().getTestResultsUpdated()).isEqualTo(1);
  }

  @Test
  void testMarksAvailableAndObtainedAreNotUpdatedWhenFirstSubmissionIsHigher() throws Exception {
    // Given
    String payload = """
            <mcq-test-results>
                <mcq-test-result scanned-on="2024-07-22T12:12:10+11:00">
                    <first-name>John</first-name>
                    <last-name>Doe</last-name>
                    <student-number>005566</student-number>
                    <test-id>202405</test-id>
                    <summary-marks available="24" obtained="15"/>
                </mcq-test-result>
                <mcq-test-result scanned-on="2024-07-22T12:12:10+11:00">
                    <first-name>John</first-name>
                    <last-name>Doe</last-name>
                    <student-number>005566</student-number>
                    <test-id>202405</test-id>
                    <summary-marks available="20" obtained="13"/>
                </mcq-test-result>
            </mcq-test-results>
        """;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(XmlMarkrMessageConverter.MEDIA_TYPE);
    HttpEntity<String> entity = new HttpEntity<>(payload, headers);

    // When
    ResponseEntity<ImportResponseDTO> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, ImportResponseDTO.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getStatus()).isEqualTo("success");
    assertThat(response.getBody().getMessage()).isEqualTo("Import completed successfully");
    assertThat(response.getBody().getData().getStudentsCreated()).isEqualTo(1);
    assertThat(response.getBody().getData().getStudentsUpdated()).isEqualTo(0);
    assertThat(response.getBody().getData().getTestsCreated()).isEqualTo(1);
    assertThat(response.getBody().getData().getTestsUpdated()).isEqualTo(0);
    assertThat(response.getBody().getData().getTestResultsCreated()).isEqualTo(1);
    assertThat(response.getBody().getData().getTestResultsUpdated()).isEqualTo(0);
  }

}