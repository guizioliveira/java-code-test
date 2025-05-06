package com.skidata.codingtest.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record TelephoneDTO(
	UUID id,
	@NotBlank(message = "countryCode is required")
	String countryCode,
	@NotBlank(message = "number is required")
	String number
) {
}
