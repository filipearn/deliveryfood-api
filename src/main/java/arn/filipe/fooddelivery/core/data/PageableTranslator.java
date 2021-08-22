package arn.filipe.fooddelivery.core.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;
import java.util.stream.Collectors;

public class PageableTranslator {

    public static Pageable translate(Pageable pageable, Map<String, String> mapping){
        var orders = pageable.getSort().stream()
                .filter(order -> mapping.containsKey(order.getProperty()))
                .map(order -> new Sort.Order(order.getDirection(), mapping.get(order.getProperty())))
                .collect(Collectors.toList());

        System.out.println(orders);

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(orders));
    }
}
