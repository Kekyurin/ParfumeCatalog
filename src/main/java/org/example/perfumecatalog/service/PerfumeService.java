package org.example.perfumecatalog.service;

import org.example.perfumecatalog.model.Perfume;
import org.example.perfumecatalog.repository.PerfumeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PerfumeService {

    private final PerfumeRepository perfumeRepository;

    private final FileService fileService;

    private final RandomNameGeneratorService randomNameGeneratorService;

    private final String perfumePictureAbsoluteUploadPath;

    public PerfumeService(PerfumeRepository perfumeRepository,
                          FileService fileService,
                          RandomNameGeneratorService randomNameGeneratorService,
                          @Qualifier("perfumePictureAbsoluteUploadPath")
                          String perfumePictureAbsoluteUploadPath) {
        this.perfumeRepository = perfumeRepository;
        this.fileService = fileService;
        this.randomNameGeneratorService = randomNameGeneratorService;
        this.perfumePictureAbsoluteUploadPath = perfumePictureAbsoluteUploadPath;
    }

    public Perfume savePerfume(Perfume perfume, MultipartFile perfumeImage) {
        String filename = getRandomPictureName(perfumeImage);
        perfume.setPerfumePictureName(filename);
        fileService.save(perfumePictureAbsoluteUploadPath, filename, perfumeImage);
        return perfumeRepository.save(perfume);
    }

    private String getRandomPictureName(MultipartFile picture) {
        String filename = picture.getOriginalFilename();
        var extension = "";
        if(filename != null) {
            String[] splitFilename = filename.split("\\.");
            extension = splitFilename[splitFilename.length-1];
        }
        return randomNameGeneratorService.getRandomName() + "." + extension;
    }

    public List<Perfume> getAllPerfumes() {
        return perfumeRepository.findAll();
    }

    public Perfume getById(Long id) {
        return getRawPerfumeOrThrowNotFound(id);
    }

    private Perfume getRawPerfumeOrThrowNotFound(Long id) {
        return perfumeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Perfume with id %s not found".formatted(id)));
    }

    public void deletePerfume(Long perfumeId) {
        perfumeRepository.findById(perfumeId).ifPresent(perfume -> {
            perfumeRepository.deleteById(perfumeId);
            fileService.delete(perfumePictureAbsoluteUploadPath, perfume.getPerfumePictureName());
        });
    }

    public void updatePerfume(Long perfumeId, Perfume newData, MultipartFile newPerfumeImage) {
        Perfume fromDb = getRawPerfumeOrThrowNotFound(perfumeId);
        if (newData.getName() != null && !newData.getName().isEmpty()) {
            fromDb.setName(newData.getName());
        }
        if (newData.getDescription() != null && !newData.getDescription().isEmpty()) {
            fromDb.setDescription(newData.getDescription());
        }
        if (newPerfumeImage != null &&
            newPerfumeImage.getOriginalFilename() != null &&
            !newPerfumeImage.getOriginalFilename().isEmpty()) {
            fileService.delete(perfumePictureAbsoluteUploadPath, fromDb.getPerfumePictureName());
            String newPictureName = getRandomPictureName(newPerfumeImage);
            fileService.save(perfumePictureAbsoluteUploadPath, newPictureName, newPerfumeImage);
            fromDb.setPerfumePictureName(newPictureName);
        }
        perfumeRepository.save(fromDb);
    }

}
