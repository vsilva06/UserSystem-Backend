package cl.losguindos.UserSystemBackend.controller;

import cl.losguindos.UserSystemBackend.model.Role;
import cl.losguindos.UserSystemBackend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService service;

    @GetMapping("/get")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.status(200).body(service.getRoles().stream()
                .map(Role::toRole).toList());
    }

}
