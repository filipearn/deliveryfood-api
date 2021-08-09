package arn.filipe.fooddelivery;

import arn.filipe.fooddelivery.domain.repository.CustomizedJpaRepository;
import arn.filipe.fooddelivery.infrastructure.repository.CustomizedJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomizedJpaRepositoryImpl.class)
public class FooddeliveryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FooddeliveryApiApplication.class, args);
	}

}
