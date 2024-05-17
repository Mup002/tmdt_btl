package tmdtdemo.tmdt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tmdtdemo.tmdt.dto.response.PageProductSpuResponse;
import tmdtdemo.tmdt.dto.response.ProductDetailsResponse;
import tmdtdemo.tmdt.dto.response.ProductTypeResponse;
import tmdtdemo.tmdt.service.ProductService;
import tmdtdemo.tmdt.utils.AppConstants;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;
    @GetMapping("/allProductSpu")
    public ResponseEntity<PageProductSpuResponse> getAllProductWithPaginationSort(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String dir,
            @RequestParam(defaultValue = AppConstants.TYPE_DEFAULT, required = false)String type,
            @RequestParam Long categoryId
    ){
        return ResponseEntity.ok(productService.getAllProductSpuWithPageAndSort(pageNumber,pageSize,sortBy,dir,categoryId,type));
    }

    @GetMapping("/detailProduct/{id}")
    public ResponseEntity<ProductDetailsResponse> getProductDetails(@PathVariable("id")Long id){
        ProductDetailsResponse response = productService.getProductDetail(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getByCategoryAndType/{id}")
    public ResponseEntity<List<ProductTypeResponse>> getByCateAndType(@PathVariable("id") Long id){
        return ResponseEntity.ok(productService.getByCategoriesAndType(id));
    }
}
