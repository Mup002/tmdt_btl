package tmdtdemo.tmdt.service;

import tmdtdemo.tmdt.dto.response.PageProductSpuResponse;
import tmdtdemo.tmdt.dto.response.ProductDetailsResponse;
import tmdtdemo.tmdt.dto.response.ProductTypeResponse;
import tmdtdemo.tmdt.entity.ProductSku;
import tmdtdemo.tmdt.entity.ProductSpu;

import java.util.List;

public interface ProductService {
    PageProductSpuResponse getAllProductSpuWithPageAndSort(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String dir,
            Long categoryId,
            String type
    );

    ProductDetailsResponse getProductDetail(Long id);
    String quantityOreder(Long id , Long ordered);
    ProductSpu findBySkuId(Long id);
    List<ProductTypeResponse> getByCategoriesAndType(Long categoryId);
    List<ProductSpu> getAllProductSpu();
    List<ProductSku> getAllProductSku(Long idSpu);
    ProductSku updateQuantity(Long skuId,Long add_quantity);
    ProductSku updateCost(Long skuId, Long new_cost);
}
