package Person.person.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import Person.person.DTO.PersonDto;
import Person.person.model.Person;
import Person.person.model.PersonResponse;
import Person.person.service.PersonService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class PersonController {
    @Autowired
    private PersonService ps;

    @GetMapping(value="/status")
    public String statusService(@Value("${local.server.port}") int port) {
        return String.format("Active service on port ", port);
    }
    

    @PostMapping(value = "/person")
    public ResponseEntity<PersonResponse> create(@RequestBody @Valid PersonResponse person) {
        ModelMapper mapper = new ModelMapper();
        PersonDto dto = mapper.map(person, PersonDto.class);
        dto = ps.createPerson(dto);
        return new ResponseEntity<>(mapper.map(dto, PersonResponse.class), HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/persons")
    public ResponseEntity<List<PersonResponse>> findAll() {
        List<PersonDto> dtos = ps.findAll();

        if(dtos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        ModelMapper mapper = new ModelMapper();
        List<PersonResponse> resp = dtos.stream()
                    .map(dto -> mapper.map(dto, PersonResponse.class))
                    .collect(Collectors.toList());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @GetMapping(value="/person/{id}")
    public ResponseEntity<PersonResponse> findById(@PathVariable int id) {
        Optional<PersonDto> pessoa = ps.findById(id);

        if(pessoa.isPresent()) {
            return new ResponseEntity<>(
                new ModelMapper().map(pessoa.get(), PersonResponse.class),
                HttpStatus.OK
            );
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value="/person/{id}")
    public ResponseEntity<PersonResponse> update(@PathVariable int id,
        @Valid @RequestBody Person person) {
        ModelMapper mapper = new ModelMapper();
        PersonDto dto = mapper.map(person, PersonDto.class);
        dto = ps.update(id,dto);

        return new ResponseEntity<>(mapper.map(dto, PersonResponse.class), HttpStatus.OK);
    }

    @DeleteMapping(value="/person/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        ps.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } 
}
