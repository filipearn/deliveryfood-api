package arn.filipe.fooddelivery.domain.repository;

import arn.filipe.fooddelivery.domain.model.PhotoProduct;

public interface ProductRepositoryQueries {

    PhotoProduct save(PhotoProduct photoProduct);

    void delete(PhotoProduct photoProduct);
}
