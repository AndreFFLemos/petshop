package Person.person.http;

import Person.person.DTO.AnimalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="animals")
public interface Animalsfeignclient {

    @GetMapping(value = "api/owners/{owner}/list")
    List<AnimalDto> findAnimals(@PathVariable int owner);

    }
