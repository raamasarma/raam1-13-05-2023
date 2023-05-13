package com.raithanna.dairy.RaithannaDairy.controller;

import com.raithanna.dairy.RaithannaDairy.models.bank;
import com.raithanna.dairy.RaithannaDairy.repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class bankController {
    @Autowired
    private BankRepository bankRepository;
    @GetMapping("/bank")
    private String bankNameForm(Model model, HttpSession session){
        bank b=new bank();
        model.addAttribute("bank",b);
        return "bank";
    }
    @PostMapping("/bank")
    public String saveBank(Model model,bank bn){
        bankRepository.save(bn);
        bank b=new bank();
        model.addAttribute("bank",b);
        return "bank";
    }

}
