package cl.evaluation.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import cl.evaluation.exercise.models.PhoneEntity;

@Repository
public interface PhoneRepository extends CrudRepository<PhoneEntity, Long> {

}
