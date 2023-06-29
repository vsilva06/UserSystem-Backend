package cl.losguindos.UserSystemBackend.controller;

import cl.losguindos.UserSystemBackend.model.dto.AccountDTO;
import cl.losguindos.UserSystemBackend.service.ManageUsersService;
import cl.losguindos.UserSystemBackend.utils.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseEntity<JwtResponse>> login(@RequestBody AccountDTO loginRequest){
        try {
            return ResponseEntity.status(200).body(service.login(loginRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    @PostMapping("/sing-up")
    public ResponseEntity<ResponseEntity<JwtResponse>> createUser(@RequestBody AccountDTO account){
        try {
            return ResponseEntity.status(200).body(service.createUser(account));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }
}
