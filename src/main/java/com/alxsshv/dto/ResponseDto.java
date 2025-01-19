package com.alxsshv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDto {
    private final String filename;
    private final List<VerificationRecordDto> records;
}
