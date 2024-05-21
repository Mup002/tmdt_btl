package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.PaymentMethod;
@Repository
public interface PaymentMethodRepo extends JpaRepository<PaymentMethod, Long> {
    PaymentMethod findPaymentMethodById(Long id);
}
