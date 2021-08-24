package arn.filipe.fooddelivery.infrastructure.service.storage;

import arn.filipe.fooddelivery.core.storage.StorageProperties;
import arn.filipe.fooddelivery.domain.service.PhotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;


public class S3PhotoStorageService implements PhotoStorageService{

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public RecoveredPhoto recover(String fileName) {
        String filePth = getFilePath(fileName);

        URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), filePth);

        return RecoveredPhoto.builder()
                .url(url.toString()).build();
    }

    @Override
    public void store(NewPhoto newPhoto) {
        try{
            String filePath = getFilePath(newPhoto.getFileName());

            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(newPhoto.getContentType());

            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    filePath,
                    newPhoto.getInputStream(),
                    objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Was not possible send file to amazon s3", e);
        }

    }

    private String getFilePath(String fileName) {
        return String.format("%s/%s",storageProperties.getS3().getPhotosDirectory(), fileName);
    }

    @Override
    public void remove(String fileName) {
        try {
            String filePath = getFilePath(fileName);

            var deleteObjectRequest = new DeleteObjectRequest(
                    storageProperties.getS3().getBucket(),
                    filePath
            );

            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Was not possible remove file to amazon s3", e);
        }
    }
}
