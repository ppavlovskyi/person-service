package telran.java52.person.service;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java52.person.dao.PersonRepository;
import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.PersonDto;
import telran.java52.person.dto.exceptions.PersonNotFoundException;
import telran.java52.person.model.Address;
import telran.java52.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Transactional
	@Override
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return modelMapper.map(person, PersonDto.class);
	}
	
	@Transactional
	@Override
	public PersonDto deletePersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.delete(person);
		return modelMapper.map(person, PersonDto.class);
	}
	
	@Transactional
	@Override
	public PersonDto updateName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setName(name);
//		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Transactional
	@Override
	public PersonDto updateAddressById(Integer id, AddressDto newAddress) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		Address address = modelMapper.map(newAddress, Address.class);
		person.setAddress(address);
//		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<PersonDto> findPersonsByCity(String city) {
		List<PersonDto> resPersons = personRepository.findByAddressCityIgnoreCase(city)
				.map(p -> modelMapper.map(p, PersonDto.class)).toList();
		return resPersons;
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<PersonDto> findPersonsByAges(Integer minAge, Integer maxAge) {
		LocalDate dateFrom = LocalDate.now().minusYears(maxAge);
		LocalDate dateTo = LocalDate.now().minusYears(minAge);
		List<PersonDto> resPersons = personRepository.findByBirthDateBetween(dateFrom, dateTo)
				.map(p -> modelMapper.map(p, PersonDto.class)).toList();
		return resPersons;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Iterable<PersonDto> findPersonsByName(String name) {
		List<PersonDto> resPersons = personRepository.findByNameIgnoreCase(name).map(p -> modelMapper.map(p, PersonDto.class))
				.toList();
		return resPersons;
	}

	@Override
	public Iterable<CityPopulationDto> populationByCities() {
		
		return personRepository.getCitiesPopulation();
	}
	



}
