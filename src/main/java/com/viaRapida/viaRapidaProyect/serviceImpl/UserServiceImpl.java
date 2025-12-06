package com.viaRapida.viaRapidaProyect.serviceImpl;

import com.viaRapida.viaRapidaProyect.model.User;
import com.viaRapida.viaRapidaProyect.repository.UserRepository;
import com.viaRapida.viaRapidaProyect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByDni(String dni) {
        return userRepository.findByDni(dni);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findOrCreateUser(String dni, String name, String email, String phone) {
        Optional<User> existingUser = userRepository.findByDni(dni);
        if (existingUser.isPresent()) {
            return existingUser.get();
        }
        User newUser = new User(dni, name, email, phone);
        return userRepository.save(newUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
