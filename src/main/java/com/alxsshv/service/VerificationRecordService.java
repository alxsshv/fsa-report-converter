package com.alxsshv.service;

import com.alxsshv.exceptions.ArshinResponseException;
import com.alxsshv.model.VerificationRecord;
import com.alxsshv.utils.arshin.entities.vri.VriItem;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
@Slf4j
public class VerificationRecordService {
    @Autowired
    private ArshinDataService arshinDataService;

    public void updateRecordsFromArshin(List<VerificationRecord> records) {
        String okMessage = "Номера записей о поверке в ФГИС Аршин успешно получены";
        for (VerificationRecord record : records) {
            try {
                VriItem item = arshinDataService.findVerificationRecordsData(record);
                record.setVriId(item.getVriId());
                record.setNumberVerification(item.getResultDocnum());
                int result = item.getResultDocnum().contains("С-") ? 1 : 2;
                record.setResultVerification(result);
                Thread.sleep(200);
            } catch (ArshinResponseException ex){
                log.error(ex.getMessage());
                throw new ArshinResponseException(ex.getMessage());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.info(okMessage);
    }
}
