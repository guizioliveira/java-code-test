package com.skidata.codingtest.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.skidata.codingtest.entity.Person;
import com.skidata.codingtest.entity.Telephone;
import com.skidata.codingtest.repository.PersonRepository;

@SpringBootTest
@Transactional
class TelephoneBookServiceTest {
	private static final String FIRSTNAME = "FirstNameOfTestPerson";
	private static final String LASTNAME = "LastNameOfTestPerson";

	@Autowired
	private TelephoneBookService target;
	@Autowired
	private PersonRepository personRepository;

	@Test
	void findPersonByFirstName() {
		Person person = personRepository.save(new Person(FIRSTNAME, LASTNAME));
		assertEquals(FIRSTNAME, target.findByFirstName(person.getFirstName()).getFirstName());
	}

	@Test
	void findPersonByLastName() {
		Person person = personRepository.save(new Person(FIRSTNAME, LASTNAME));
		assertEquals(LASTNAME, target.findByLastName(person.getLastName()).getLastName());
	}

	@Test
	void savePerson() {
		Person person = new Person(FIRSTNAME, LASTNAME);
		person = target.savePerson(person);
		assertEquals(person.getId(), personRepository.findById(person.getId()).get().getId());
	}

	@Test
	void deletePerson() {
		Person person = personRepository.save(new Person(FIRSTNAME, LASTNAME));
		assertNotNull(person.getId());
		assertEquals(person.getId(), personRepository.findById(person.getId()).get().getId());
		target.deletePerson(person);
		assertFalse(personRepository.findById(person.getId()).isPresent());
	}

	@Test
	void savePersonWithTelephone() {
		Person person = new Person(FIRSTNAME, LASTNAME);
		person = target.savePerson(person);
		Telephone telephone = new Telephone("AT", "123456789");
		target.addTelephone(person.getId(), telephone);
		Person foundPerson = personRepository.findById(person.getId()).get();
		assertEquals(telephone.getNumber(), foundPerson.getTelephones().get(0).getNumber());
		assertEquals(foundPerson, person);
	}
}
