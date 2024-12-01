package com.thentrees.lab_week5_www.backend.mapper;

import com.thentrees.lab_week5_www.backend.dto.request.AddressRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.AddressResponse;
import com.thentrees.lab_week5_www.backend.models.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public Address toAddress(AddressRequestDto request){
        return Address.builder()
                .city(request.getCity())
                .zipCode(request.getZipCode())
                .country(request.getCountry())
                .street(request.getStreet())
                .number(request.getNumber())
                .build();
    }

    public AddressResponse toAddressResponse(Address address){
        return AddressResponse.builder()
                .id(address.getId())
                .city(address.getCity())
                .zipCode(address.getZipCode())
                .country(address.getCountry())
                .street(address.getStreet())
                .number(address.getNumber())
                .candidate(address.getCandidate())
                .company(address.getCompany())
                .build();
    }
}
