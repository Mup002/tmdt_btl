package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.ImportHistory;
@Repository
public interface ImportHistoryRepo extends JpaRepository<ImportHistory, Long> {
}
