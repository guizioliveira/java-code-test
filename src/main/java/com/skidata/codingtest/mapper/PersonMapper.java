package com.skidata.codingtest.mapper;

import com.skidata.codingtest.dto.PersonDTO;
import com.skidata.codingtest.dto.TelephoneDTO;
import com.skidata.codingtest.entity.Person;
import com.skidata.codingtest.entity.Telephone;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonMapper {

	public PersonDTO toDto(Person p) {
		return new PersonDTO(
			p.getId(),
			p.getFirstName(),
			p.getLastName(),
			toTelephoneDtoList(p.getTelephones())
		);
	}

	public List<PersonDTO> toDtoList(List<Person> persons) {
		return persons.stream()
			.map(this::toDto)
			.toList();
	}

	public TelephoneDTO toDto(Telephone t) {
		return new TelephoneDTO(
			t.getId(),
			t.getCountryCode(),
			t.getNumber()
		);
	}

	public List<TelephoneDTO> toTelephoneDtoList(List<Telephone> telephones) {
		return telephones.stream()
			.map(this::toDto)
			.toList();
	}
}
