package com.alxsshv.controller;

import com.alxsshv.dto.ResponseDto;
import com.alxsshv.service.ConvertService;
import com.alxsshv.service.XMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/convert")
public class ConverterController {
    @Autowired
    private ConvertService convertService;
    @Autowired
    private XMLService xmlService;


    @GetMapping(value = "/xml", produces = "application/xml")
    @ResponseBody
    public ResponseEntity<?> getXMLReportForFsa(@RequestParam("filename")String filename) throws IOException {
        return xmlService.getFsaXMLFile(filename);
    }

    @PostMapping()
    public ResponseDto parseFile(@RequestParam("file") Optional<MultipartFile> fileOpt) throws IOException {
        if (fileOpt.isEmpty()) {
            throw new FileNotFoundException("Отсутствует файл для поиска данных о поверке");
        }
        return convertService.convert(fileOpt.get());

    }
}
