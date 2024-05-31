package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.DailyRevenue;
@Repository
public interface DailyRevenueRepo extends JpaRepository<DailyRevenue, Long> {
}
