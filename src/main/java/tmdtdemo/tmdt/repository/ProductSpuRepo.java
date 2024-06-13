package tmdtdemo.tmdt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT ps.type FROM productspus ps JOIN productskus psk ON ps.productspu_id = psk.productspu_id WHERE psk.productsku_id = :skuId", nativeQuery = true)
    String findTypeByProductSkuId(@Param("skuId") Long skuId);

    @Query(value = "SELECT ps.productspu_name " +
            "FROM productspus ps " +
            "JOIN productskus psk ON ps.productspu_id = psk.productspu_id " +
            "JOIN orderskus osk ON psk.productsku_id = osk.sku_id " +
            "GROUP BY ps.productspu_id " +
            "ORDER BY SUM(osk.quantity) DESC " +
            "LIMIT 10", nativeQuery = true)
    List<String> findTop10ProductSpuNamesByTotalQuantity();
}
