package cl.losguindos.UserSystemBackend.security;

import cl.losguindos.UserSystemBackend.model.Account;
import cl.losguindos.UserSystemBackend.model.Role;
import cl.losguindos.UserSystemBackend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class Hierarchy {
    @Autowired
    private AccountRepository repository;
    public boolean isCurrentAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        try{
            Account account = repository.findByAccEmail(email).orElseThrow();
            Set<Role> roles = account.getAccRoles();
            return roles.stream().anyMatch(role -> Objects.equals(role.getRoleName(), "ROLE_ADMIN"));
        }catch (Exception e){
            return false;
        }
    }

    public boolean isEdtingMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        try{
            Account account = repository.findByAccEmail(email).orElseThrow();
            Set<Role> roles = account.getAccRoles();
            return roles.stream().anyMatch(role -> Objects.equals(role.getRoleName(), "ROLE_ADMIN") || Objects.equals(role.getRoleName(), "ROLE_MANAGER"));
        }catch (Exception e){
            return false;
        }
    }
}

