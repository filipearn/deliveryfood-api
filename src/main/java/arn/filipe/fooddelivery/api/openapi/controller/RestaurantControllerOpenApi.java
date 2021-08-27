package arn.filipe.fooddelivery.api.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.model.RestaurantModel;
import arn.filipe.fooddelivery.api.model.input.RestaurantInput;
import arn.filipe.fooddelivery.api.model.view.RestaurantView;
import arn.filipe.fooddelivery.api.openapi.model.RestaurantSummaryModelOpenApi;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.List;

@Api(tags = "Restaurants")
public interface RestaurantControllerOpenApi {

    @ApiOperation(value = "List restaurants", response = RestaurantSummaryModelOpenApi.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Purchase order projection name", allowableValues = "only-name",
                    name = "projection", paramType = "query", type = "string")
    })
    List<RestaurantModel> listAll();

    @JsonView(RestaurantView.Summary.class)
    List<RestaurantModel> listAllSummary();

    @ApiOperation(value = "List restaurants", hidden = true)
    List<RestaurantModel> listAllOnlyName();


    @ApiOperation("Find a restaurant by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid restaurant id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    RestaurantModel findById(@ApiParam(value = "Restaurant id", example = "1", required = true) Long id);


    @ApiOperation("Find a restaurant by name and kitchen id")
    List<Restaurant> findByNameAndKitchen(@ApiParam(value = "Restaurant name", example = "Espeteria", required = true) String name,
                                          @ApiParam(value = "Kitchen id", example = "1", required = true) Long kitchenId);

    @ApiOperation("Find a restaurant by name, a min and max freight rate")
    List<Restaurant> findByNameAndFreightRate(@ApiParam(value = "Restaurant name", example = "Espeteria", required = true) String name,
                                              @ApiParam(value = "Freight rate min", example = "0") BigDecimal freightRateInitial,
                                              @ApiParam(value = "Freight rate max", example = "4.99") BigDecimal freightRateFinal);

    @ApiOperation("Find a restaurant with free shipping")
    List<Restaurant> withFreeShipping(@ApiParam(value = "Restaurant name", example = "Espeteria", required = true) String name);

    @ApiOperation("Find a restaurant by name, a min and max freight rate")
    ResponseEntity<?> findFirst();

    @ApiOperation("Register a new restaurant")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Restaurant created with success")
    })
    RestaurantModel save(@ApiParam(name = "body", value = "Representation of a restaurant with new data", required = true)
                                 RestaurantInput restaurantInput);


    @ApiOperation("Update a restaurant by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurant updated with success"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    RestaurantModel update(@ApiParam(value = "Restaurant id", example = "1", required = true) Long id,
                           @ApiParam(name = "body", value = "Representation of a restaurant with new data", required = true)
                                   RestaurantInput restaurantInput);

    @ApiOperation("Activate a restaurant by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurant activated with success"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    void activate(@ApiParam(value = "Restaurant id", example = "1", required = true) Long id);

    @ApiOperation("Deactivate a restaurant by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurant deactivated with success"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    void deactivate(@ApiParam(value = "Restaurant id", example = "1", required = true) Long id);

    @ApiOperation("Activate multiple restaurants by ids list")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurants activated with success"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    void multipleActivation(@ApiParam(name = "body", value = "Restaurants ids", required = true) List<Long> ids);

    @ApiOperation("Deactivate multiple restaurants by ids list")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurants deactivated with success"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    void multipleDeactivation(@ApiParam(name = "body", value = "Restaurants ids", required = true)List<Long> ids);

    @ApiOperation("Open a restaurant by id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    void opening(@ApiParam(value = "Restaurant id", example = "1", required = true) Long id);

    @ApiOperation("Close a restaurant by id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    void closure(@ApiParam(value = "Restaurant id", example = "1", required = true) Long id);

    @ApiOperation("Delete a restaurant by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid restaurant id", response = ApiError.class),
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    void delete(@ApiParam(value = "Restaurant id", example = "1", required = true) Long id);
}
