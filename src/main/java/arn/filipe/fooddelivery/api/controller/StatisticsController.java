package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.openapi.controller.StatisticsControllerOpenApi;
import arn.filipe.fooddelivery.domain.filter.DailySaleFilter;
import arn.filipe.fooddelivery.domain.model.dto.DailySale;
import arn.filipe.fooddelivery.domain.service.SaleQueryService;
import arn.filipe.fooddelivery.domain.service.SaleReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/statistics")
public class StatisticsController implements StatisticsControllerOpenApi {

    @Autowired
    private SaleReportService saleReportService;

    @Autowired
    private SaleQueryService saleQueryService;

    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailySale> findDailySales(DailySaleFilter dailySaleFilter,
                                          @RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
        return saleQueryService.findDailySales(dailySaleFilter, timeOffset);
    }

    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> findDailySalesPDF(DailySaleFilter dailySaleFilter,
                                            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) throws JRException {

        byte[] bytesPDF = saleReportService.emitDailySales(dailySaleFilter, timeOffset);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPDF);
    }
}
