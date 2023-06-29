package cl.losguindos.UserSystemBackend.service;

import cl.losguindos.UserSystemBackend.model.Account;
import cl.losguindos.UserSystemBackend.model.dto.AccountDTO;
import cl.losguindos.UserSystemBackend.repository.AccountRepository;
import cl.losguindos.UserSystemBackend.repository.RoleRepository;
import cl.losguindos.UserSystemBackend.security.Hierarchy;
import cl.losguindos.UserSystemBackend.utils.CustomResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class AdminService {

    @Autowired
    AccountRepository repository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    Hierarchy hierarchy;

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    public Account getProfileByEmail(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        currentauht(authentication);
        try {
            return repository.findByAccEmail(email).orElseThrow();
        }catch (RuntimeException e){
            throw new RuntimeException(CustomResponse.generateResponse("404", "User not found"));
        }
    }

    public String updateProfileByEmail(AccountDTO accountDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        currentauht(authentication);

        try {
            Account account = repository.findByAccEmail(accountDTO.getAccEmail().toLowerCase()).orElseThrow();
            buildtoupdate(account, accountDTO);
            repository.save(account);
            return CustomResponse.generateResponse("200", "User: \n" + account.getAccEmail() + "\n is updated");
        } catch (RuntimeException e) {
            throw new RuntimeException(CustomResponse.generateResponse("404", "User whit email: " + accountDTO.getAccEmail() + " not found"));
        }
    }

    private void buildtoupdate(Account account, AccountDTO accountDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        logger.info("Moderator only can update name and lastname");
        if (roles.stream().anyMatch(role -> role.getAuthority().equals("ROLE_MODERATOR") || role.getAuthority().equals("ROLE_ADMIN")) ){
            account.setAccName(accountDTO.getAccName());
            account.setAccLastName(accountDTO.getAccLastName());
            account.setAccFirstName(accountDTO.getAccFirstName());
            account.setAccLastModifiedDate(LocalDateTime.now());
        }
        logger.info("Admin can update all fields");
        if (roles.stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN")) ){
            account.setAccPass(accountDTO.getAccPass());
            account.getAccRoles().add(roleRepository.findByRoleName(accountDTO.getRole()).orElse(null));
        }
    }

    public String disableProfileByEmail(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        currentauht(authentication);

        try {
            Account account = repository.findByAccEmail(email).orElseThrow();
            account.setAccEnabled(false);
            repository.save(account);
            return CustomResponse.generateResponse("200", "User: \n" + getProfileByEmail(account.getAccEmail()).toString() + "\n is disabled");
        } catch (RuntimeException e) {
            throw new RuntimeException(CustomResponse.generateResponse("404", "User whit email: " + email + " not found"));
        }
    }

        public String deleteProfileByEmail (String email){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            currentauht(authentication);
            try {
                if (hierarchy.isCurrentAdmin()) {
                    throw new RuntimeException(CustomResponse.generateResponse("403", "admin can't delete himself"));
                }
                Account account = repository.findByAccEmail(email).orElseThrow();
                repository.delete(account);
                return CustomResponse.generateResponse("200", "User: " + getProfileByEmail(account.getAccEmail()).toString() + " is deleted");
            } catch (RuntimeException e) {
                throw new RuntimeException(CustomResponse.generateResponse("404", "User whit email: " + email + " not found"));
            }
        }

    private void currentauht(Authentication authentication) {
        if (!authentication.isAuthenticated())
            throw new RuntimeException(CustomResponse.generateResponse("404", "No user logged in"));

        if (!hierarchy.isEdtingMe())
            throw new RuntimeException(CustomResponse.generateResponse("403", "You don't have permission to get this user"));
    }

}