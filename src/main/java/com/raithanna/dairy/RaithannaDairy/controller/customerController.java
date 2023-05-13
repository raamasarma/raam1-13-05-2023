package com.raithanna.dairy.RaithannaDairy.controller;

import com.fasterxml.jackson.core.JacksonException;
import com.raithanna.dairy.RaithannaDairy.models.customer;
import com.raithanna.dairy.RaithannaDairy.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class customerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/createCustomer")
    public String createCustomerForm(Model model) {
        customer cm = new customer();

        // model.addAttribute("msg", "customer created");
        model.addAttribute("Custome", cm);
        return "Custome";

    }
    @GetMapping("/createCustomer1")
    public String createCustomerForm1(Model model) {
        customer cm = new customer();

        model.addAttribute("msg", "customer created");
        model.addAttribute("Custome", cm);
        return "Custome";

    }
    @GetMapping("/createCustomer2")
    public String createCustomerForm2(Model model) {
        customer cm = new customer();

        model.addAttribute("msg", "customer already created");
        model.addAttribute("Custome", cm);
        return "Custome";

    }


    @PostMapping("/createCustomer")
    public ModelAndView saveCustomer(Model model, customer Customer) {
        customer custWithMaxCustno = customerRepository.findTopByOrderByCustSeriesDesc();
        String result="";
        Integer maxCust_no = 80;
        if (custWithMaxCustno != null) {
            maxCust_no = custWithMaxCustno.getCustSeries();
            System.out.println(maxCust_no);
            maxCust_no++;
        }
        Customer.setCustSeries(maxCust_no);
        Customer.setCustCode("RDML00" + (maxCust_no));
        System.out.println(Customer);
        String mobileNo= Customer.getMobileNo();
        customer existingCheck = customerRepository.findByMobileNo(mobileNo);
        if(existingCheck!=null)
        {
            model.addAttribute("msg", "customer already created");
            System.out.println("if customer Not Created");
            result ="createCustomer2";
            return new ModelAndView( "redirect:/createCustomer2");
        }else{

            System.out.println("customer Created");
            customerRepository.save(Customer);

            model.addAttribute("msg", "customer created");


        }
        return new ModelAndView( "redirect:/createCustomer1");


    }


    @RequestMapping("/customerEdit")
    public String createOrder_html(Model model, HttpSession session) {
        if (session.getAttribute("loggedIn").equals("yes")) {
            Iterable<customer> CustomersIterable = customerRepository.findAll();
            List<customer> Customers = new ArrayList<>();
            for (customer Customer : CustomersIterable) {
                Customers.add(Customer);
            }


            model.addAttribute("customers", Customers);
            return "customerEdit";
        }
        List<Object> messages = new ArrayList<>();
        messages.add("Login First");
        model.addAttribute("messages", messages);
        customer cm = new customer();
        model.addAttribute("Custome", cm);
        return "redirect:/custome";
    }

    //getcustvalue i.e name,phonenumber,email
    @PostMapping("/getCustValues")
    public ResponseEntity<?> getCustValues(@RequestParam Map<String, String> body) {
        Map<String, String> respBody = new HashMap<>();
        System.out.println(body);
        customer Customer = customerRepository.findByCustCode(body.get("code"));
        respBody.putIfAbsent("custName", Customer.getCustName());
        respBody.putIfAbsent("phoneNumber", Customer.getMobileNo());
        respBody.putIfAbsent("Email", Customer.getEmail());
        return ResponseEntity.ok(respBody);
    }

    @PostMapping("/customerEdit")
    public ResponseEntity<?> customerEdit(@RequestParam Map<String, String> body) {
        Map<String, String> respBody = new HashMap<>();
        System.out.println(body);
        customer Customer = customerRepository.findByCustCode(body.get("code"));
        Customer.setCustName(body.get("custName"));
        Customer.setMobileNo(body.get("phone"));
        Customer.setEmail(body.get("email"));
        respBody.putIfAbsent("message", "success");
        return ResponseEntity.ok(respBody);
    }

}
