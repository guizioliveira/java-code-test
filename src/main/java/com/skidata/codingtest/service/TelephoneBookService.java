package com.skidata.codingtest.service;

import java.util.List;
import java.util.UUID;

import com.skidata.codingtest.dto.PersonCreateDTO;
import com.skidata.codingtest.dto.PersonDTO;
import com.skidata.codingtest.dto.TelephoneCreateDTO;
import com.skidata.codingtest.mapper.PersonMapper;
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
	@Autowired
	PersonMapper personMapper;

	public List<PersonDTO> findAll() {
		return personMapper.toDtoList(personRepository.findAll());
	}

	public PersonDTO findByFirstName(String firstName) {
		Person p = personRepository.findByFirstName(firstName).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found")
		);

		return personMapper.toDto(p);
	}

	public PersonDTO findByLastName(String lastName) {
		Person p = personRepository.findByLastName(lastName)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));

		return personMapper.toDto(p);
	}

	public List<PersonDTO> findPersonByPhoneNumber(String phoneNumber) {
		return personMapper.toDtoList(personRepository.findByPhone(phoneNumber));
	}

	public Page<PersonDTO> findPersonByQuery(String query, Pageable pageable) {
		return personRepository.findByQuery(query, pageable).map(personMapper::toDto);
	}

	public PersonDTO savePerson(PersonCreateDTO person) {
		Person entity = new Person(person.firstName(), person.lastName());
		Person savedPerson = personRepository.save(entity);

		return personMapper.toDto(savedPerson);
	}

	public PersonDTO updatePerson(UUID personId, PersonCreateDTO newData) {
		Person updated = personRepository.findById(personId).map(
				person -> {
					person.setFirstName(newData.firstName());
					person.setLastName(newData.lastName());
					return personRepository.save(person);
				})
			.orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found")
			);

		return personMapper.toDto(updated);
	}

	public void deletePerson(Person person) {
		personRepository.delete(person);
	}

	public void deletePersonById(UUID personId) {
		if (!personRepository.existsById(personId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
		}

		personRepository.deleteById(personId);
	}

	public PersonDTO addTelephone(UUID personId, TelephoneCreateDTO telephone) {
		Person person = personRepository.findById(personId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));

		Telephone tel = new Telephone(telephone.countryCode(), telephone.number());
		person.getTelephones().add(tel);

		Person updated = personRepository.save(person);
		return personMapper.toDto(updated);
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

}
