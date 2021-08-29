package arn.filipe.fooddelivery.api.v1.openapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedModelOpenApi<T> {

    private List<T> content;

    @ApiModelProperty(example = "10", value = "Number of registers by page")
    private Long size;

    @ApiModelProperty(example = "50", value = "Total number of registers")
    private Long totalElements;

    @ApiModelProperty(example = "5", value = "Total number of pages")
    private Long totalPages;

    @ApiModelProperty(example = "0", value = "Page number (starts with 0)")
    private Long number;
}
