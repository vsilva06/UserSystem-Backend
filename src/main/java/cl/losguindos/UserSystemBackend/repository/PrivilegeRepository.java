package cl.losguindos.UserSystemBackend.repository;

import cl.losguindos.UserSystemBackend.model.EPrivilege;
import cl.losguindos.UserSystemBackend.model.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {

    Optional<Privilege> findByPrivName(EPrivilege privName);
}
