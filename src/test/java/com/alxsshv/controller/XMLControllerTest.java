package com.alxsshv.controller;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class XMLControllerTest {
    private static String separator = FileSystems.getDefault().getSeparator();
    @LocalServerPort
    private int port;
    private final TestRestTemplate template = new TestRestTemplate();
    @TempDir
    private static Path tempDir;

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("paths.report-storage-path", ()->tempDir.toString());
    }

    @Test
    @DisplayName("Test getXMLReportForFSA if file found")
    public void testGetXMLReportForFSAIfFileFound() throws IOException {
        String expectedContent = "test";
        String reportFileName = "report.xml";
        File reportFile =  new File(tempDir + separator + reportFileName);
        FileWriter writer = new FileWriter(reportFile);
        writer.write(expectedContent);
        writer.flush();
        writer.close();
        String actualContent = template.getRestTemplate()
                .getForObject("http://localhost:" + port + "/xml?filename="+reportFileName, String.class);
        Assertions.assertEquals(expectedContent, actualContent);
    }

    @Test
    @DisplayName("Test getXMLReportForFSA if file not found")
    public void testGetXMLReportForFSAIfFileNotFound() throws IOException {
        String expectedContent = "test";
        String reportFileName = "report.xml";
        ResponseEntity<String> response = template.getRestTemplate()
                .getForEntity("http://localhost:" + port + "/xml?filename="+reportFileName, String.class);
        Assertions.assertEquals(500,response.getStatusCode().value());
    }

}
