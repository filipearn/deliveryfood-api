package arn.filipe.fooddelivery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface CustomizedJpaRepository<T, ID> extends JpaRepository<T, ID> {

    Optional<T> findFirst();

    void detach(T entity);
}
