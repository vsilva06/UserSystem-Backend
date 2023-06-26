package cl.losguindos.UserSystemBackend.controller;

import cl.losguindos.UserSystemBackend.model.dto.AccountDTO;
import cl.losguindos.UserSystemBackend.model.dto.UpdatePassRequest;
import cl.losguindos.UserSystemBackend.service.RecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recovery")
public class RecoveryController {

    @Autowired
    private RecoveryService service;

    @PostMapping("/password/recovery")
    public ResponseEntity<String> rocoverpass(@RequestBody AccountDTO accountDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.sendLink(accountDTO));
    }

    @PutMapping("/password/recovery")
    public ResponseEntity<String> updatepass(@RequestBody UpdatePassRequest bodyjson) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updatepass(bodyjson));
    }

}
