package tmdtdemo.tmdt.controller;

import lombok.RequiredArgsConstructor;
import org.json.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tmdtdemo.tmdt.dto.request.AddressRequest;
import tmdtdemo.tmdt.dto.response.AddressResponse;
import tmdtdemo.tmdt.service.AddressService;
import tmdtdemo.tmdt.utils.BaseResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    @PostMapping("/add")
    public ResponseEntity<BaseResponse> add(@RequestBody AddressRequest request){
        return ResponseEntity.ok(
                BaseResponse.builder()
                        .code(HttpStatus.OK.toString())
                        .message(addressService.addNew(request)).build()
        );
    }

    @GetMapping("/loadBy/{username}")
    public ResponseEntity<List<AddressResponse>> loadByUser(@PathVariable("username") String username){
        return ResponseEntity.ok(addressService.loadAddressByUser(username));
    }
    @PostMapping("/checkAddress")
    public ResponseEntity<BaseResponse> check(@RequestBody AddressRequest request){
        return ResponseEntity.ok(
                BaseResponse.builder()
                        .code(HttpStatus.OK.toString())
                        .message(addressService.addIfDontExist(request).toString()).build()
        );
    }
}
