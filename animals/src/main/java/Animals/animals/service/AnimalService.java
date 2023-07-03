package Animals.animals.service;

import java.util.List;
import java.util.Optional;

import Animals.animals.DTO.AnimalDto;

public interface AnimalService {
    AnimalDto createAnimal(AnimalDto animal);
    List<AnimalDto> findAll();
    Optional<AnimalDto> findById(int id);
    List<AnimalDto> findByOwner(int dono);
    void delete(int id);
    boolean defineAsDead(int id);
    AnimalDto update(int id, AnimalDto animal);
}
