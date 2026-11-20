package group.networkinventorytask.company.inventory.repository;

import group.networkinventorytask.company.inventory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsername(String username);
}
