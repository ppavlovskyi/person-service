package telran.java52.person.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.ChildDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.EmployeeDto;
import telran.java52.person.dto.PersonDto;
import telran.java52.person.service.PersonService;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
	final PersonService personService;

	@PostMapping
	public Boolean addPerson(@RequestBody PersonDto personDto) {
		return personService.addPerson(personDto);
	}

	@GetMapping("/{id}")
	public PersonDto findPersonById(@PathVariable Integer id) {
		return personService.findPersonById(id);
	}

	@GetMapping("/city/{city}")
	public PersonDto[] findByCity(@PathVariable String city) {
		return personService.findPersonsByCity(city);
	}

	@GetMapping("/ages/{min}/{max}")
	public PersonDto[] findPersonsBetweenAge(@PathVariable Integer min, @PathVariable Integer max) {
		return personService.findPersonsBetweenAge(min, max);
	}

	@PutMapping("/{id}/name/{name}")
	public PersonDto updateName(@PathVariable Integer id, @PathVariable String name) {
		return personService.updateName(id, name);
	}

	@GetMapping("/name/{name}")
	public PersonDto[] findByName(@PathVariable String name) {
		return personService.findPersonsByName(name);
	}

	@GetMapping("/population/city")
	public Iterable<CityPopulationDto> populationByCities() {
		return personService.populationByCities();
	}

	@PutMapping("/{id}/address")
	public PersonDto updateAddressById(@PathVariable Integer id, @RequestBody AddressDto newAddress) {
		return personService.updateAddressById(id, newAddress);
	}

	@DeleteMapping("{id}")
	public PersonDto deletePersonById(@PathVariable Integer id) {
		return personService.deletePersonById(id);
	}

	@GetMapping("/children")
	public PersonDto[] findAllChildren() {
		return personService.findAllChildren();
	}

	@GetMapping("/salary/{minSalary}/{maxSalary}")
	public  PersonDto[] findEmployeesBySalary(@PathVariable Integer minSalary, @PathVariable Integer maxSalary) {
		return personService.findEmployeesBySalary(minSalary, maxSalary);
	}
}
