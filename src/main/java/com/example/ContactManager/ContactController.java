package com.example.ContactManager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
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
        saveContactsToDisk(); // Salva os contatos no disco após adicionar um novo contato
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchContact(@RequestParam String name, Model model) {
        try {
            Contact contact = contacts.get(name);
            if (contact != null) {
                model.addAttribute("contacts", Collections.singletonList(contact));
                return "list";
            } else {
                throw new Exception("Contato não encontrado.");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "listError";
    }

    @GetMapping("/delete/{name}")
    public String deleteContact(@PathVariable String name) {
        contacts.remove(name);
        saveContactsToDisk(); // Salva os contatos no disco após remover um contato
        return "redirect:/";
    }


// ...

    private static final String FILE_PATH = "contatos.json";

    private void saveContactsToDisk() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            Gson gson = new Gson();
            gson.toJson(contacts, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar contatos no disco: " + e.getMessage());
        }
    }

    private void loadContactsFromDisk() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Contact>>() {}.getType();
            contacts = gson.fromJson(reader, type);
            if (contacts == null) {
                contacts = new HashMap<>();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo de contatos não encontrado. Será criado um novo arquivo ao salvar os contatos.");
        } catch (IOException e) {
            System.err.println("Erro ao carregar contatos do disco: " + e.getMessage());
        }
    }


}