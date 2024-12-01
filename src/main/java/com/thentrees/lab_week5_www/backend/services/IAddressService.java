package com.thentrees.lab_week5_www.backend.services;

import com.thentrees.lab_week5_www.backend.dto.request.AddressRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.AddressResponse;
import com.thentrees.lab_week5_www.backend.models.Address;
import org.springframework.transaction.annotation.Transactional;

public interface IAddressService {
    @Transactional
    Address addAddress(AddressRequestDto request);

    AddressResponse getAddressById(Long id);

    @Transactional
    void updateAddress(Long id, AddressRequestDto request);

    @Transactional
    void deleteAddress(Long id);
}
