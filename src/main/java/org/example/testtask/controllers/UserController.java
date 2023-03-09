package org.example.testtask.controllers;

import org.example.testtask.dto.Dto;
import org.example.testtask.dto.DtoPassword;
import org.example.testtask.models.Role;
import org.example.testtask.models.Status;
import org.example.testtask.models.User;
import org.example.testtask.repository.RoleRepository;
import org.example.testtask.repository.UserRepository;
import org.example.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> userRegistration(@Valid @RequestBody() User user) throws ResourceNotFoundException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setUserStatus(Status.Active);
        user.setRegDate(timestamp);
        userService.checkDuplicateMail(user.getEmail());
        userService.userValid(user);
        User newUser = userRepository.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> userEdit(@PathVariable("id") UUID id, @RequestBody Dto user) throws ResourceNotFoundException {
        Optional<User> maybeUser = userRepository.findById(id);
        if (!maybeUser.isPresent()) {
            throw new ResourceNotFoundException(null, "UserNotFound", "Пользователь с id" + id + " не найден");
        }
        User updateUser = userRepository.findById(id).get();
        updateUser.setEmail(user.getEmail());
        updateUser.setFamilyName(user.getFamilyName());
        updateUser.setName(user.getName());
        updateUser.setMiddleName(user.getMiddleName());
        userService.checkDuplicateMail(updateUser.getEmail());
        userRepository.save(updateUser);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }


    @PostMapping("/users/{id}/set-password")
    public ResponseEntity<User> userPasswordEdit(@PathVariable UUID id, @RequestBody DtoPassword password) throws ResourceNotFoundException {
        Optional<User> maybeUser = userRepository.findById(id);
        if (!maybeUser.isPresent()) {
            throw new ResourceNotFoundException(null, "UserNotFound", "Пользователь с id" + id + " не найден");
        }
        User user = maybeUser.get();
        if (password.getPassword().equals(password.getConfPassword())) {
            user.setPassword(password.getPassword());
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Введенные пароли не совпадают", "PasswordNotConfirmed" + password.getPassword());
        }
    }


    @PostMapping("/users/{id}/set-role")
    public ResponseEntity<User> userPasswordEdit(@PathVariable UUID id, @RequestBody Role role) throws ResourceNotFoundException {
        Optional<User> maybeUser = userRepository.findById(id);
        if (!maybeUser.isPresent()) {
            throw new ResourceNotFoundException("", "UserNotFound", "Пользователь с id" + id + " не найден");
        }
        User userNewRole = maybeUser.get();
        if (roleRepository.existsById(role.getId())) {
            userNewRole.setRole(role.getId());
            return new ResponseEntity<>(userNewRole, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Указанная роль не существует", "RoleNotFound", role.getRoleName());
        }
    }

    @PostMapping("/user/{id}/{state}")
    public ResponseEntity<User> userNewStatus(@PathVariable("id") UUID id, @PathVariable("state") String state) throws ResourceNotFoundException {
        Optional<User> maybeUser = userRepository.findById(id);
        if (!maybeUser.isPresent()) {
            throw new ResourceNotFoundException(null, "UserNotFound", "Пользователь с id" + id + " не найден");
        }
        User newUserStatus = userRepository.findById(id).get();
        Status status = Status.valueOf(state);
        if (status.equals(Status.Banned)) {
            newUserStatus.setUserStatus(Status.Banned);
            return new ResponseEntity<>(userRepository.findById(id).get(), HttpStatus.OK);
        } else if (status.equals(Status.Active)) {
            newUserStatus.setUserStatus(Status.Active);
            return new ResponseEntity<>(userRepository.findById(id).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> userDelete(@PathVariable("id") UUID id) throws ResourceNotFoundException {
        Optional<User> maybeUser = userRepository.findById(id);
        if (!maybeUser.isPresent()) {
            throw new ResourceNotFoundException(null, "UserNotFound", "Пользователь с id" + id + " не найден");
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> users() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> user(@PathVariable UUID id) throws ResourceNotFoundException {
        Optional<User> maybeUser = userRepository.findById(id);
        if (!maybeUser.isPresent()) {
            throw new ResourceNotFoundException(null, "UserNotFound", "Пользователь с id" + id + " не найден");
        }
        return new ResponseEntity<>(userRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping("/role")
    public ResponseEntity<List<Role>> roleList() {
        return new ResponseEntity<>(roleRepository.findAll(), HttpStatus.OK);
    }
}
