package org.example.perfumecatalog.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    public void save(String uploadDirectory, String fileName, MultipartFile multipartFile) {
        var fileNameAndPath = Paths.get(uploadDirectory + File.separatorChar + fileName);
        checkIfAlreadyExists(fileNameAndPath);
        trySavingFile(multipartFile, fileNameAndPath);
    }

    private void checkIfAlreadyExists(Path fileNameAndPath) {
        if (Files.exists(fileNameAndPath)) {
            throw new IllegalArgumentException(
                    String.format("File with path %s already exists", fileNameAndPath
                    ));
        }
    }

    private void trySavingFile(MultipartFile multipartFile, Path fileNameAndPath) {
        try {
            Files.write(fileNameAndPath, multipartFile.getBytes());
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to save", e);
        }
    }

    public void delete(String sourceDir, String fileName) {
        try {
            Files.deleteIfExists(Paths.get(sourceDir, fileName));
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to delete", e);
        }
    }
}
