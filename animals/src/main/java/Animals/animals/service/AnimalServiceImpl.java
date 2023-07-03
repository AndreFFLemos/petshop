package Animals.animals.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import Animals.animals.DTO.AnimalDto;
import Animals.animals.Model.Animals;
import Animals.animals.Repository.AnimalRepository;
import Animals.animals.service.AnimalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AnimalServiceImpl implements AnimalService {
    @Autowired
    private AnimalRepository ar;

    @Override
    public AnimalDto createAnimal(AnimalDto animal) {
        return saveAnimal(animal);
    }

    @Override
    public List<AnimalDto> findAll() {
        List<Animals> animals = ar.findAll();

        return animals.stream()
            .map(animal -> new ModelMapper().map(animal, AnimalDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<AnimalDto> findById(int id) {
        Optional<Animals> animal = ar.findById(id);

       if(animal.isPresent()) {
           return Optional.of(new ModelMapper().map(animal.get(), AnimalDto.class));
       }

       return Optional.empty();
    }

    @Override
    public List<AnimalDto> findByOwner(int owner) {
        List<Animals> animals = ar.findByOwner(owner);

        return animals.stream()
            .map(animal -> new ModelMapper().map(animal, AnimalDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {
        ar.deleteById(id);
    }

    @Override
    public boolean defineAsDead(int id) {
        Optional<Animals> animal = ar.findById(id);
        if(animal.isPresent()) {
            animal.get().setAlive(false);
            ar.save(animal.get());

            return true;
        }

        return false;
    }

    @Override
    public AnimalDto update(int id, AnimalDto animal) {
        animal.setId(id);
        return saveAnimal(animal);
    }

    private AnimalDto saveAnimal(AnimalDto animal) {
        ModelMapper mapper = new ModelMapper();
        Animals a1 = mapper.map(animal, Animals.class);
        a1 = ar.save(a1);

        return mapper.map(a1, AnimalDto.class);
    }
    
}
