package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.assembler.UserInputDisassembler;
import arn.filipe.fooddelivery.api.assembler.UserModelAssembler;
import arn.filipe.fooddelivery.api.model.UserModel;
import arn.filipe.fooddelivery.api.openapi.controller.RestaurantUserControllerOpenApi;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.service.RestaurantService;
import arn.filipe.fooddelivery.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/v1/restaurants/{restaurantId}/responsible", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantUserController implements RestaurantUserControllerOpenApi {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @Autowired
    private UserInputDisassembler userInputDisassembler;

    @Autowired
    private UserService userService;

    @GetMapping
    public CollectionModel<UserModel> listAll(@PathVariable Long restaurantId){
        Restaurant restaurant = restaurantService.verifyIfExistsOrThrow(restaurantId);

        return userModelAssembler.toCollectionModel(restaurant.getUsers())
                .removeLinks()
                .add(linkTo(methodOn(RestaurantUserController.class)
                        .listAll(restaurantId)).withSelfRel());
    }

    @PutMapping("/{userId}")
    public void associateUser(@PathVariable Long restaurantId,@PathVariable Long userId){
        restaurantService.associateUser(restaurantId, userId);
    }

    @DeleteMapping("/{userId}")
    public void disassociateUser(@PathVariable Long restaurantId, @PathVariable Long userId){
        restaurantService.disassociateUser(restaurantId, userId);
    }
}
