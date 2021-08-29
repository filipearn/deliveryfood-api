package arn.filipe.fooddelivery.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "photos")
@Getter
@Setter
public class PhotoProductModel extends RepresentationModel<PhotoProductModel> {

    @ApiModelProperty(example = "Chorizzo.png")
    private String fileName;

    @ApiModelProperty(example = "Chorizzo servido ao ponto menos")
    private String description;

    @ApiModelProperty(example = "image/png")
    private String contentType;

    @ApiModelProperty(example = "202912")
    private Long size;
}
