package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.OrderSku;

import java.util.List;

@Repository
public interface OrderSkuRepo extends JpaRepository<OrderSku,Long> {
    List<OrderSku> findOrderSkusByOrderDetailsId(Long id);
}
