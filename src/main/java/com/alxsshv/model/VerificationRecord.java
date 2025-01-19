package com.alxsshv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationRecord {
    private String vriId;
    private String numberVerification; // Номер записи о поверке
    private LocalDate dateVerification;  //дата поверки
    private LocalDate dateEndVerification; //дата действия поверки
    private String typeMeasurementInstrument; //модификация
    private Employee employee; // поверитель
    private int resultVerification; // результат поверки
    private String serialNumber;
}
