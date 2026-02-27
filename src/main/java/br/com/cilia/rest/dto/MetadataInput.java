package br.com.cilia.rest.dto;

import br.com.cilia.rest.dto.enums.PriorityLevel;

public record MetadataInput(String source, PriorityLevel priority, String notes) {

}
