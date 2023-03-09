package org.example.testtask.controllers;

import org.example.testtask.models.Role;
import org.example.testtask.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/role")
    public ResponseEntity<UUID> userRole(@RequestBody() Role role) {
        Role newRole = roleRepository.save(role);
        if (newRole.getId() == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(role.getId(), HttpStatus.CREATED);
    }
}
