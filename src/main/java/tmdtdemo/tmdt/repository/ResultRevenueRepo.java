package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.ResultRevenue;

@Repository
public interface ResultRevenueRepo extends JpaRepository<ResultRevenue,Long> {
    ResultRevenue findResultRevenueById(Long id);


}
