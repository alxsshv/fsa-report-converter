package com.alxsshv.service;

import com.alxsshv.config.PathsConfig;
import com.alxsshv.dto.ResponseDto;
import com.alxsshv.dto.VerificationRecordDto;
import com.alxsshv.dto.VerificationRecordDtoMapper;
import com.alxsshv.model.VerificationRecord;
import com.alxsshv.utils.PathResolver;
import com.alxsshv.utils.fsa.FsaReportFileWriter;
import com.alxsshv.utils.fsa.entity.FsaReportMessage;
import com.alxsshv.utils.fsa.factory.FsaReportMessageFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;


@Service
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Slf4j
public class XMLService {
    private static final String separator = FileSystems.getDefault().getSeparator();
    @Autowired
    private FsaReportFileWriter fsaReportFileWriter;
    @Autowired
    private PathsConfig paths;
    @Autowired
    private PathResolver pathResolver;

    public ResponseDto createFsaXMLfile(List<VerificationRecord> records) throws IOException {
        FsaReportMessage fsaMessage = FsaReportMessageFactory.createMessage(records);
        String fileName = "fsaReport" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + ".xml";
        pathResolver.createFilePathIfNotExist(paths.getReportStoragePath());
        String filePath = paths.getReportStoragePath()+ separator + fileName;
        fsaReportFileWriter.writeToFile(fsaMessage, filePath);
        List<VerificationRecordDto> recordDtos = records.stream().map(VerificationRecordDtoMapper:: map).toList();
        return new ResponseDto(fileName, recordDtos);
    }

    public ResponseEntity<?> getFsaXMLFile(String fileName) throws FileNotFoundException{
        try {
            File fsaXML = new File(paths.getReportStoragePath() + separator + fileName);
            return ResponseEntity.ok()
                    .header("Content-Disposition" , "attachment; filename=\""+ fsaXML.getName() +"\"")
                    .body(Files.readAllBytes(Path.of(fsaXML.toURI())));
        } catch (IOException ex) {
            log.error(ex.getMessage());
            throw new FileNotFoundException("Файл " + fileName + " не найден");
        }
    }


    }



