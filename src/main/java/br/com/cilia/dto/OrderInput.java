package br.com.cilia.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderInput(@Valid CustomerInput customer, @NotEmpty @Valid List<ItemInput> items, @Valid ShippingAddressInput shippingAddress, MetadataInput metadata) {

}
