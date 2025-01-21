package com.alxsshv.controller;

import com.alxsshv.service.XMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/xml")
public class XmlController {
    @Autowired
    private XMLService xmlService;

    @GetMapping(produces = "application/xml")
    @ResponseBody
    public ResponseEntity<?> getXMLReportForFsa(@RequestParam("filename")String filename) throws IOException {
        return xmlService.getFsaXMLFile(filename);
    }
}
