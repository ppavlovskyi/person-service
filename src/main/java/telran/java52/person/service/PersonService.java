package telran.java52.person.service;

import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.ChildDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.EmployeeDto;
import telran.java52.person.dto.PersonDto;

public interface PersonService {
	Boolean addPerson(PersonDto personDto);

	PersonDto findPersonById(Integer id);

	PersonDto[] findPersonsByCity(String city);

	PersonDto[] findPersonsBetweenAge(Integer minAge, Integer maxAge);

	PersonDto updateName(Integer id, String name);

	PersonDto[] findPersonsByName(String name);

	Iterable<CityPopulationDto> populationByCities();

	PersonDto updateAddressById(Integer id, AddressDto newAddress);

	PersonDto deletePersonById(Integer id);
	
	 PersonDto[] findAllChildren();
	
	 PersonDto[] findEmployeesBySalary(Integer minSalary, Integer maxSalary);
	
	

}
