package com.skidata.codingtest.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidateExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationErrors(
		MethodArgumentNotValidException ex,
		HttpServletRequest request
	) {
		String message = ex.getBindingResult()
			.getFieldErrors().stream()
			.map(FieldError::getDefaultMessage)
			.collect(Collectors.joining("; "));

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", Instant.now().toString());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("error", "Bad Request");
		body.put("message", message);
		body.put("path", request.getRequestURI());

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
}
