package Person.person.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import Person.person.DTO.PersonDto;
import Person.person.model.Person;
import Person.person.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository pr;

    @Override
    public PersonDto createPerson(PersonDto person) {
        return savePerson(person);
    }

    @Override
    public List<PersonDto> findAll() {
        List<Person> pessoas = pr.findAll();

        return pessoas.stream()
            .map(pessoa -> new ModelMapper().map(pessoa, PersonDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<PersonDto> findById(int id) {
       Optional<Person> pessoa = pr.findById(id);

       if(pessoa.isPresent()) {

            PersonDto dto = new ModelMapper().map(pessoa.get(), PersonDto.class);

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
