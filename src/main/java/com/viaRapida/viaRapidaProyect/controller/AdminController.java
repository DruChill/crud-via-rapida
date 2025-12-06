package com.viaRapida.viaRapidaProyect.controller;

import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.model.Destination;
import com.viaRapida.viaRapidaProyect.model.User;
import com.viaRapida.viaRapidaProyect.service.TicketService;
import com.viaRapida.viaRapidaProyect.service.DestinationService;
import com.viaRapida.viaRapidaProyect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private DestinationService destinationService;
    
    @Autowired
    private UserService userService;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password";

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            session.setAttribute("admin", true);
            return "redirect:/admin";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/admin")
    public String showAdminPage(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        model.addAttribute("tickets", ticketService.getAllTickets());
        return "admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditTicketPage(@PathVariable("id") Long id, HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        Ticket ticket = ticketService.getTicketById(id);
        model.addAttribute("ticket", ticket);
        model.addAttribute("destinations", destinationService.getAllDestinations());
        model.addAttribute("users", userService.getAllUsers());
        return "editTicket";
    }

    @PostMapping("/admin/edit/{id}")
    public String editTicket(@PathVariable("id") Long id,
                             @RequestParam("userId") Long userId,
                             @RequestParam("fromDestinationId") Long fromDestinationId,
                             @RequestParam("toDestinationId") Long toDestinationId,
                             @RequestParam("travelDate") String travelDate,
                             @RequestParam("travelTime") String travelTime,
                             @RequestParam("seatNumber") String seatNumber,
                             @RequestParam("price") double price,
                             HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        
        Ticket ticket = ticketService.getTicketById(id);
        if (ticket != null) {
            User user = userService.getUserById(userId).orElse(null);
            Destination fromDest = destinationService.getDestinationById(fromDestinationId).orElse(null);
            Destination toDest = destinationService.getDestinationById(toDestinationId).orElse(null);
            
            ticket.setUser(user);
            ticket.setFromDestination(fromDest);
            ticket.setToDestination(toDest);
            ticket.setTravelDate(java.time.LocalDate.parse(travelDate));
            ticket.setTravelTime(travelTime);
            ticket.setSeatNumber(seatNumber);
            ticket.setPrice(price);
            
            ticketService.saveTicket(ticket);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteTicket(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        ticketService.deleteTicketById(id);
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}