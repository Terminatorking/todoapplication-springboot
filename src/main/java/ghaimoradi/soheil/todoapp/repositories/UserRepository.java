package ghaimoradi.soheil.todoapp.repositories;

import ghaimoradi.soheil.todoapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}