package cl.losguindos.UserSystemBackend.controller;

import cl.losguindos.UserSystemBackend.model.Account;
import cl.losguindos.UserSystemBackend.model.dto.AccountDTO;
import cl.losguindos.UserSystemBackend.service.AdminService;
import cl.losguindos.UserSystemBackend.service.ManageProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
public class AdminController {

    @Autowired
    private AdminService service;

    @Autowired
    ManageProfileService manageProfileService;

    @GetMapping
    @RequestMapping("/get/profile")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Account> getProfileByEmail(@RequestParam String email){
        return ResponseEntity.status(200).body(service.getProfileByEmail(email));
    }

    @GetMapping
    @RequestMapping("/get")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<List<Account>> getAllProfiles(){
        return ResponseEntity.status(200).body(manageProfileService.getProfiles().stream().map(Account::toadminAccount).toList());
    }

    @PutMapping
    @RequestMapping("/update/profile")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<String> updateProfileByEmail(@RequestBody AccountDTO account){
        return ResponseEntity.status(200).body(service.updateProfileByEmail(account));
    }


    @PutMapping
    @RequestMapping("/disable/profile")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<String> disableProfileByEmail(@RequestParam String email){
        return ResponseEntity.status(200).body(service.disableProfileByEmail(email));
    }

    @DeleteMapping
    @RequestMapping("/delete/profile")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProfileByEmail(@RequestParam String email){
        return ResponseEntity.status(200).body(service.deleteProfileByEmail(email));
    }

}
