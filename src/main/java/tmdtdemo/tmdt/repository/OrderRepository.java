package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.OrderDetails;
@Repository
public interface OrderRepository extends JpaRepository<OrderDetails,Long> {
    OrderDetails findOrderDetailsByCode
            (String code);

    //custom query
    @Query("SELECT SUM(od.total) FROM OrderDetails od WHERE od.status = 'DONE' AND MONTH(od.createdAt) = :month AND YEAR(od.createdAt) = :year")
    Long sumTotalByMonthAndYearAndStatus(@Param("month") int month, @Param("year") int year);
}
