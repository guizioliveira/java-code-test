package com.skidata.codingtest.repository;

import com.skidata.codingtest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
	UserDetails findByUserName(String userName);
}
