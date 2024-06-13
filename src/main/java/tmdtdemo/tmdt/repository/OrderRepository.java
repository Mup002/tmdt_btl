package tmdtdemo.tmdt.repository;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.OrderDetails;
import tmdtdemo.tmdt.entity.ProductSku;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetails,Long> {
    OrderDetails findOrderDetailsByCode
            (String code);

    List<OrderDetails> findOrderDetailsByUserUsername(String username);
    //custom query
    @Query("SELECT SUM(od.total) FROM OrderDetails od WHERE od.status = 'DONE' AND MONTH(od.createdAt) = :month AND YEAR(od.createdAt) = :year")
    Long sumTotalByMonthAndYearAndStatus(@Param("month") int month, @Param("year") int year);

    @Query("SELECT SUM(od.total) FROM OrderDetails  od where od.status ='DONE' AND DAY(od.createdAt) =:day AND MONTH(od.createdAt) =:month AND YEAR(od.createdAt) =:year")
    Long sumTotalByDay(@Param("day") int day, @Param("month") int month , @Param("year") int year);

    @Query("SELECT od FROM OrderDetails  od where MONTH(od.createdAt) = :month AND YEAR(od.createdAt) = :year AND od.status = 'DONE'")
    List<OrderDetails> findAllOrderByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT od FROM OrderDetails  od where DATE(od.createdAt) = :createAt AND od.status='DONE'")
    List<OrderDetails> findAllOrderByDay(@Param("createAt") Date createAt);

    @Query(value = "SELECT SUM(od.total) FROM orderdetails od WHERE od.user_id = :userId", nativeQuery = true)
    Long sumTotalByUser(@Param("userId") Long userId);

    @Query("SELECT COUNT(*) FROM OrderDetails WHERE MONTH(createdAt) = :month AND YEAR(createdAt) = :year")
    Long countOrdersByMonth(@Param("month") int month, @Param("year") int year);
}
