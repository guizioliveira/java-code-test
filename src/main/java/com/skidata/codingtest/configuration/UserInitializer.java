package com.skidata.codingtest.configuration;

import com.skidata.codingtest.entity.Role;
import com.skidata.codingtest.entity.User;
import com.skidata.codingtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class UserInitializer {

	@Autowired
	UserRepository userRepository;

	public void init() {
		User user = new User("admin", new BCryptPasswordEncoder().encode("password"), Role.ADMIN);
		userRepository.save(user);
		userRepository.save(
			new User("max", new BCryptPasswordEncoder().encode("1234567"), Role.USER)
		);
	}

}
