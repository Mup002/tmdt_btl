package tmdtdemo.tmdt.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImportBillRequest {
    private Long total_price;
    private String importAt;
    List<ImportProductRequest> importList = new ArrayList<>();

}
