package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.ProductSku;

@Repository
public interface ProductSkuRepo extends JpaRepository<ProductSku,Long> {
    ProductSku findProductSkuById(Long id);
    ProductSku findProductSkuByColor(String color);
    ProductSku findProductSkuByColorAndProductSpuId(String color, Long spuId);
    //custom query
    @Query(value = "select MIN(price) from productskus where productspu_id = :productSpuId",nativeQuery = true)
    Double findMinPriceByProductSpuId(Long productSpuId);

}
