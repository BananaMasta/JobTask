package org.example.testtask.service;

import org.springframework.stereotype.Service;

@Service
public class RoleService {
    public boolean roleNameValidation(String serviceName) {
        return serviceName.matches("[A-Za-zА-Яа-я\\s-_] ");
    }


}
