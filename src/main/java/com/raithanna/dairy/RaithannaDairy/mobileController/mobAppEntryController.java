package com.raithanna.dairy.RaithannaDairy.mobileController;

import com.raithanna.dairy.RaithannaDairy.models.userModel;
import com.raithanna.dairy.RaithannaDairy.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class mobAppEntryController {
    @Autowired
    UserModelRepository userModelRepository;

    @PostMapping(value = "/registerMobile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> registerMob(Model model, @RequestBody userModel user, HttpServletRequest request, HttpSession session) {
        List<String> messages = new ArrayList<>();
        System.out.println(user);
        try {
            userModelRepository.save(user);
            return ResponseEntity.status(202).body("Successfully Created(CODE 202)\n");
        } catch (Exception handlerException) {
            model.addAttribute("messages", messages);
            return ResponseEntity.status(203).body("Error Creating your Account pls retry (CODE 203)\n");
        }

    }
    @PostMapping(value = "/loginMobile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> loginMob(Model model, @RequestBody Map<String,String> loginCred, HttpServletRequest request, HttpSession session) {
        List<String> messages = new ArrayList<>();
        System.out.println(loginCred);
        try {
            userModel user = userModelRepository.findByLoginIDAndPassword(loginCred.get("mobile").toString(), loginCred.get("password").toString());
            if (user == null){
                return ResponseEntity.status(203).body("Error logging in! retry Mismatched password and phone number\n");
            }
            return ResponseEntity.status(202).body("Successfully Logged\n");
        } catch (Exception handlerException) {
            model.addAttribute("messages", messages);
            return ResponseEntity.status(203).body("Error logging in! retry Mismatched password and phone number\n");
        }

    }
}
