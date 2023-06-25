package cl.losguindos.UserSystemBackend.service;

import cl.losguindos.UserSystemBackend.model.Account;
import cl.losguindos.UserSystemBackend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageProfileService {

    @Autowired
    private AccountRepository repository;
    public Account getProfile() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public List<Account> getProfiles() {
        return (List<Account>) repository.findAll();
    }

    public String updateProfile() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public String disableProfile() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public String deleteProfile() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
