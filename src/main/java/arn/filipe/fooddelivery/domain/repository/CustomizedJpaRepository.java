package arn.filipe.fooddelivery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CustomizedJpaRepository<T, ID> extends JpaRepository<T, ID> {

    Optional<T> findFirst();
}
