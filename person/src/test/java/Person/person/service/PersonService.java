package Person.person.service;

import java.util.List;
import java.util.Optional;
import Person.person.DTO.PersonDto;

public interface PersonService {
    PersonDto createPerson(PersonDto person);
    List<PersonDto> findAll();
    Optional<PersonDto> findById(int id);
    void delete(int id);
    PersonDto update(int id, PersonDto person);
}
