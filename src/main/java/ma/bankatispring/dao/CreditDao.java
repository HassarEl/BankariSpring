package ma.bankatispring.dao;

import ma.bankatispring.model.Credit;
import ma.bankatispring.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditDao extends JpaRepository<Credit, Long> {
    Page<Credit> findByMotifContains(String motif, Pageable pageable);
    Page<Credit> findByUserAndMotifContains(User user, String keyword, Pageable pageable);
}
