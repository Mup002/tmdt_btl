package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.DailyRevenue;

import java.util.Date;
import java.util.List;

@Repository
public interface DailyRevenueRepo extends JpaRepository<DailyRevenue, Long> {
    @Query("SELECT dr FROM DailyRevenue dr where dr.resultDate between :start and :end")
    List<DailyRevenue> findAllDailyRevenue(@Param("start") Date start,@Param("end")Date end);
}

