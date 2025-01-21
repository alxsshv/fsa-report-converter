package com.alxsshv.utils;

import com.alxsshv.model.ArshinDataObject;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Slf4j
@Component
public class ExcelConverter {

    public List<ArshinDataObject> getDataFromExcelFile(MultipartFile file) throws IOException {
        log.info("Читаем данные из файла");
        InputStream is = new BufferedInputStream(file.getInputStream());
        return Poiji.fromExcel(is, PoijiExcelType.XLSX, ArshinDataObject.class);
    }
}
