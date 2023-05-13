package com.raithanna.dairy.RaithannaDairy.controller;
import com.raithanna.dairy.RaithannaDairy.models.supplier;
import com.raithanna.dairy.RaithannaDairy.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class supplierController {
    @Autowired
    private SupplierRepository supplierRepository;
    @GetMapping("/supplier")
    private String supplierForm(Model model){
        supplier su=new supplier();
        model.addAttribute("supplier",su);
        return "supplier";
    }
    @PostMapping("/supplier")
    public String saveSupplier(Model model,supplier sr){
        String supName = sr.getSupplierName();
        String initials = Arrays.stream(supName.split(" "))
                .map(s -> s.substring(0, 1))
                .collect(Collectors.joining());
        System.out.println("----"+initials);
    Long sup = supplierRepository.count();
          Integer count = (int) (sup+1);
        String format = String.format("%03d", count);
        // sr.setSupplierCode(initials.toUpperCase()+"_"+count.toString());
        sr.setSupplierCode(initials.toUpperCase()+"_"+format);
        supplierRepository.save(sr);
        supplier su=new supplier();
        model.addAttribute("supplier",su);
        return "supplier";
    }
}
