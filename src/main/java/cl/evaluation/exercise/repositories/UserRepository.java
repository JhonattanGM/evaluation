package cl.evaluation.exercise.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import cl.evaluation.exercise.models.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(String email);

  @Query("select u from UserEntity u where u.email = ?1")
  Optional<UserEntity> getName(String username);

}
