package ma.bankatispring.dao;

import ma.bankatispring.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User,Long> {

    Page<User> findByFirstNameContains(String kw, Pageable pageable);
    Optional<User> findByUsername(String username);
}
