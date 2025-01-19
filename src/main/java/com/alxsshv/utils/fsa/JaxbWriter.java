package com.alxsshv.utils.fsa;

import com.alxsshv.utils.fsa.entity.FsaReportMessage;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Service
@AllArgsConstructor
@Slf4j
public class JaxbWriter {

    public void write(FsaReportMessage message, String filePath){
        try{
            JAXBContext context = JAXBContext.newInstance(FsaReportMessage.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "schema.xsd");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(message, new FileOutputStream(filePath));
            log.info("Файл создан");
        } catch (JAXBException e) {
            log.error("Jaxb-context ошибочен " + e);
        } catch (FileNotFoundException ex){
            log.error("Файл не может быть создан " + ex);
        }
    }
}