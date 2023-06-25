package cl.losguindos.UserSystemBackend.repository;

import cl.losguindos.UserSystemBackend.model.AuxUserPass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuxUserPassRepository extends CrudRepository<AuxUserPass, Long> {

    Optional<AuxUserPass> findByAuxpassToken(String auxpassToken);
}
