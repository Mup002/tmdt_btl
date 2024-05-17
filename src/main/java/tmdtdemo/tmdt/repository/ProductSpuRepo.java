package tmdtdemo.tmdt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.ProductSpu;

import java.util.List;

@Repository
public interface ProductSpuRepo extends JpaRepository<ProductSpu ,Long> {
    ProductSpu findProductSpuById(Long id);
    ProductSpu findProductSpuByName(String name);
    List<ProductSpu> findProductSpusByCategoryId(Long id);
    Page<ProductSpu> findProductSpusByCategoryIdAndType(Long id,String type, Pageable pageable);
    Page<ProductSpu> findProductSpusByCategoryId(Long id, Pageable pageable);
}
