package br.com.cilia.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ItemInput(@NotBlank String sku, @NotBlank String description, @Min(1) @Max(99) Integer quantity, @Min(0) BigDecimal unitPrice) {

}
