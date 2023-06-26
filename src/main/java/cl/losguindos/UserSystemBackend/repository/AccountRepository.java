package cl.losguindos.UserSystemBackend.repository;

import cl.losguindos.UserSystemBackend.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByAccEmail(String email);
}
