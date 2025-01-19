package com.alxsshv.utils.fsa.factory;


import com.alxsshv.model.VerificationRecord;
import com.alxsshv.utils.DateStringConverter;
import com.alxsshv.utils.fsa.entity.MeasurementInstrument;

public class MeasurementInstrumentFactory {
    public static MeasurementInstrument createInstrument(VerificationRecord record){
        MeasurementInstrument instrument = new MeasurementInstrument();
        String[] verificationNumberParts = record.getNumberVerification().split("/");
        instrument.setNumberVerification(verificationNumberParts[2]);
        instrument.setTypeMeasuringInstrument(record.getTypeMeasurementInstrument());
        instrument.setDateVerification(DateStringConverter.getStringOrNull(record.getDateVerification()).substring(0,10));
        if (record.getDateEndVerification() != null) {
            instrument.setDateEndVerification(DateStringConverter.getStringOrNull(record.getDateEndVerification()).substring(0, 10));
        }
        instrument.setApprovedEmployee(ApprovedEmployeeFactory.createApprovedEmployee(record.getEmployee()));
        instrument.setResultVerification(record.getResultVerification());
        return instrument;
    }
}