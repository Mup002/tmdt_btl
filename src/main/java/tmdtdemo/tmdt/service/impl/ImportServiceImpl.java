package tmdtdemo.tmdt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import tmdtdemo.tmdt.dto.request.ImportBillRequest;
import tmdtdemo.tmdt.dto.request.ImportProductRequest;
import tmdtdemo.tmdt.entity.ImportHistory;
import tmdtdemo.tmdt.entity.ImportProduct;
import tmdtdemo.tmdt.entity.ProductSku;
import tmdtdemo.tmdt.entity.ProductSpu;
import tmdtdemo.tmdt.exception.ResourceNotFoundException;
import tmdtdemo.tmdt.repository.ImportHistoryRepo;
import tmdtdemo.tmdt.repository.ImportProductRepo;
import tmdtdemo.tmdt.repository.ProductSkuRepo;
import tmdtdemo.tmdt.repository.ProductSpuRepo;
import tmdtdemo.tmdt.service.ImportService;
import tmdtdemo.tmdt.service.ProductService;
import tmdtdemo.tmdt.service.UserService;
import tmdtdemo.tmdt.utils.DateFormat;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService {
    private final ImportProductRepo importProductRepo;
    private final ImportHistoryRepo importHistoryRepo;
    private final ProductSkuRepo productSkuRepo;
    private final ProductSpuRepo productSpuRepo;
    private final UserService userService;
    private final ProductService productService;
    @Override
    @Transactional
    public String importDataProduct(ImportBillRequest request,String username) {
        ImportHistory history = new ImportHistory();
        history.setImportAt(DateFormat.convertStringToDate(request.getImportAt()));
        history.setUser(userService.findUserByUsername(username));
        history.setTotal_price(request.getTotal_price());
        history.setPayment_status(true);
        importHistoryRepo.save(history);
        for(ImportProductRequest productRequest : request.getImportList()){
            ImportProduct product = new ImportProduct();
            product.setPrice(productRequest.getPrice());
            product.setQuantity(productRequest.getQuantity());
            ProductSpu spu = productSpuRepo.findProductSpuById(productRequest.getSpuId());
            if(ObjectUtils.isEmpty(spu)){
                throw new ResourceNotFoundException("Khong tim thay san pham nay");
            }
            product.setProductSpu(spu);
            if(productRequest.getSkuId() != null){
                ProductSku sku = productSkuRepo.findProductSkuById(productRequest.getSkuId());
                product.setProductSku(sku);
            }
            product.setImportHistory(history);
            importProductRepo.save(product);
            productService.updateQuantity(productRequest.getSkuId(),productRequest.getQuantity());
        }

        return "Import successfully";
    }
}
