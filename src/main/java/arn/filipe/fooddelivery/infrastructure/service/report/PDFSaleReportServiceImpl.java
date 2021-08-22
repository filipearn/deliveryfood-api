package arn.filipe.fooddelivery.infrastructure.service.report;

import arn.filipe.fooddelivery.domain.filter.DailySaleFilter;
import arn.filipe.fooddelivery.domain.service.SaleQueryService;
import arn.filipe.fooddelivery.domain.service.SaleReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class PDFSaleReportServiceImpl implements SaleReportService {

    @Autowired
    private SaleQueryService saleQueryService;

    @Override
    public byte[] emitDailySales(DailySaleFilter dailySaleFilter, String timeOffset) {

        try{
            var inputStream = this.getClass().getResourceAsStream("/reports/daily-sales.jasper");

            var params = new HashMap<String, Object>();
            params.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var dailySales = saleQueryService.findDailySales(dailySaleFilter, timeOffset);

            var dataSource = new JRBeanCollectionDataSource(dailySales);

            var jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e){
            throw new ReportException("Was not possible to emit daily sales report.");
        }

    }
}
