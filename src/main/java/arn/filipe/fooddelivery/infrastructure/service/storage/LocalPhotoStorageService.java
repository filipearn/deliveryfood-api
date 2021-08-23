package arn.filipe.fooddelivery.infrastructure.service.storage;

import arn.filipe.fooddelivery.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalPhotoStorageService implements PhotoStorageService {

    @Value("${fooddelivery.storage.local.photos-directory}")
    private Path photosDirectory;

    @Override
    public InputStream recover(String fileName) {
        try {
            Path filePath = getFilePath(fileName);

            return Files.newInputStream(filePath);
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
        return photosDirectory.resolve(Path.of(fileName));
    }
}
