package com.skidata.codingtest.dto;

import jakarta.validation.constraints.NotBlank;

public record PersonCreateDTO(
	@NotBlank(message = "firstName is required")
	String firstName,
	@NotBlank(message = "lastName is required")
	String lastName
) {
}
