package Animals.animals.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import Animals.animals.DTO.AnimalDto;
import Animals.animals.service.AnimalService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import Animals.animals.Model.AnimalAlteration;
import Animals.animals.Model.AnimalInclusion;
import Animals.animals.Model.AnimalResponse;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {
    @Autowired
    private AnimalService as;

    @GetMapping(value="/status")
    public String statusService(@Value("${local.server.port}") String port) {
        return String.format("Active service executing at port ", port);
    }    

    @PostMapping
    public ResponseEntity<AnimalResponse> createAnimal(@RequestBody @Valid AnimalInclusion Animal) {
        ModelMapper mapper = new ModelMapper();
        AnimalDto dto = mapper.map(Animal, AnimalDto.class);
        dto = as.createAnimal(dto);
        return new ResponseEntity<>(mapper.map(dto, AnimalResponse.class), HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<AnimalResponse>> findAll() {
        List<AnimalDto> dtos = as.findAll();

        if(dtos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        ModelMapper mapper = new ModelMapper();
        List<AnimalResponse> resp = dtos.stream()
                    .map(dto -> mapper.map(dto, AnimalResponse.class))
                    .collect(Collectors.toList());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping(value="/{owner}/list")
    public ResponseEntity<List<AnimalResponse>> findByOwner(@PathVariable int owner) {
        List<AnimalDto> dtos = as.findByOwner(owner);

        if(dtos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        ModelMapper mapper = new ModelMapper();
        List<AnimalResponse> resp = dtos.stream()
                    .map(dto -> mapper.map(dto, AnimalResponse.class))
                    .collect(Collectors.toList());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @GetMapping(value="/{id}")
    public ResponseEntity<AnimalResponse> findById(@PathVariable int id) {
        Optional<AnimalDto> Animal = as.findById(id);

        if(Animal.isPresent()) {
            return new ResponseEntity<>(
                new ModelMapper().map(Animal.get(), AnimalResponse.class),
                HttpStatus.OK
            );
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<AnimalResponse> update(@PathVariable int id,
                                                          @Valid @RequestBody AnimalAlteration Animal) {
        ModelMapper mapper = new ModelMapper();
        AnimalDto dto = mapper.map(Animal, AnimalDto.class);
        dto = as.update(id, dto);

        return new ResponseEntity<>(mapper.map(dto, AnimalResponse.class), HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        as.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value="/{id}")
    public ResponseEntity<Void> defineAsDead(@PathVariable int id) {
        if(as.defineAsDead(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
