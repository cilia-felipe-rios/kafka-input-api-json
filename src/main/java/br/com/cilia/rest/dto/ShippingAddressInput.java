package br.com.cilia.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record ShippingAddressInput(@NotBlank String addressLine1, String addressLine2, @NotBlank String city, @NotBlank String state, @NotBlank String postalCode, @NotBlank String country) {

}
