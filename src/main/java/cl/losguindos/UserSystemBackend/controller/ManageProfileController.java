package cl.losguindos.UserSystemBackend.controller;

import cl.losguindos.UserSystemBackend.model.Account;
import cl.losguindos.UserSystemBackend.model.dto.AccountDTO;
import cl.losguindos.UserSystemBackend.service.ManageProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ManageProfileController {

    @Autowired
    private ManageProfileService service;

    @GetMapping("/get")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Account> getProfile(){
        return ResponseEntity.status(200).body(service.getProfile().pulicAccount());
    }

    @GetMapping("/get/profiles")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Account>> getProfiles(){
        return ResponseEntity.status(200).body(service.getProfiles().stream().map(Account::pulicAccount).toList());
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> updateProfile(@RequestBody AccountDTO accountDTO){
        return ResponseEntity.status(200).body(service.updateProfile(accountDTO));
    }

    @PutMapping("/disable")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> disableProfile(){
        return ResponseEntity.status(200).body(service.disableProfile());
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteProfile(){
        return ResponseEntity.status(200).body(service.deleteProfile());
    }
}
