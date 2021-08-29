package arn.filipe.fooddelivery.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "addresses")
@Getter
@Setter
public class AddressModel extends RepresentationModel<AddressModel> {

    @ApiModelProperty(example = "35400-000")
    private String cep;

    @ApiModelProperty(example = "Rua Conde Bobadela")
    private String street;

    @ApiModelProperty(example = "50")
    private String number;

    @ApiModelProperty(example = "A")
    private String complement;

    @ApiModelProperty(example = "Centro")
    private String district;

    private CitySummaryModel city;
}
