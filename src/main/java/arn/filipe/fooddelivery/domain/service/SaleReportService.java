package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.filter.DailySaleFilter;
import net.sf.jasperreports.engine.JRException;

public interface SaleReportService {

    byte[] emitDailySales(DailySaleFilter dailySaleFilter, String timeOffset) throws JRException;
}
