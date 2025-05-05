package com.skidata.codingtest.service;

import com.skidata.codingtest.dto.AuthenticationDTO;
import com.skidata.codingtest.dto.RegisterDTO;
import com.skidata.codingtest.entity.User;
import com.skidata.codingtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthenticationService {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	TokenService tokenService;

	public String login(AuthenticationDTO dto) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(dto.userName(), dto.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);

		var user = (User) auth.getPrincipal();
		return tokenService.generateToken(user);
	}

	public void register(RegisterDTO dto) {
		if (this.userRepository.findByUserName(dto.userName()) != null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is not available");

		var encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
		User newUser = new User(dto.userName(), encryptedPassword, dto.role());
		this.userRepository.save(newUser);
	}
}
