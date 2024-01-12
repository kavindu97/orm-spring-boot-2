package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import security.example.security.entity.CartEntity;
@Repository
public interface CartRepository extends JpaRepository<CartEntity,Integer> {
}
