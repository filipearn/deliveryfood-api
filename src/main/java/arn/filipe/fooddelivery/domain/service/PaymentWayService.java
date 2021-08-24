package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.PaymentWayNotFoundException;
import arn.filipe.fooddelivery.domain.model.PaymentWay;
import arn.filipe.fooddelivery.domain.repository.PaymentWayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentWayService {

    public static final String PAYMENT_WAY_IN_USE = "Payment way with id %d can't be removed. Resource in use.";

    @Autowired
    private PaymentWayRepository paymentWayRepository;

    public List<PaymentWay> listAll() {
        return paymentWayRepository.findAll();
    }

    public PaymentWay findById(Long id){
        return verifyIfExistsOrThrow(id);
    }

    @Transactional
    public PaymentWay save(PaymentWay paymentWay){
        return paymentWayRepository.save(paymentWay);
    }

    @Transactional
    public void delete(Long id){
        try{
            paymentWayRepository.deleteById(id);
            paymentWayRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PaymentWayNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(PAYMENT_WAY_IN_USE, id));
        }
    }

    public PaymentWay verifyIfExistsOrThrow(Long id) {
        return paymentWayRepository.findById(id)
                .orElseThrow(() -> new PaymentWayNotFoundException(id));
    }


}
