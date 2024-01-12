package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import security.example.security.entity.AddressEntity;
@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Long> {
}
