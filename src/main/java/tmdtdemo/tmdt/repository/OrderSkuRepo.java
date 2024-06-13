package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.OrderSku;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderSkuRepo extends JpaRepository<OrderSku,Long> {
    List<OrderSku> findOrderSkusByOrderDetailsId(Long id);

    @Query(value = "SELECT SUM(os.quantity) FROM orderskus os JOIN orderdetails od ON os.orderdetail_id = od.orderdetail_id WHERE od.status = 'DONE' AND od.created_at BETWEEN :startDate AND :endDate", nativeQuery = true)
    Long sumQuantityByOrderDetailsCreatedAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT os.sku_id FROM orderskus os JOIN orderdetails od ON os.orderdetail_id = od.orderdetail_id WHERE od.status = 'DONE' AND od.created_at BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Long> findSkuIdsByOrderDetailsCreatedAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT SUM(osk.quantity) " +
            "FROM orderskus osk " +
            "JOIN productskus psk ON osk.sku_id = psk.productsku_id " +
            "WHERE psk.productspu_id = :productSpuId", nativeQuery = true)
    Long sumTotalQuantityByProductSpuId(@Param("productSpuId") Long productSpuId);
}
