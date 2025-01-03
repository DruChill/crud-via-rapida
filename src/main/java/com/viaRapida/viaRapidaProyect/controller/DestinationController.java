package com.viaRapida.viaRapidaProyect.controller;

import com.viaRapida.viaRapidaProyect.model.Destination;
import com.viaRapida.viaRapidaProyect.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DestinationController {
    @Autowired
    private DestinationService destinationService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Destination> listDestinations = destinationService.getAllDestinations();
        model.addAttribute("listDestinations", listDestinations);
        return "index";
    }
}