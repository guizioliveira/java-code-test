package com.skidata.codingtest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.skidata.codingtest.entity.Person;
import com.skidata.codingtest.entity.Telephone;
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
	List<Person> findAll() {
		return telephoneBookService.findAll();
	}

	@GetMapping("/person/findByFirstName/{firstName}")
	Person findPersonByFirstName(@PathVariable String firstName) {
		return telephoneBookService.findByFirstName(firstName);
	}

	@GetMapping("/person/findByLastName/{lastName}")
	Person findPersonByLastName(@PathVariable String lastName) {
		return telephoneBookService.findByLastName(lastName);
	}

	@GetMapping("/person/findByNumber/{phoneNumber}")
	List<Person> findPersonByPhoneNumber(@PathVariable String phoneNumber) {
		return telephoneBookService.findPersonByPhoneNumber(phoneNumber);
	}

	@GetMapping("/person/search")
	Page<Person> searchPerson(@RequestParam("query") String query,
		@PageableDefault(size = 20) Pageable pageable) {
		return telephoneBookService.findPersonByQuery(query, pageable);
	}

	@PostMapping("/person")
	Person addPerson(@RequestBody Person person) {
		return telephoneBookService.savePerson(person);
	}

	@PutMapping("/person/{id}")
	Person updatePerson(@PathVariable String id, @RequestBody Person person) {
		return telephoneBookService.updatePerson(UUID.fromString(id), person);
	}

	@PutMapping("/person/{id}/telephone")
	Person addPhoneNumber(@PathVariable String id, @RequestBody Telephone telephone) {
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
