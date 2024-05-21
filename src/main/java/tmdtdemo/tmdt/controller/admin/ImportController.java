package tmdtdemo.tmdt.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tmdtdemo.tmdt.dto.request.ImportBillRequest;
import tmdtdemo.tmdt.service.ImportService;
import tmdtdemo.tmdt.utils.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/import")
public class ImportController {
    private final ImportService importService;
    @PostMapping("/productImportBill")
    public ResponseEntity<BaseResponse> importProductBill(@RequestBody ImportBillRequest request,
    HttpServletRequest httprequest){
        String username = httprequest.getHeader("x-admin-username");
        if(username.isEmpty() || username == null){
            return ResponseEntity.badRequest().body(BaseResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.toString())
                    .message("Failed")
                    .build());
        }
        return ResponseEntity.ok(BaseResponse.builder()
                .code(HttpStatus.OK.toString())
                .message(importService.importDataProduct(request,username))
                .build());
    }
}
