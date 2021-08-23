package arn.filipe.fooddelivery.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface PhotoStorageService {

    InputStream recover(String fileName);

    void store(NewPhoto newPhoto);

    void remove(String fileName);

    default void replace(String oldFileName, NewPhoto newPhoto){
        this.store(newPhoto);

        if(oldFileName != null){
            this.remove(oldFileName);
        }
    }

    default String generateFileName(String originalName){
        return UUID.randomUUID().toString() + "_" + originalName;
    }

    @Builder
    @Getter
    class NewPhoto{
        private String fileName;
        private InputStream inputStream;

    }
}
