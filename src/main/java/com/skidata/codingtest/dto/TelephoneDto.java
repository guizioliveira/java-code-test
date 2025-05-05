package com.skidata.codingtest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelephoneDto {
	private UUID id;

	@NotBlank(message = "number is required")
	private String number;

	@NotBlank(message = "countryCode is required")
	private String countyCode;
}
