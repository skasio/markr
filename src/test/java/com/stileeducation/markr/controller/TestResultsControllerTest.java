package com.stileeducation.markr.controller;

import com.stileeducation.markr.MarkrApplication;
import com.stileeducation.markr.converter.XmlMarkrMessageConverter;
import com.stileeducation.markr.dto.MCQTestResultsDTO;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import static com.stileeducation.markr.controller.TestResultsController.IMPORT_ENDPOINT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MarkrApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TestResultsControllerTest {

    private static MCQTestResultsDTO sampleResults;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    static void setup() throws JAXBException, IOException {
        ClassPathResource resource = new ClassPathResource("sample-results.xml");
        try (InputStream inputStream = resource.getInputStream()) {
            JAXBContext jaxbContext = JAXBContext.newInstance(MCQTestResultsDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            sampleResults = (MCQTestResultsDTO) unmarshaller.unmarshal(inputStream);
        }
    }

    private static String toXmlString(MCQTestResultsDTO mcqTestResultsDTO) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(MCQTestResultsDTO.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(mcqTestResultsDTO, stringWriter);
        return stringWriter.toString();
    }

    @Test
    void testPost() throws Exception {
        // Given
        String requestXml = toXmlString(sampleResults);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(XmlMarkrMessageConverter.MEDIA_TYPE);

        // When
        HttpEntity<String> entity = new HttpEntity<>(requestXml, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void testUnsupportedMediaType() throws Exception {
        // Given
        MCQTestResultsDTO request = new MCQTestResultsDTO();
        String requestXml = toXmlString(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        // When
        HttpEntity<String> entity = new HttpEntity<>(requestXml, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

}