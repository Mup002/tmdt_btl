package tmdtdemo.tmdt.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductListRequest {
    private List<Long> ids = new ArrayList<>();
}
