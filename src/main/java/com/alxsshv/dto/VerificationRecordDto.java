package com.alxsshv.dto;

import com.alxsshv.model.Employee;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VerificationRecordDto {
    private String vriId;
    private String numberVerification;
    private String dateVerification;
    private String dateEndVerification;
    private String typeMeasurementInstrument;
    private Employee employee;
    private String resultVerification;
}
