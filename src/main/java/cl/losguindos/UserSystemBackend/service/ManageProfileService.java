package cl.losguindos.UserSystemBackend.service;

import cl.losguindos.UserSystemBackend.model.Account;
import cl.losguindos.UserSystemBackend.model.dto.AccountDTO;
import cl.losguindos.UserSystemBackend.repository.AccountRepository;
import cl.losguindos.UserSystemBackend.security.Hierarchy;
import cl.losguindos.UserSystemBackend.utils.CustomResponse;
import cl.losguindos.UserSystemBackend.utils.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ManageProfileService {

    @Autowired
    private AccountRepository repository;
    @Autowired
    private Hierarchy hierarchy;

    public Account getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        islogged(authentication);
        try{
            return repository.findByAccEmail(authentication.getName()).orElseThrow();
        }catch (NoSuchElementException e){
            throw new RuntimeException(CustomResponse.generateResponse("404", "User not found"));
        }
    }

    public List<Account> getProfiles() {
            return (List<Account>) repository.findAll();
    }

    public String updateProfile(AccountDTO account) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        islogged(authentication);
        try {
            Account currentAccount = repository.findByAccEmail(authentication.getName()).orElseThrow(() -> new RuntimeException(CustomResponse.generateResponse("404", "No user logged in")));
            Account account_to_update = repository.findByAccEmail(account.getAccEmail().toLowerCase()).orElseThrow(() -> new RuntimeException(CustomResponse.generateResponse("404", "User to update not found")));

            if (currentAccount.getAccEmail().equals(account_to_update.getAccEmail())) {
                buildtoupdate(currentAccount, account);
                repository.save(currentAccount);
                return getProfile().toString();

            }
            throw new RuntimeException(CustomResponse.generateResponse("403", "You don't have permission to update this user"));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildtoupdate(Account currentAccount, AccountDTO account) {
        currentAccount.setAccFirstName(account.getAccFirstName());
        currentAccount.setAccLastName(account.getAccLastName());
        currentAccount.setAccPass(MyPasswordEncoder.encode(account.getAccPass()));
        currentAccount.setAccLastModifiedDate(LocalDateTime.now());
    }

    public String disableProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        islogged(authentication);
        try {
            Account account_to_Update = repository.findByAccEmail(authentication.getName()).orElseThrow();
            account_to_Update.setAccEnabled(!account_to_Update.isAccEnabled());
            account_to_Update.setAccLastModifiedDate(LocalDateTime.now());
            repository.save(account_to_Update);
            return CustomResponse.generateResponse("200", "User " + account_to_Update.getAccEmail() + " is: " + account_to_Update.isAccEnabled());
        } catch (NoSuchElementException e) {
            throw new RuntimeException(CustomResponse.generateResponse("404", "User not found"));
        }
    }

    /**
     * @apiNote Elimina el usuario logeado
     * @return String
     */

    public String deleteProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        islogged(authentication);
        try {
            if (hierarchy.isCurrentAdmin()) {
                throw new RuntimeException(CustomResponse.generateResponse("403", "Admins can't delete their own account"));
            }
            Account currentAccount = repository.findByAccEmail(authentication.getName()).orElseThrow();
            repository.delete(currentAccount);
            return CustomResponse.generateResponse("200", "User deleted");

        } catch (RuntimeException e) {
            throw new RuntimeException(CustomResponse.generateResponse("404", "User not found"));
        }
    }

    private void islogged(Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new RuntimeException(CustomResponse.generateResponse("404", "No user logged in"));
        }
    }
}
