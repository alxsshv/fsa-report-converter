package com.alxsshv.utils.fsa;


import com.alxsshv.utils.fsa.entity.FsaReportMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Scanner;

@Component
@AllArgsConstructor
@Slf4j
public class FsaReportFileWriter {
    public void writeToFile(FsaReportMessage message, String filePath) throws FileNotFoundException {
            writeFsaXMLFile(message, filePath);
            deleteEmptyTagsFromFile(filePath);
    }

    private void writeFsaXMLFile(FsaReportMessage message, String filePath) {
        JaxbWriter writer = new JaxbWriter();
        writer.write(message, filePath);
    }

    private void deleteEmptyTagsFromFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        StringBuilder builder = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.contains("<DateEndVerification xsi:nil=\"true\"/>")) {
                    builder.append(line).append("\n");
                }
            }
            scanner.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            writer.write(builder.toString());
            writer.close();
        } catch (IOException ex) {
            log.error(ex.getMessage());
            throw new FileNotFoundException("Файл " + file.getName() + " не найден или не содержит записей");
        }
    }
}
