package cl.losguindos.UserSystemBackend.controller;

import cl.losguindos.UserSystemBackend.model.Account;
import cl.losguindos.UserSystemBackend.model.dto.AccountDTO;
import cl.losguindos.UserSystemBackend.service.ManageUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class ManageUsersController {

    @Autowired
    private ManageUsersService service;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AccountDTO loginRequest){
        try {
            return ResponseEntity.status(200).body(service.login(loginRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Error");
        }
    }


    @PostMapping("/sing_up")
    public ResponseEntity<Account> createUser(@RequestBody AccountDTO account){
        return ResponseEntity.status(200).body(service.createUser(account).pulicAccount());
    }
}
