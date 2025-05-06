package com.skidata.codingtest.dto;

import jakarta.validation.constraints.NotBlank;

public record TelephoneCreateDTO(
	@NotBlank(message = "countryCode is required")
	String countryCode,
	@NotBlank(message = "number is required")
	String number
) {
}
