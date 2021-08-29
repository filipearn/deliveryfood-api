package arn.filipe.fooddelivery.api.v1.openapi.controller;

import arn.filipe.fooddelivery.domain.filter.DailySaleFilter;
import arn.filipe.fooddelivery.domain.model.dto.DailySale;
import io.swagger.annotations.*;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "Statistics")
public interface StatisticsControllerOpenApi {


    @ApiOperation(value = "Find daily sales using min and max data")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restaurantId", value = "Restaurant id",
                    example = "1", dataType = "int"),
            @ApiImplicitParam(name = "registrationDateInitial", value = "Initial date/hour of purchase order registration",
                    example = "2019-12-01T00:00:00Z", dataType = "date-time"),
            @ApiImplicitParam(name = "registrationDateFinal", value = "Final date/hour of purchase order registration",
                    example = "2019-12-02T23:59:59Z", dataType = "date-time")
    })
    List<DailySale> findDailySales(DailySaleFilter dailySaleFilter,
                                   @ApiParam(value = "Time offset to be considered in the query in relation to UTC",
                                           defaultValue = "+00:00") String timeOffset);

    ResponseEntity<byte[]> findDailySalesPDF(DailySaleFilter dailySaleFilter,
                                                    @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) throws JRException;
}
