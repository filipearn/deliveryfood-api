package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.filter.DailySaleFilter;
import arn.filipe.fooddelivery.domain.model.dto.DailySale;

import java.util.List;

public interface SaleQueryService {

    List<DailySale> findDailySales(DailySaleFilter dailySaleFilter, String timeOffset);
}
