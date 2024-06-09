package telran.java52.person.service;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java52.person.dao.PersonRepository;
import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.ChildDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.EmployeeDto;
import telran.java52.person.dto.PersonDto;
import telran.java52.person.dto.exceptions.PersonNotFoundException;
import telran.java52.person.model.Address;
import telran.java52.person.model.Child;
import telran.java52.person.model.Employee;
import telran.java52.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {
	final PersonRepository personRepository;
	final ModelMapper modelMapper;
	final PersonModelDtoMapper mapper;

	@Transactional
	@Override
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(mapper.mapToModel(personDto));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		
		return mapper.mapToDto(person);
	}
	
	@Transactional
	@Override
	public PersonDto deletePersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.delete(person);
		return mapper.mapToDto(person);
	}
	
	@Transactional
	@Override
	public PersonDto updateName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setName(name);
//		personRepository.save(person);
		return mapper.mapToDto(person);
	}

	@Transactional
	@Override
	public PersonDto updateAddressById(Integer id, AddressDto newAddress) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		Address address = modelMapper.map(newAddress, Address.class);
		person.setAddress(address);
//		personRepository.save(person);
		return mapper.mapToDto(person);
	}

	@Transactional(readOnly = true)
	@Override
	public PersonDto[] findPersonsByCity(String city) {
		return personRepository.findByAddressCityIgnoreCase(city)
				.map(p -> mapper.mapToDto(p))
				.toArray(PersonDto[]::new);
	}

	@Transactional(readOnly = true)
	@Override
	public PersonDto[] findPersonsBetweenAge(Integer minAge, Integer maxAge) {
		LocalDate from = LocalDate.now().minusYears(maxAge);
		LocalDate to = LocalDate.now().minusYears(minAge);
		return personRepository.findByBirthDateBetween(from, to)
				.map(p ->mapper.mapToDto(p))
				.toArray(PersonDto[]::new);
	}
	
	@Transactional(readOnly = true)
	@Override
	public PersonDto[] findPersonsByName(String name) {
		return personRepository.findByNameIgnoreCase(name)
				.map(p ->mapper.mapToDto(p))
				.toArray(PersonDto[]::new);
	}

	@Override
	public Iterable<CityPopulationDto> populationByCities() {
		
		return personRepository.getCitiesPopulation();
	}
	
	@Transactional(readOnly = true)
	@Override
	public  PersonDto[] findAllChildren() {
		
		return personRepository.findAllChildren()
				.map(c->mapper.mapToDto(c))
				.toArray( PersonDto[]::new);
	}
	
	@Transactional(readOnly = true)
	@Override
	public  PersonDto[] findEmployeesBySalary(Integer minSalary, Integer maxSalary) {
		return personRepository.findEmployeesBySalary(minSalary, maxSalary)
				.map(c->mapper.mapToDto(c))
				.toArray( PersonDto[]::new);
	}
	
	
@Transactional
	@Override
	public void run(String... args) throws Exception {
	if(personRepository.count() == 0) {
		Person person = new Person(1000, "John", LocalDate.of(1985, 3, 11), 
				new Address("Tel Aviv", "Ben Gvirol", 81));
		Child child = new Child(2000, "Mosche", LocalDate.of(2018, 7, 5),
				new Address("Ashkelon", "Bar Kohva", 21), "Shalom");
		Employee employee = new Employee(3000, "Sarah", LocalDate.of(1995, 11, 23), 
				new Address("Rehovot", "Herzl", 7), "Motorola", 20_000);
		personRepository.save(person);
		personRepository.save(child);
		personRepository.save(employee);
		}
		
	}


	



}
