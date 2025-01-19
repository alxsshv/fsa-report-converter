package com.alxsshv.utils;

import com.alxsshv.model.ArshinDataObject;
import com.alxsshv.model.Employee;
import com.alxsshv.model.VerificationRecord;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Validated
@Component
public class VerificationRecordParser {
    public List<VerificationRecord> parse(@Valid List<ArshinDataObject> arshinObjects, List<Employee> employees){
        List<VerificationRecord> records = new ArrayList<>();
        for (ArshinDataObject object : arshinObjects){
            VerificationRecord record = new VerificationRecord();
            record.setDateVerification(DateStringConverter.parseLocalDateOrGetNull(object.getVerificationDate()));
            record.setDateEndVerification(DateStringConverter.parseLocalDateOrGetNull(object.getValidDate()));
            record.setTypeMeasurementInstrument(object.getModification().trim());
            record.setSerialNumber(object.getSerialNumber().trim());
            int result = object.getReason()== null ? 1 : 2; //1 - пригоден, 2 - непригоден
            record.setResultVerification(result);
            record.setEmployee(EmployeeFinder.find(employees,object.getEmployee().trim()));
            records.add(record);
        }
        return records;
    }
}
