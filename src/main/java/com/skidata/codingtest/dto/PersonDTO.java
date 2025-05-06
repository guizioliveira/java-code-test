package com.skidata.codingtest.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record PersonDTO(
	UUID id,
	@NotBlank(message = "firstName is required")
	String firstName,
	@NotBlank(message = "lastName is required")
	String lastName,

	List<TelephoneDTO> telephones

) {
}
