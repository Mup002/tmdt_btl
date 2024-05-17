package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.Coupon;

@Repository
public interface CouponRepo extends JpaRepository<Coupon,Long> {
    Coupon findCouponById(Long id);
}
