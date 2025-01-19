package com.alxsshv.service;


import com.alxsshv.dto.ResponseDto;
import com.alxsshv.model.ArshinDataObject;
import com.alxsshv.model.Employee;
import com.alxsshv.model.VerificationRecord;
import com.alxsshv.utils.ExcelConverter;
import com.alxsshv.utils.VerificationRecordParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ConvertService {
    @Autowired
    private ExcelConverter excelConverter;
    @Autowired
    private VerificationRecordParser verificationRecordParser;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private VerificationRecordService recordService;
    @Autowired
    private XMLService xmlService;

    public ResponseDto convert(MultipartFile file) throws IOException {
        List<Employee> employees = employeeService.findAll();
        List<ArshinDataObject> objects = excelConverter.getDataFromExcelFile(file);
        List<VerificationRecord> records = verificationRecordParser.parse(objects,employees);
        recordService.updateRecordsFromArshin(records);
        return xmlService.createFsaXMLfile(records);
    }
}
