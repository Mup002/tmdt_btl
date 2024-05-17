package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.OrderDetails;
@Repository
public interface OrderRepository extends JpaRepository<OrderDetails,Long> {
}
