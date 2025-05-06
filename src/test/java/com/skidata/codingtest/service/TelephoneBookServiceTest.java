package com.skidata.codingtest.service;

import static org.junit.jupiter.api.Assertions.*;

import com.skidata.codingtest.dto.PersonCreateDTO;
import com.skidata.codingtest.dto.PersonDTO;
import com.skidata.codingtest.dto.TelephoneCreateDTO;
import com.skidata.codingtest.dto.TelephoneDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.skidata.codingtest.entity.Person;
import com.skidata.codingtest.entity.Telephone;
import com.skidata.codingtest.repository.PersonRepository;

import java.util.List;
import java.util.UUID;

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
		personRepository.save(new Person(FIRSTNAME, LASTNAME));
		PersonDTO dto = target.findByFirstName(FIRSTNAME);
		assertEquals(FIRSTNAME, dto.firstName());
		assertEquals(LASTNAME, dto.lastName());
	}

	@Test
	void findPersonByLastName() {
		personRepository.save(new Person(FIRSTNAME, LASTNAME));
		PersonDTO dto = target.findByLastName(LASTNAME);
		assertEquals(FIRSTNAME, dto.firstName());
		assertEquals(LASTNAME, dto.lastName());
	}

	@Test
	void savePerson() {
		PersonCreateDTO createDto = new PersonCreateDTO(FIRSTNAME, LASTNAME);
		PersonDTO dto = target.savePerson(createDto);
		assertNotNull(dto.id());
		assertTrue(personRepository.existsById(dto.id()));
		assertEquals(FIRSTNAME, dto.firstName());
		assertEquals(LASTNAME, dto.lastName());
	}

	@Test
	void deletePerson() {
		Person person = personRepository.save(new Person(FIRSTNAME, LASTNAME));
		UUID id = person.getId();
		assertTrue(personRepository.existsById(id));
		target.deletePersonById(id);
		assertFalse(personRepository.existsById(id));
	}

	@Test
	void savePersonWithTelephone() {
		Person saved = personRepository.save(new Person(FIRSTNAME, LASTNAME));
		UUID personId = saved.getId();
		TelephoneCreateDTO telDto = new TelephoneCreateDTO("AT", "123456789");
		PersonDTO updated = target.addTelephone(personId, telDto);
		List<TelephoneDTO> phones = updated.telephones();
		assertEquals(1, phones.size());
		TelephoneDTO phone = phones.get(0);
		assertEquals("AT", phone.countryCode());
		assertEquals("123456789", phone.number());
		assertEquals(personId, updated.id());
	}

	@Test
	void deletePersonWithTelephone() {
		Person saved = personRepository.save(new Person(FIRSTNAME, LASTNAME));
		Telephone t = new Telephone("AT", "1234");
		saved.getTelephones().add(t);
		personRepository.save(saved);
		target.deletePersonTelephone(saved.getId(), t.getId());
		Person after = personRepository.findById(saved.getId()).get();
		assertTrue(after.getTelephones().isEmpty());
	}

	@Test
	void findAllPersons() {
		personRepository.save(new Person("A", "A"));
		personRepository.save(new Person("B", "B"));
		List<PersonDTO> all = target.findAll();
		assertTrue(all.size() >= 2);
	}

	@Test
	void searchPersonByQuery() {
		personRepository.save(new Person("Jane", "Doe"));
		personRepository.save(new Person("John", "Smith"));
		Page<PersonDTO> page = target.findPersonByQuery("Jane", PageRequest.of(0, 10));
		assertEquals(1, page.getTotalElements());
		assertEquals("Jane", page.getContent().get(0).firstName());
	}

	@Test
	void updatePerson() {
		Person saved = personRepository.save(new Person(FIRSTNAME, LASTNAME));
		PersonCreateDTO updateDto = new PersonCreateDTO("NewFirst", "NewLast");
		PersonDTO updated = target.updatePerson(saved.getId(), updateDto);
		assertEquals("NewFirst", updated.firstName());
		assertEquals("NewLast", updated.lastName());
	}
}
