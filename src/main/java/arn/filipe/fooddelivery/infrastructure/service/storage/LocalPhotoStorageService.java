package arn.filipe.fooddelivery.infrastructure.service.storage;

import arn.filipe.fooddelivery.core.storage.StorageProperties;
import arn.filipe.fooddelivery.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;


public class LocalPhotoStorageService implements PhotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public RecoveredPhoto recover(String fileName) {
        try {
            Path filePath = getFilePath(fileName);
            RecoveredPhoto recoveredPhoto = RecoveredPhoto.builder()
                    .inputStream(Files.newInputStream(filePath))
                    .build();

            return recoveredPhoto;
        } catch (FileNotFoundException e) {
            throw new StorageException("File not found", e);
        } catch (IOException e) {
            throw new StorageException("Was not possible to recover the file", e);
        }
    }

    @Override
    public void store(NewPhoto newPhoto) {

        try{
            Path filePath = getFilePath(newPhoto.getFileName());

            FileCopyUtils.copy(newPhoto.getInputStream(),
                    Files.newOutputStream(filePath));
        } catch (Exception e){
            throw new StorageException("Was not possible to store the file", e);
        }
    }

    @Override
    public void remove(String fileName) {
        try{
            Path filePath = getFilePath(fileName);
            Files.deleteIfExists(filePath);
        } catch (Exception e){
            throw new StorageException("Was not possible to exclude the file", e);
        }

    }

    private Path getFilePath(String fileName){
        return storageProperties.getLocal().getPhotosDirectory().resolve(Path.of(fileName));
    }
}
