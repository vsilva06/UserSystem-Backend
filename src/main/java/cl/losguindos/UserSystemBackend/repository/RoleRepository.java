package cl.losguindos.UserSystemBackend.repository;

import cl.losguindos.UserSystemBackend.model.ERole;
import cl.losguindos.UserSystemBackend.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);
}
