package com.alxsshv.service;


import com.alxsshv.config.OrganizationConfig;
import com.alxsshv.config.PathsConfig;
import com.alxsshv.exceptions.ArshinResponseException;
import com.alxsshv.model.VerificationRecord;
import com.alxsshv.utils.arshin.ArshinHttpClient;
import com.alxsshv.utils.arshin.VerificationRequestBuilder;
import com.alxsshv.utils.arshin.entities.vri.VriItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;



@Service
@RequiredArgsConstructor
@Slf4j
public class ArshinDataService {
@Autowired
private PathsConfig paths;
@Autowired
private OrganizationConfig organizationConfig;

    public VriItem findVerificationRecordsData(VerificationRecord record) throws ArshinResponseException {
        log.info("Получение данных ФГИС \"Аршин\" для записи о поверке {} зав. №{} поверенного {}"
                , record.getTypeMeasurementInstrument(), record.getSerialNumber(), record.getDateVerification());
            List<String> modificationParts = Arrays.stream(record.getTypeMeasurementInstrument().trim().split(" ")).toList();
            String verificationRequest = new VerificationRequestBuilder()
                    .uri(paths.getArshinVriUri())
                    .miModification(modificationParts)
                    .miNumber(record.getSerialNumber())
                    .orgTitle(organizationConfig.getTitle())
                    .verificationDate(record.getDateVerification())
                    .build();
           return ArshinHttpClient.getVerificationItemIfOnlyMatches(verificationRequest, 0);
    }


}
