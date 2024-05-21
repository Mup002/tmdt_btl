package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.ImportProduct;

@Repository
public interface ImportProductRepo extends JpaRepository<ImportProduct,Long> {
}
