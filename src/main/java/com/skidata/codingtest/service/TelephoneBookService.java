package com.skidata.codingtest.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
		return personRepository.findByFirstName(firstName);
	}

	public Person findByLastName(String lastName) {
		return personRepository.findByLastName(lastName);
	}

	public List<Person> findPersonByPhoneNumber(String phoneNumber) {
		return personRepository.findByPhone(phoneNumber);
	}

	public Person savePerson(Person person) {
		return personRepository.save(person);
	}

	public void deletePerson(Person person) {
		personRepository.delete(person);
	}

	public void deletePersonById(UUID personId) {
		Person person = personRepository.findById(personId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		deletePerson(person);
	}

	public void deletePersonTelephone(UUID personId, UUID telephoneId) {
		Person person = personRepository.findById(personId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		Telephone telephone = person.getTelephones().stream().filter(phone -> phone.getId().equals(telephoneId))
			.findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		person.getTelephones().remove(telephone);
		personRepository.save(person);
	}

	public Person addTelephone(UUID personId, Telephone telephone) {
		Optional<Person> op = personRepository.findById(personId);
		if (op.isPresent()) {
			Person p = op.get();
			p.getTelephones().add(telephone);
			personRepository.save(p);
			return p;
		}
		// TODO Add Exception
		return null;
	}

}
