package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import security.example.security.entity.CartEntity;
import security.example.security.entity.ItemEntity;

@Repository
public interface ItemsRepository extends JpaRepository<ItemEntity,Long> {
}
