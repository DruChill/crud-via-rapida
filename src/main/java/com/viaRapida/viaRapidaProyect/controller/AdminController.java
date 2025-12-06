package com.viaRapida.viaRapidaProyect.controller;

import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.model.Destination;
import com.viaRapida.viaRapidaProyect.model.User;
import com.viaRapida.viaRapidaProyect.service.TicketService;
import com.viaRapida.viaRapidaProyect.service.DestinationService;
import com.viaRapida.viaRapidaProyect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private DestinationService destinationService;
    
    @Autowired
    private UserService userService;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (ADMIN_USERNAME.equals(loginRequest.getUsername()) && 
            ADMIN_PASSWORD.equals(loginRequest.getPassword())) {
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("token", "admin-token-" + System.currentTimeMillis());
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/tickets")
    public ResponseEntity<?> getAllTicketsForAdmin() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/destinations")
    public ResponseEntity<?> getAllDestinationsForAdmin() {
        return ResponseEntity.ok(destinationService.getAllDestinations());
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsersForAdmin() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Clases internas para requests
    static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}