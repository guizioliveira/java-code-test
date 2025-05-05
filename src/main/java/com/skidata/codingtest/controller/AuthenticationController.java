package com.skidata.codingtest.controller;

import com.skidata.codingtest.dto.AuthenticationDTO;
import com.skidata.codingtest.dto.LoginResponseDTO;
import com.skidata.codingtest.dto.RegisterDTO;

import com.skidata.codingtest.entity.User;
import com.skidata.codingtest.infra.security.TokenService;
import com.skidata.codingtest.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.userName(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);

		var token = tokenService.generateToken((User) auth.getPrincipal());
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
		if (this.userRepository.findByUserName(data.userName()) != null)
			return ResponseEntity.badRequest().build();

		var encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(data.userName(), encryptedPassword, data.role());
		userRepository.save(newUser);

		return ResponseEntity.ok().build();
	}

}
