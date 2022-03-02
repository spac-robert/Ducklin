package ro.robert.duckling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.robert.duckling.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
