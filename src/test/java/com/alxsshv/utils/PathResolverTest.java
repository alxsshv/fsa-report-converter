package com.alxsshv.utils;


import junit.framework.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathResolverTest {
    private final PathResolver pathResolver = new PathResolver();

    @Test
    public void testCreateFilePathIfNotExist(@TempDir Path tempDir) throws IOException {
        pathResolver.createFilePathIfNotExist(tempDir + "/nextFolder/file.txt");
        Assert.assertTrue(Files.exists(Path.of(tempDir + "/nextFolder/")));
    }

    @Test
    public void testCreateFolderIfNotExist(@TempDir Path tempDir) throws IOException {
        String folderPath = tempDir + "/nextFolder";
        System.out.println(folderPath);
        pathResolver.createFolderIfNotExist(folderPath);
        Assertions.assertTrue(Files.exists(Path.of(folderPath)));
    }

    @Test

    public void testIsDirectoryIfIsNotFolder(){
        String fileName = "filesystem/file.xls";
        Assertions.assertFalse(pathResolver.isDirectory(fileName));
    }

    @Test
    @DisplayName("Test isDirectory if is directory")
    public void testIsDirectoryIfIsFolder(){
        String folderName = "filesystem/files";
        Assertions.assertTrue(pathResolver.isDirectory(folderName));
    }

}
