package com.thentrees.lab_week5_www.backend.services.impl;

import com.thentrees.lab_week5_www.backend.dto.request.AddressRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.AddressResponse;
import com.thentrees.lab_week5_www.backend.exception.ResourceNotFoundException;
import com.thentrees.lab_week5_www.backend.mapper.AddressMapper;
import com.thentrees.lab_week5_www.backend.models.Address;
import com.thentrees.lab_week5_www.backend.repositories.AddressRepository;
import com.thentrees.lab_week5_www.backend.services.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public Address addAddress(AddressRequestDto request) {
        Address address = addressMapper.toAddress(request);
        addressRepository.save(address);
        return address;
    }

    @Override
    public AddressResponse getAddressById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Address not found")
        );
        return addressMapper.toAddressResponse(address);
    }

    @Override
    public void updateAddress(Long id, AddressRequestDto request) {
        Address address = addressRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Address not found")
        );
        updateAddress(address,request);
    }

    @Override
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Address not found")
        );
        addressRepository.delete(address);
    }

    private void updateAddress(Address address, AddressRequestDto request) {
        address.setCity(request.getCity());
        address.setZipCode(request.getZipCode());
        address.setCountry(request.getCountry());
        address.setStreet(request.getStreet());
        address.setNumber(request.getNumber());
        addressRepository.save(address);
    }
}
