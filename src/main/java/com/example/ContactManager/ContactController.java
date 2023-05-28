package com.example.ContactManager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ContactController {
    private Map<String, Contact> contacts = new HashMap<>();

    @GetMapping("/")
    public String listContacts(Model model) {
        model.addAttribute("contacts", contacts.values());
        return "list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "add";
    }

    @PostMapping("/add")
    public String addContact(@ModelAttribute Contact contact) {
        contacts.put(contact.getName(), contact);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchContact(@RequestParam String name, Model model) {
        Contact contact = contacts.get(name);
        model.addAttribute("contacts", (contact != null) ? Collections.singletonList(contact) : contacts.values());
        return "list";
    }

    @GetMapping("/delete/{name}")
    public String deleteContact(@PathVariable String name) {
        contacts.remove(name);
        return "redirect:/";
    }

}