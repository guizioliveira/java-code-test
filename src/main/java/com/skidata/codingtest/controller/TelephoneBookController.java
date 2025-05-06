package com.skidata.codingtest.controller;

import java.util.List;
import java.util.UUID;

import com.skidata.codingtest.dto.PersonCreateDTO;
import com.skidata.codingtest.dto.PersonDTO;
import com.skidata.codingtest.dto.TelephoneCreateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.skidata.codingtest.service.TelephoneBookService;

@RestController
public class TelephoneBookController {

	@Autowired
	TelephoneBookService telephoneBookService;

	@GetMapping("/ping")
	String ping() {
		return "pong";
	}

	@GetMapping("/person/list")
	List<PersonDTO> findAll() {
		return telephoneBookService.findAll();
	}

	@GetMapping("/person/findByFirstName/{firstName}")
	PersonDTO findPersonByFirstName(@PathVariable String firstName) {
		return telephoneBookService.findByFirstName(firstName);
	}

	@GetMapping("/person/findByLastName/{lastName}")
	PersonDTO findPersonByLastName(@PathVariable String lastName) {
		return telephoneBookService.findByLastName(lastName);
	}

	@GetMapping("/person/findByNumber/{phoneNumber}")
	List<PersonDTO> findPersonByPhoneNumber(@PathVariable String phoneNumber) {
		return telephoneBookService.findPersonByPhoneNumber(phoneNumber);
	}

	@GetMapping("/person/search")
	Page<PersonDTO> searchPerson(@RequestParam("query") String query,
		@PageableDefault(size = 20) Pageable pageable) {
		return telephoneBookService.findPersonByQuery(query, pageable);
	}

	@PostMapping("/person")
	ResponseEntity<PersonDTO> addPerson(@RequestBody @Valid PersonCreateDTO person) {
		PersonDTO created = telephoneBookService.savePerson(person);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping("/person/{id}")
	PersonDTO updatePerson(@PathVariable String id, @RequestBody @Valid PersonCreateDTO person) {
		return telephoneBookService.updatePerson(UUID.fromString(id), person);
	}

	@PutMapping("/person/{id}/telephone")
	PersonDTO addPhoneNumber(@PathVariable String id, @RequestBody @Valid TelephoneCreateDTO telephone) {
		return telephoneBookService.addTelephone(UUID.fromString(id), telephone);
	}

	@DeleteMapping("/person/{id}/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deletePerson(@PathVariable String id) {
		telephoneBookService.deletePersonById(UUID.fromString(id));
	}

	@DeleteMapping("/person/{id}/telephone/{telephoneId}/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deletePersonTelephone(@PathVariable String id, @PathVariable String telephoneId) {
		telephoneBookService.deletePersonTelephone(UUID.fromString(id), UUID.fromString(telephoneId));
	}

}
