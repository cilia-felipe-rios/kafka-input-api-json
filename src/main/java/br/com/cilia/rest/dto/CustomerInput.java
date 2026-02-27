package br.com.cilia.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerInput(String id, @NotBlank String name, @NotBlank @Email String email) {

}
