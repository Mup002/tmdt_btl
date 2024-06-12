package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.Carrier;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier,Long> {
    Carrier findCarrierByShortname(String shortname);
    Carrier findCarrierByCarrier(String carrier);
}
