package com.alxsshv.dto;

import com.alxsshv.model.VerificationRecord;
import com.alxsshv.utils.DateStringConverter;

public class VerificationRecordDtoMapper {

    public static VerificationRecordDto map(VerificationRecord record){
        VerificationRecordDto dto = new VerificationRecordDto();
        dto.setVriId(record.getVriId());
        dto.setNumberVerification(record.getNumberVerification());
        dto.setDateVerification(DateStringConverter.getStringOrNull(record.getDateVerification()));
        dto.setDateEndVerification(DateStringConverter.getStringOrNull(record.getDateEndVerification()));
        String result = record.getResultVerification() == 1 ? "Пригоден" : "Непригоден";
        dto.setResultVerification(result);
        dto.setTypeMeasurementInstrument(record.getTypeMeasurementInstrument());
        dto.setEmployee(record.getEmployee());
        return dto;
    }

}
