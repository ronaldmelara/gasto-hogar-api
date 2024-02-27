package mg.factory.gastoshogarapi.repositories;

import mg.factory.gastoshogarapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<UserDetails> findByUsername(String username);
}
