package Person.person.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import Person.person.DTO.AnimalDto;
import Person.person.DTO.PersonDto;
import Person.person.http.Animalsfeignclient;
import Person.person.model.Person;
import Person.person.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository pr;
    @Autowired
    private Animalsfeignclient animalsfeignclient;

    @Override
    public PersonDto createPerson(PersonDto person) {
        return savePerson(person);
    }

    @Override
    public List<PersonDto> findAll() {
        List<Person> persons = pr.findAll();

        return persons.stream()
            .map(person -> new ModelMapper().map(person, PersonDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<PersonDto> findById(int id) {
       Optional<Person> person = pr.findById(id);

       if(person.isPresent()) {
            PersonDto dto = new ModelMapper().map(person.get(), PersonDto.class);

            List <AnimalDto> animals= animalsfeignclient.findAnimals(id);
            dto.setAnimals(animals);
            return Optional.of(dto);
       }

       return Optional.empty();
    }

    @Override
    public void delete(int id) {
        pr.deleteById(id);
    }

    @Override
    public PersonDto update(int id, PersonDto person) {
        person.setId(id);
        return savePerson(person);
    }

    private PersonDto savePerson(PersonDto person) {
        ModelMapper mapper = new ModelMapper();
        Person p1 = mapper.map(person, Person.class);
        p1 = pr.save(p1);

        return mapper.map(p1, PersonDto.class);
    }
}
