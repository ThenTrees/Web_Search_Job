package com.thentrees.lab_week5_www.backend.controller;


import com.thentrees.lab_week5_www.backend.dto.request.AddressRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.AddressResponse;
import com.thentrees.lab_week5_www.backend.dto.response.ResponseObject;
import com.thentrees.lab_week5_www.backend.models.Address;
import com.thentrees.lab_week5_www.backend.services.IAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/addresses")
@RequiredArgsConstructor
@Slf4j
public class AddressController {
    private final IAddressService addressService;

    // add address
    @PostMapping
    public ResponseEntity<ResponseObject> addAddress(@RequestBody AddressRequestDto addressRequestDto){
        log.info("insert Address begin");
        Address addressResponse = addressService.addAddress(addressRequestDto);
        log.info("insert Address end");
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ResponseObject.builder()
                        .code(HttpStatus.CREATED.value())
                        .data(addressResponse)
                        .message("Address added successfully")
                        .build()
            );
    }

    /*
         get address by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getAddressById(@PathVariable Long id){
        log.info("get Address by id {} begin", id);
        AddressResponse addressResponse = addressService.getAddressById(id);
        log.info("get Address by id end");
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ResponseObject.builder()
                        .code(HttpStatus.OK.value())
                        .data(addressResponse)
                        .message("Address retrieved successfully")
                        .build()
            );
    }

    /*
         update address
     */

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateAddress(@PathVariable Long id, @RequestBody AddressRequestDto addressRequestDto){
        log.info("update Address by id {} begin", id);
        addressService.updateAddress(id, addressRequestDto);
        log.info("update Address by id end");
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ResponseObject.builder()
                        .code(HttpStatus.OK.value())
                        .message("Address updated successfully")
                        .build()
            );
    }

    /*
         delete address
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteAddress(@PathVariable Long id){
        log.info("delete Address by id {} begin", id);
        addressService.deleteAddress(id);
        log.info("delete Address by id end");
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ResponseObject.builder()
                        .code(HttpStatus.OK.value())
                        .message("Address deleted successfully")
                        .build()
            );
    }

}
