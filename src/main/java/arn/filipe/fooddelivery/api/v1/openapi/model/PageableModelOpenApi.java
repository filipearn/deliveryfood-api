package arn.filipe.fooddelivery.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("Pageable")
@Setter
@Getter
public class PageableModelOpenApi {

    @ApiModelProperty(example = "0", value = "Page number (starts with 0)")
    private int page;

    @ApiModelProperty(example = "10", value = "Number of elements by page")
    private int size;

    @ApiModelProperty(example = "nome, asc", value = "Property name to sort")
    private List<String> sort;
}
