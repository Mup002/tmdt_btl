package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.ShippingDetails;

@Repository
public interface ShippingDetailsRepo extends JpaRepository<ShippingDetails,Long> {
    ShippingDetails findShippingDetailsById(Long id);
}
