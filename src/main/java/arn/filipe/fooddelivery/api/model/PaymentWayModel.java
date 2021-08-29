package arn.filipe.fooddelivery.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "paymentWays")
@Getter
@Setter
public class PaymentWayModel extends RepresentationModel<PaymentWayModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Cartão de crédito")
    private String description;
}
