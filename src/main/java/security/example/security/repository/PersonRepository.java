package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import security.example.security.entity.PersonEntity;
@Repository
public interface PersonRepository extends JpaRepository<PersonEntity,Long> {
}
