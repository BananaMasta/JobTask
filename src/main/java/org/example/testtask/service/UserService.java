package org.example.testtask.service;

import org.example.testtask.controllers.ResourceNotFoundException;
import org.example.testtask.models.User;
import org.example.testtask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    public void checkDuplicateMail(String mail) throws ResourceNotFoundException {
        if (userRepository.existsByEmail(mail)) {
            throw new ResourceNotFoundException("Mail exist, make new mail", "EmailExist", mail);
        }
    }

    public boolean checkUserName(String userFamilyName) {
        return userFamilyName.matches("[A-Za-zА-Яа-я-]");
    }

    public void userValid(User user) throws ResourceNotFoundException {
        if (checkUserName(user.getFamilyName())) {
            throw new ResourceNotFoundException("Измените фамилию", "BadFamilyName", user.getFamilyName());
        }
        if (checkUserName(user.getName())) {
            throw new ResourceNotFoundException("Измените Имя", "BadName", user.getName());
        }
        if (checkUserName(user.getMiddleName())) {
            throw new ResourceNotFoundException("Измените Отчество", "BadMiddleName", user.getMiddleName());
        }
    }

    public UUID findUser(UUID id) {
        List<User> users = userRepository.findAll();
        return users.stream().filter(x -> x.getId() == id).findFirst().get().getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username);
    }
}


