package arn.filipe.fooddelivery;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.repository.KitchenRepository;
import arn.filipe.fooddelivery.domain.service.KitchenService;
import arn.filipe.fooddelivery.domain.service.RestaurantService;
import arn.filipe.fooddelivery.util.DatabaseCleaner;
import arn.filipe.fooddelivery.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class KitchenServiceIT {

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private KitchenRepository kitchenRepository;

	@Autowired
	private KitchenService kitchenService;

	@Autowired
	private RestaurantService restaurantService;

	Kitchen italianKitchen;
	Kitchen brazilianKitchen;

	private String jsonChineseKitchenRegistration;
	private Long registeredKitchenNumber;

	private static final Long NONEXISTENT_KITCHEN = -1L;

	@BeforeEach
	public void setUp(){
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = this.port;
		RestAssured.basePath = "/api/v1/kitchens";

		jsonChineseKitchenRegistration = ResourceUtils.getContentFromResource("/json/correct/KitchenRegistrationCorrect.json");

		databaseCleaner.clearTables();
		prepareData();
	}

	@Test
	public void mustReturnStatus200_WhenRequestKitchen(){

		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void mustReturnResponseECorrectStatus_WhenRequestExistentKitchen(){
		given()
			.pathParam("id", italianKitchen.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{id}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", equalTo(italianKitchen.getName()));
	}

	@Test
	public void mustReturnStatus404_WhenRequestNonExistentKitchen(){
		given()
				.pathParam("id", NONEXISTENT_KITCHEN)
				.accept(ContentType.JSON)
				.when()
				.get("/{id}")
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void mustReturnStatus201_WhenRegisterKitchen(){

		given()
				.body(jsonChineseKitchenRegistration)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void mustReturn2Kitchens_WhenRequestKitchen(){

		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(2))
			.body("name", hasItems("Brasileira"));
	}

	@Test
	public void mustSuccess_WhenKitchenRegistrationWitchCorrectData(){
		Kitchen newKitchen = new Kitchen();
		newKitchen.setName("Chinesa");

		newKitchen = kitchenService.save(newKitchen);

		Assertions.assertThatObject(newKitchen).isNotNull();
		Assertions.assertThatObject(newKitchen.getId()).isNotNull();

	}



	@Test
	public void mustFailure_WhenRegisterKitchenWithoutName(){
		Kitchen kitchen = new Kitchen();

		Assertions.assertThatThrownBy(() -> {
			kitchen.setName(null);
			kitchenService.save(kitchen);
		}).isInstanceOf(ConstraintViolationException.class);
	}


	@Test
	public void mustFailure_WhenDeleteKitchenInUse(){

		Kitchen kitchen = new Kitchen();
		kitchen.setId(1L);
		kitchen.setName("Brasileira");

		Restaurant restaurant = new Restaurant();
		restaurant.setName("Komb Hamburgueria");
		restaurant.setFreighRate(new BigDecimal(10));
		restaurant.setKitchen(kitchen);

		restaurantService.save(restaurant);

		Assertions.assertThatThrownBy(() -> {
			kitchenService.delete(kitchen.getId());
		}).isInstanceOf(DataIntegrityViolationException.class);

	}

	@Test
	public void mustFailure_WhenDeleteNonExistentKitchen(){

		Assertions.assertThatThrownBy(() -> {
			kitchenService.delete(NONEXISTENT_KITCHEN);
		}).isInstanceOf(EntityNotFoundException.class);
	}

	private void prepareData(){
		brazilianKitchen = new Kitchen();
		brazilianKitchen.setName("Brasileira");
		kitchenRepository.save(brazilianKitchen);

		italianKitchen = new Kitchen();
		italianKitchen.setName("Italiana");
		kitchenRepository.save(italianKitchen);

		registeredKitchenNumber = kitchenRepository.count();

	}

}
