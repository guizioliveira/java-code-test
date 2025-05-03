package com.skidata.codingtest.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skidata.codingtest.entity.Person;

public interface PersonRepository extends JpaRepository<Person, UUID> {
	Optional<Person> findByFirstName(String firstName);

	Optional<Person> findByLastName(String lastName);

	@Query("SELECT p FROM Person p JOIN p.telephones as tp WHERE tp.number = :phoneNumber")
	List<Person> findByPhone(@Param("phoneNumber") String phoneNumber);
}
