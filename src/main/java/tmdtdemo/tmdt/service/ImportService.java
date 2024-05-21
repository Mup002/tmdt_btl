package tmdtdemo.tmdt.service;

import tmdtdemo.tmdt.dto.request.ImportBillRequest;

public interface ImportService {
    String importDataProduct(ImportBillRequest request, String username);
}
