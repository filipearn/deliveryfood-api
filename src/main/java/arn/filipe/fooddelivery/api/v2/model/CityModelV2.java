package arn.filipe.fooddelivery.api.v2.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cities")
@Getter
@Setter
public class CityModelV2 extends RepresentationModel<CityModelV2> {

    @ApiModelProperty(example = "1")
    private Long cityId;

    @ApiModelProperty(example = "Belo Horizonte")
    private String cityName;

    @ApiModelProperty(example = "1")
    private Long stateId;

    @ApiModelProperty(example = "Minas Gerais")
    private String stateName;

//    private StateModel state;
}
