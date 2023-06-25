package cl.losguindos.UserSystemBackend.repository;

import cl.losguindos.UserSystemBackend.model.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {
}
