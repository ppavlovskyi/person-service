package telran.java52.person.service;

import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.PersonDto;

public interface PersonService {
	Boolean addPerson(PersonDto personDto);
	
	PersonDto findPersonById(Integer id);
	
	Iterable<PersonDto> findPersonsByCity(String city);
	
	Iterable<PersonDto> findPersonsByAges(Integer from, Integer to);
	
	PersonDto updateName (Integer id, String name);
	
	Iterable<PersonDto> findPersonsByName(String name);
	
	Iterable<CityPopulationDto> populationByCities();
	
	PersonDto updateAddressById(Integer id, AddressDto newAddress);
	
	PersonDto deletePersonById(Integer id);	

}
