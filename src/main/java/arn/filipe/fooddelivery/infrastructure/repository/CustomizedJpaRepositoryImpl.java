package arn.filipe.fooddelivery.infrastructure.repository;

import arn.filipe.fooddelivery.domain.repository.CustomizedJpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CustomizedJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomizedJpaRepository<T, ID> {

    private EntityManager manager;
    public CustomizedJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
                                       EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.manager = entityManager;
    }

    @Override
    public Optional<T> findFirst() {
        var jpql = "from " + getDomainClass().getName();

        T entity = manager.createQuery(jpql, getDomainClass())
                .setMaxResults(1)
                .getSingleResult();

        return Optional.ofNullable(entity);
    }
}
