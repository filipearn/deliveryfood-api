package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.assembler.PhotoProductModelAssembler;
import arn.filipe.fooddelivery.api.model.PhotoProductModel;
import arn.filipe.fooddelivery.api.model.input.PhotoProductInput;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.PhotoProduct;
import arn.filipe.fooddelivery.domain.model.Product;
import arn.filipe.fooddelivery.domain.service.PhotoProductService;
import arn.filipe.fooddelivery.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {

    @Autowired
    private ProductService productService;

    @Autowired
    private PhotoProductService photoProductService;

    @Autowired
    private PhotoProductModelAssembler photoProductModelAssembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PhotoProductModel find(@PathVariable Long restaurantId,
                                  @PathVariable Long productId) throws Exception {
        Product product = productService.verifyIfExistsOrThrow(restaurantId, productId);

        PhotoProduct photoProduct = photoProductService.find(restaurantId, productId);

        return photoProductModelAssembler.toModel(photoProduct);
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> servePhoto(@PathVariable Long restaurantId,
                                                          @PathVariable Long productId,
                                                          @RequestHeader(name = "accept") String acceptHeader) throws Exception {
        try{
            Product product = productService.verifyIfExistsOrThrow(restaurantId, productId);

            PhotoProduct photoProduct = photoProductService.find(restaurantId, productId);

            List<MediaType> mediaTypeAllowed = MediaType.parseMediaTypes(acceptHeader);
            MediaType mediaTypePhoto = MediaType.parseMediaType(photoProduct.getContentType());

            verifyMediaTypesCompatibility(mediaTypePhoto, mediaTypeAllowed);

            InputStream inputStream = photoProductService.servePhoto(restaurantId, productId);

            return ResponseEntity.ok()
                    .contentType(mediaTypePhoto)
                    .body(new InputStreamResource(inputStream));
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }

    private void verifyMediaTypesCompatibility(MediaType mediaTypePhoto, List<MediaType> mediaTypeAllowed) throws HttpMediaTypeNotAcceptableException {
        boolean compatible = mediaTypeAllowed.stream()
                .anyMatch(mediaTypeAccepted -> mediaTypeAccepted.isCompatibleWith(mediaTypePhoto));

        if(!compatible){
            throw new HttpMediaTypeNotAcceptableException(mediaTypeAllowed);
        }
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PhotoProductModel updatePhoto(@PathVariable Long restaurantId,
                                         @PathVariable Long productId,
                                         @Valid PhotoProductInput productPhotoInput) throws IOException {

        Product product = productService.verifyIfExistsOrThrow(restaurantId, productId);

        MultipartFile file = productPhotoInput.getFile();

        PhotoProduct photo = new PhotoProduct();
        photo.setProduct(product);
        photo.setDescription(productPhotoInput.getDescription());
        photo.setContentType(file.getContentType());
        photo.setSize(file.getSize());
        photo.setFileName(file.getOriginalFilename());

        return photoProductModelAssembler.toModel(photoProductService.save(photo, file.getInputStream()));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePhoto(@PathVariable Long restaurantId,
                                         @PathVariable Long productId){
        photoProductService.deletePhoto(restaurantId, productId);
    }
}
