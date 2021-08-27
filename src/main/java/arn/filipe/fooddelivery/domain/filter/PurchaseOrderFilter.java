package arn.filipe.fooddelivery.domain.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Setter
@Getter
public class PurchaseOrderFilter {

    @ApiModelProperty(example = "1", value = "Client id for search filter")
    private Long clientId;

    @ApiModelProperty(example = "1", value = "Restaurant id for search filter")
    private Long restaurantId;

    @ApiModelProperty(example = "2019-10-30T00:00:00Z",
            value = "Initial registration date/hour for search filter")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime registrationDateInitial;

    @ApiModelProperty(example = "2019-11-01T00:00:00Z",
            value = "Final registration date/hour for search filter")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime registrationDateFinal;
}
