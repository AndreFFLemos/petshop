package Person.person.model;

import Person.person.DTO.AnimalDto;

import java.util.List;

public class PersonResponseDetail extends PersonResponse{

    private List<AnimalDto> animals;

    public List<AnimalDto> getAnimals() {
        return animals;
    }

    public void setAnimals(List<AnimalDto> animals) {
        this.animals = animals;
    }
}
