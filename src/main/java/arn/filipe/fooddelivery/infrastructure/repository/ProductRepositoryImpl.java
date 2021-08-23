package arn.filipe.fooddelivery.infrastructure.repository;

import arn.filipe.fooddelivery.domain.model.PhotoProduct;
import arn.filipe.fooddelivery.domain.repository.ProductRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public PhotoProduct save(PhotoProduct photoProduct) {
        return manager.merge(photoProduct);
    }

    @Override
    public void delete(PhotoProduct photoProduct) {
        manager.remove(photoProduct);
    }
}
