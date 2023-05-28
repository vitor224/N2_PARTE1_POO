package com.example.ContactManager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ContactController {
    private Map<String, String> contacts = new HashMap<>();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", contacts);
        return "index";
    }

    @PostMapping("/addContact")
    public String addContact(@RequestParam String name, @RequestParam String phoneNumber) {
        contacts.put(name, phoneNumber);
        return "redirect:/";
    }
}
