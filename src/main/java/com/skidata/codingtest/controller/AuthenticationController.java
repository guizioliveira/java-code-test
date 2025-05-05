package com.skidata.codingtest.controller;

import com.skidata.codingtest.dto.AuthenticationDTO;
import com.skidata.codingtest.dto.LoginResponseDTO;
import com.skidata.codingtest.dto.RegisterDTO;

import com.skidata.codingtest.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired
	AuthenticationService authService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
		String jwt = authService.login(data);
		return ResponseEntity.ok(new LoginResponseDTO(jwt));
	}

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
		authService.register(data);
		return ResponseEntity.ok().build();
	}

}
