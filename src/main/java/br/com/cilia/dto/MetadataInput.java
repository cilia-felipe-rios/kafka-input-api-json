package br.com.cilia.dto;

import br.com.cilia.dto.enums.PriorityLevel;

public record MetadataInput(String source, PriorityLevel priority, String notes) {

}
