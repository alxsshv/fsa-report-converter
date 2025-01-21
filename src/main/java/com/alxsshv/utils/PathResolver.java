package com.alxsshv.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.regex.Pattern;

@Component
@Slf4j
public class PathResolver {
    private final String fileSeparetor = FileSystems.getDefault().getSeparator();

    public void createFilePathIfNotExist(String path) throws IOException {
        String[] folders = path.split(fileSeparetor);
        StringBuilder currentPath = new StringBuilder();
        for (String folder : folders){
            currentPath.append(folder).append(fileSeparetor);
            createFolderIfNotExist(String.valueOf(currentPath));
        }
    }

    public void createFolderIfNotExist(String folderPath) throws IOException {
        File directory = new File(folderPath);
        if (!directory.exists() && !directory.getPath().isEmpty() && isDirectory(folderPath)) {
            log.info("Создание отсутствующей директории {}", directory.getAbsolutePath());
            boolean isCreated = directory.mkdir();
            System.out.println(directory.getAbsolutePath());
            if (!isCreated){
                throw new IOException("Ошибка создания директории " +  directory.getPath());
            }
        }
    }

    public boolean isDirectory(String fileName){
        String fileTemplate = "[\\w\\/\\\\]+\\.[\\w\\/\\\\]+";
        return !Pattern.matches(fileTemplate,fileName);
    }
}
