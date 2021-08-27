package arn.filipe.fooddelivery.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoProductModel {

    @ApiModelProperty(example = "Chorizzo.png")
    private String fileName;

    @ApiModelProperty(example = "Chorizzo servido ao ponto menos")
    private String description;

    @ApiModelProperty(example = "image/png")
    private String contentType;

    @ApiModelProperty(example = "202912")
    private Long size;
}
