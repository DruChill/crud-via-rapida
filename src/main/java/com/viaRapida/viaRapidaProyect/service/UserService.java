package com.viaRapida.viaRapidaProyect.service;

import com.viaRapida.viaRapidaProyect.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    Optional<User> getUserByDni(String dni);
    User saveUser(User user);
    User findOrCreateUser(String dni, String name, String email, String phone);
    void deleteUser(Long id);
}
