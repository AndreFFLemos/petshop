package Animals.animals.Repository;

import Animals.animals.Model.Animals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository< Animals,Integer> {

    List<Animals> findByOwner(int Owner);
}
