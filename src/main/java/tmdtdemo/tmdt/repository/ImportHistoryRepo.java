package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.ImportHistory;
@Repository
public interface ImportHistoryRepo extends JpaRepository<ImportHistory, Long> {
    @Query("SELECT SUM(ih.total_price) from ImportHistory ih where MONTH(ih.createdAt) = :month AND YEAR(ih.createdAt) = :year")
    Long sumTotalPriceByMonthAndYear(@Param("month") int month, @Param("year") int year);
}
