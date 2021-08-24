package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.PhotoProductNotFound;
import arn.filipe.fooddelivery.domain.model.PhotoProduct;
import arn.filipe.fooddelivery.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class PhotoProductService {

    @Autowired
    private PhotoStorageService photoStorageService;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public PhotoProduct save(PhotoProduct photoProduct, InputStream inputStream){
        Long restaurantId = photoProduct.getProduct().getRestaurant().getId();
        Long productId = photoProduct.getProduct().getId();

        String newFileName = photoStorageService.generateFileName(photoProduct.getFileName());
        String existentFileName = null;

        Optional<PhotoProduct> existentPhoto = productRepository.
                findPhotoById(restaurantId, productId);

        if(existentPhoto.isPresent()){
            existentFileName = existentPhoto.get().getFileName();
            productRepository.delete(existentPhoto.get());
        }

        photoProduct.setFileName(newFileName);
        photoProduct = productRepository.save(photoProduct);
        productRepository.flush();

        PhotoStorageService.NewPhoto newPhoto = PhotoStorageService.NewPhoto.builder()
                        .fileName(photoProduct.getFileName())
                        .contentType(photoProduct.getContentType())
                        .inputStream(inputStream)
                        .build();

        photoStorageService.replace(existentFileName, newPhoto);

        return photoProduct;
    }

    public PhotoProduct find(Long restaurantId, Long productId) throws Exception {
        PhotoProduct photoProduct = productRepository.findPhotoById(restaurantId, productId)
                .orElseThrow(() -> new PhotoProductNotFound(restaurantId, productId));

        return photoProduct;
    }

    public PhotoStorageService.RecoveredPhoto servePhoto(Long restaurantId, Long productId) throws Exception {
        PhotoProduct photoProduct = productRepository.findPhotoById(restaurantId, productId)
                .orElseThrow(() -> new PhotoProductNotFound(restaurantId, productId));

        return photoStorageService.recover(photoProduct.getFileName());
    }

    @Transactional
    public void deletePhoto(Long restaurantId, Long productId) {
        PhotoProduct photoProduct = productRepository.findPhotoById(restaurantId, productId)
                .orElseThrow(() -> new PhotoProductNotFound(restaurantId, productId));

        productRepository.delete(photoProduct);
        productRepository.flush();
        photoStorageService.remove(photoProduct.getFileName());
    }
}
