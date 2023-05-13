package com.raithanna.dairy.RaithannaDairy.controller;

import com.raithanna.dairy.RaithannaDairy.models.userModel;
import com.raithanna.dairy.RaithannaDairy.repositories.UserModelRepository;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class homeContoller {
    Logger logger = LoggerFactory.getLogger(homeContoller.class);
    @Autowired
    private UserModelRepository userModelRepository;

    @GetMapping("/")
    public String loginHtml() {
        return "welcomeScreen";
    }

    @PostMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }


    @PostMapping("/login")
    public String login(@RequestParam String mobile, Model model, @RequestParam String password, HttpServletRequest request, HttpSession session) {
        logger.info("home method info");
        logger.warn("home method warning");
        logger.error("home method error");
        System.out.println(mobile);
        System.out.println(password);
        List<String> messages = new ArrayList<>();

            userModel user = userModelRepository.findByLoginIDAndPassword(mobile, password);
            if (user == null) {
                messages.add("Account not found! retry ");
                model.addAttribute("messages", messages);
                return "/loginPage";
            }
            System.out.println(user);
            model.addAttribute("messages", messages);
            session.setAttribute("loggedIn", "yes");
            return "/home";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session, Model model) {
        List<String> messages = new ArrayList<>();
        messages.add("Logged out successfully");
        model.addAttribute("messages", messages);
        session.setAttribute("loggedIn", "no");
        return "login";
    }

    @RequestMapping("/homePage")
    public String homePage(Model model, HttpServletRequest request, HttpSession session) {
        List<String> messages = new ArrayList<>();
        messages.add("Home Page");
        model.addAttribute("messages", messages);
        session.setAttribute("loggedIn", "yes");
        return "home";
    }
    @GetMapping("/changePassword")
    public String changePassword() {

        return "changePassword";
    }
    @PostMapping("/changePassword")
    public String changePassword( HttpServletRequest request, Model model,@RequestParam String mobileNo,  @RequestParam String oldPassword, @RequestParam String newPassword) {
        System.out.println(mobileNo);
        System.out.println(oldPassword);
        System.out.println(newPassword);
//        System.out.println(con_password);
        userModel userModel = userModelRepository.findByLoginIDAndPassword(mobileNo, oldPassword);

        if(userModel!=null)
        {
            userModel.setPassword(newPassword);
            userModelRepository.save(userModel);
        }
        return "loginPage";
    }
}
