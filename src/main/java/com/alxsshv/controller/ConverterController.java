package com.alxsshv.controller;

import com.alxsshv.dto.ResponseDto;
import com.alxsshv.service.ConvertService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @PostMapping()
    public ResponseDto parseFile(@RequestParam("file") Optional<MultipartFile> fileOpt) throws IOException {
        if (fileOpt.isEmpty()) {
            throw new FileNotFoundException("Отсутствует файл для поиска данных о поверке");
        }
        return convertService.convert(fileOpt.get());

    }
}
