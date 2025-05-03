package com.skidata.codingtest.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.skidata.codingtest.entity.Person;
import com.skidata.codingtest.entity.Telephone;
import com.skidata.codingtest.repository.PersonRepository;
import com.skidata.codingtest.repository.TelephoneRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TelephoneBookService {
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private TelephoneRepository telephoneRepository;

	public List<Person> findAll() {
		return personRepository.findAll();
	}

	public Person findByFirstName(String firstName) {
		return personRepository.findByFirstName(firstName).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found")
		);
	}

	public Person findByLastName(String lastName) {
		return personRepository.findByLastName(lastName)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));
	}

	public List<Person> findPersonByPhoneNumber(String phoneNumber) {
		return personRepository.findByPhone(phoneNumber);
	}

	public Page<Person> findPersonByQuery(String query, Pageable pageable) {
		return personRepository.findByQuery(query, pageable);
	}

	public Person savePerson(Person person) {
		return personRepository.save(person);
	}

	public Person updatePerson(UUID personId, Person newData) {
		return personRepository.findById(personId).map(
			person -> {
				person.setFirstName(newData.getFirstName());
				person.setLastName(newData.getLastName());

				return personRepository.save(person);
			}

		).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));
	}

	public void deletePerson(Person person) {
		personRepository.delete(person);
	}

	public void deletePersonById(UUID personId) {
		Person person = personRepository.findById(personId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));
		deletePerson(person);
	}

	public void deletePersonTelephone(UUID personId, UUID telephoneId) {
		Person person = personRepository.findById(personId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));

		Telephone telephone = person.getTelephones().stream()
			.filter(phone -> phone.getId().equals(telephoneId))
			.findFirst()
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Telephone not found"));

		person.getTelephones().remove(telephone);
		personRepository.save(person);
	}

	public Person addTelephone(UUID personId, Telephone telephone) {
		Person person = personRepository.findById(personId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));

		person.getTelephones().add(telephone);
		return personRepository.save(person);
	}

}
