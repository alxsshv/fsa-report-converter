package com.alxsshv.model;

import com.poiji.annotation.ExcelCellName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArshinDataObject {
    @ExcelCellName("Number")
    private String arshinNumber;
    @NotNull (message = "Не указан серийный (заводской) номер средства измерений")
    @ExcelCellName("Serial")
    private String serialNumber;
    @NotNull (message = "Не указана модификация средства измерений")
    @ExcelCellName("Modification")
    private String modification;
    @ExcelCellName("Sign")
    private String stamp;
    @ExcelCellName("Owner")
    private String owner;
    @NotNull(message = "Отсутствует дата поверки")
    @Pattern(regexp = "\\d{4}[^0-9]\\d{2}[^0-9]\\d{2}\\+\\d{2}:\\d{2}",
            message = "Неверный формат даты поверки СИ")
    @ExcelCellName("VerificationDate")
    private String verificationDate;
    @ExcelCellName("ValidDate")
    private String validDate;
    @ExcelCellName("Type")
    private int verificationType;
    @ExcelCellName("Calibration")
    private boolean calibration;
    @ExcelCellName("StickerNum")
    private String stickerNum;
    @ExcelCellName("SignPass")
    private boolean passportStamp;
    @ExcelCellName("SignMi")
    private boolean miStamp;
    @ExcelCellName("Document")
    private String verificationDocument;
    @NotNull(message = "Не указан поверитель")
    @ExcelCellName("Employee")
    private String employee;
    @ExcelCellName("Standards")
    private String standards;
    @ExcelCellName("Temperature")
    private String temperature;
    @ExcelCellName("Pressure")
    private String pressure;
    @ExcelCellName("Humidity")
    private String humidity;
    @ExcelCellName("Reason")
    private String reason;
}
