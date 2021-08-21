package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.ItemOrderNotFoundException;
import arn.filipe.fooddelivery.domain.model.ItemOrder;
import arn.filipe.fooddelivery.domain.repository.ItemOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemOrderService {

    @Autowired
    private ItemOrderRepository itemOrderRepository;

    public ItemOrder verifyIfExistsOrThrow(Long id){
        return itemOrderRepository.findById(id)
                .orElseThrow(() -> new ItemOrderNotFoundException(id));
    }
}
