package arn.filipe.fooddelivery;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.model.Product;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.repository.KitchenRepository;
import arn.filipe.fooddelivery.domain.repository.ProductRepository;
import arn.filipe.fooddelivery.domain.repository.RestaurantRepository;
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

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class RestaurantServiceIT {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private ProductRepository productRepository;

    @LocalServerPort
    private int port;

    private String jsonOPassoRestaurantRegistration;
    private String jsonRestaurantRegistrationNonExistentKitchen;
    private String jsonRestaurantRegistrationWithoutRestaurantName;
    private String jsonRestaurantRegistrationWithoutKitchen;
    private String jsonRestaurantRegistrationWithoutFreithRate;

    private Restaurant kombRestaurant;
    private Restaurant comidaMineiraRestaurant;
    private Restaurant oPassoRestaurant;

    private Product cervejaWallsProduct;

    private Long registeredRestaurantNumber;

    private static final Long RESTAURANT_NONEXISTENT = -1L;


    @BeforeEach
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1/restaurants";

        //Incorrect
        jsonOPassoRestaurantRegistration = ResourceUtils.getContentFromResource("/json/incorrect/RestaurantRegistrationCorrect.json");
        jsonRestaurantRegistrationWithoutRestaurantName = ResourceUtils.getContentFromResource("/json/incorrect/RestaurantRegistrationWithoutRestaurantName.json");
        jsonRestaurantRegistrationWithoutKitchen = ResourceUtils.getContentFromResource("/json/incorrect/RestaurantRegistrationWithoutKitchen.json");
        jsonRestaurantRegistrationWithoutFreithRate = ResourceUtils.getContentFromResource("/json/incorrect/RestaurantRegistrationWithoutFreighRate.json");
        jsonRestaurantRegistrationNonExistentKitchen = ResourceUtils.getContentFromResource("/json/incorrect/RestaurantRegistrationNonExistentKitchen.json");

        //Correct
        jsonOPassoRestaurantRegistration = ResourceUtils.getContentFromResource("/json/correct/RestaurantRegistrationCorrect.json");

        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    public void mustReturnStatus200_WhenRequestRestaurant(){
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void mustReturnStatus400_WhenRegisterRestaurantWithoutRestaurantName(){
        given()
            .body(jsonRestaurantRegistrationWithoutRestaurantName)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void mustReturnStatus400_WhenRegisterRestaurantNonExistentKitchen(){
        given()
                .body(jsonRestaurantRegistrationNonExistentKitchen)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void mustReturnStatus400_WhenRegisterRestaurantWithoutKitchen(){
        given()
            .body(jsonRestaurantRegistrationWithoutKitchen)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void mustReturnStatus400_WhenRegisterRestaurantWithoutFreighRate(){
        given()
            .body(jsonRestaurantRegistrationWithoutFreithRate)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void mustReturnStatus201_WhenRegisterCorrectRestaurant(){
        given()
            .body(jsonOPassoRestaurantRegistration)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void mustReturnResponseECorrectStatus_WhenRequestExistentRestaurant(){
        given()
            .pathParam("id", kombRestaurant.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("name", equalTo(kombRestaurant.getName()));
    }



    @Test
    public void mustReturnStatus404_WhenRequestNonExistentRestaurant(){
        given()
            .pathParam("id", RESTAURANT_NONEXISTENT)
            .accept(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void mustReturnStatus404_WhenDeleteNonExistentRestaurant(){
        given()
            .pathParam("id", RESTAURANT_NONEXISTENT)
            .accept(ContentType.JSON)
        .when()
            .delete("/{id}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void mustReturnStatus404_WhenDeleteExistentRestaurant(){
        given()
            .pathParam("id", comidaMineiraRestaurant.getId())
            .accept(ContentType.JSON)
        .when()
            .delete("/{id}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void mustFailure_WhenDeleteInUseRestaurant(){

        Assertions.assertThatThrownBy(() -> {
            restaurantService.delete(kombRestaurant.getId());
        }).isInstanceOf(DataIntegrityViolationException.class);

    }

//    @Test
//    public void mustReturnStatus404_WhenRequestNonExistentRestaurant(){
//        given()
//                .pathParam("id", RESTAURANT_NONEXISTENT)
//                .accept(ContentType.JSON)
//                .when()
//                .get("/{id}")
//                .then()
//                .statusCode(HttpStatus.NOT_FOUND.value());
//    }

    private void prepareData(){
        //Insert Kitchen
        Kitchen italianKitchen = new Kitchen();
        italianKitchen.setName("Italiana");
        italianKitchen = kitchenRepository.save(italianKitchen);

        Kitchen brazilianKitchen = new Kitchen();
        brazilianKitchen.setName("Brasileira");
        brazilianKitchen = kitchenRepository.save(brazilianKitchen);

        //Insert Restaurant
        kombRestaurant = new Restaurant();
        kombRestaurant.setName("KOMB HAMBURGUERIA");
        kombRestaurant.setFreighRate(new BigDecimal(10));
        kombRestaurant.setKitchen(italianKitchen);
        kombRestaurant = restaurantRepository.save(kombRestaurant);

        comidaMineiraRestaurant = new Restaurant();
        comidaMineiraRestaurant.setName("COMIDA MINEIRA");
        comidaMineiraRestaurant.setFreighRate(new BigDecimal(5));
        comidaMineiraRestaurant.setKitchen(brazilianKitchen);
        comidaMineiraRestaurant = restaurantRepository.save(comidaMineiraRestaurant);

        //Insert Product
        cervejaWallsProduct = new Product();
        cervejaWallsProduct.setName("Cerveja Walls");
        cervejaWallsProduct.setDescription("Cerveja Artesanal");
        cervejaWallsProduct.setPrice(new BigDecimal(9.99));
        cervejaWallsProduct.setRestaurant(kombRestaurant);
        cervejaWallsProduct.setActive(true);
        cervejaWallsProduct = productRepository.save(cervejaWallsProduct);

        registeredRestaurantNumber = restaurantRepository.count();

    }
}
