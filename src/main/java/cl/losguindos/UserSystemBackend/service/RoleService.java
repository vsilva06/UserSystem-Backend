package cl.losguindos.UserSystemBackend.service;

import cl.losguindos.UserSystemBackend.model.Role;
import cl.losguindos.UserSystemBackend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public List<Role> getRoles() {
        return (List<Role>) repository.findAll();
    }
}