package com.raithanna.dairy.RaithannaDairy.controller;

import com.raithanna.dairy.RaithannaDairy.models.vehicle;
import com.raithanna.dairy.RaithannaDairy.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class vehicleController {
    @Autowired
    private VehicleRepository  vehicleRepository;
    @GetMapping("/vehicle")
    private String vehicleForm(Model model, HttpSession session){
        vehicle v=new vehicle();
        model.addAttribute("vehicle",v);
        return "vehicle";
    }
    @PostMapping("/vehicle")
    public String savevehicle(Model model,vehicle vh){
        vehicleRepository.save(vh);
        vehicle v=new vehicle();
        model.addAttribute("vehicle",v);
        return "vehicle";
    }
}
