package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.ResultRevenue;

import java.util.Date;

@Repository
public interface ResultRevenueRepo extends JpaRepository<ResultRevenue,Long> {
    ResultRevenue findResultRevenueById(Long id);

    @Query("SELECT rv FROM ResultRevenue rv where MONTH(rv.resultDate) = :month and YEAR(rv.resultDate) = :year")
    ResultRevenue findResultRevenueByResultMonth(@Param("month") int month,@Param("year") int year);

}
