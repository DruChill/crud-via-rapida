package com.viaRapida.viaRapidaProyect.controller;

import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @Autowired
    private TicketService ticketService;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password";

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model) {
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            return "redirect:/admin";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        model.addAttribute("tickets", ticketService.getAllTickets());
        return "admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditTicketPage(@PathVariable("id") Long id, Model model) {
        Ticket ticket = ticketService.getTicketById(id);
        model.addAttribute("ticket", ticket);
        return "editTicket";
    }

    @PostMapping("/admin/edit/{id}")
    public String editTicket(@PathVariable("id") Long id,
                             @ModelAttribute("ticket") Ticket ticket) {
        ticket.setId(id);
        ticketService.saveTicket(ticket);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteTicket(@PathVariable("id") Long id) {
        ticketService.deleteTicketById(id);
        return "redirect:/admin";
    }
}