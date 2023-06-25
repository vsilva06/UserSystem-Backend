package cl.losguindos.UserSystemBackend.controller;

import cl.losguindos.UserSystemBackend.model.Account;
import cl.losguindos.UserSystemBackend.service.ManageProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profile")
public class ManageProfileController {

    @Autowired
    ManageProfileService service;

    @GetMapping("/get")
    public ResponseEntity<Account> getProfile(){
        return ResponseEntity.status(200).body(service.getProfile());
    }

    @GetMapping("/get/profiles")
    public ResponseEntity<List<Account>> getProfiles(){
        return ResponseEntity.status(200).body(service.getProfiles().stream().map(Account::privateAccount).toList());
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProfile(){
        return ResponseEntity.status(200).body(service.updateProfile());
    }

    @PutMapping("/disable")
    public ResponseEntity<String> disableProfile(){
        return ResponseEntity.status(200).body(service.disableProfile());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProfile(){
        return ResponseEntity.status(200).body(service.deleteProfile());
    }
}
