package com.thentrees.lab_week5_www.backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequestDto {

    @NotEmpty(message = "City is required")
    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
    private String city;

    @NotEmpty(message = "Zip code is required")
    @Size(min = 1, max = 7, message = "Zip code must be between 1 and 7 characters")
    private String zipCode;

    @NotBlank(message = "Country is required")
    @Positive(message = "Country must be a positive number")
    private Short country;

    @NotBlank(message = "Street is required")
    @Size(min = 2, max = 150, message = "Street must be between 2 and 50 characters")
    private String street;

    @NotBlank(message = "Number is required")
    @Size(min = 2, max = 20, message = "NUmbwe must be between 2 and 50 characters")
    private String number;
}
