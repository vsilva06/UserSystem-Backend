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

//    public boolean validateRoles(Account accountBody) throws JSONException {
//        Role prerol = accountBody.getAccRoles();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        Optional<Account> user = repository.findByUsrEmail(email);
////        return true;
//        if (user.isEmpty()) {
//            return false;
//        }
//        if (Objects.equals(user.get().getAccEmail(), accountBody.getAccEmail())) {
//            return true;
//        }
//        Role rol = user.get().getAccRoles();
//        if (Objects.equals(rol.getRoleName(), "ROLE_ADMIN")) {
//            return true;
//        } else if (Objects.equals(prerol.getRoleName(), "ROLE_MANAGER") && Objects.equals(rol.getRoleName(), "ROLE_ADMIN")) {
//            return true;
//        } else return Objects.equals(prerol.getRoleName(), "ROLE_EXECUTIVE") && (Objects.equals(rol.getRoleName(), "ROLE_ADMIN") || Objects.equals(rol.getRoleName(), "ROLE_MANAGER")) ;
//    }

    public boolean isCurrentAdmin() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        try{
//            Account account = repository.findByAccEmail(email).orElseThrow();
//            Set<Role> roles = account.getAccRole();
//            return roles.stream().anyMatch(role -> Objects.equals(role.getRoleName(), "ROLE_ADMIN"));
//        }catch (Exception e){
//            return false;
//        }
        return true;
    }

    public boolean isEdtingMe() {
        return isCurrentAdmin();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        Optional<Account> user = repository.findByUsrEmail(email);
//        return user.filter(usuario -> Objects.equals(usuario.getAccEmail(), accountBody.getAccEmail())).isPresent();
    }
}

