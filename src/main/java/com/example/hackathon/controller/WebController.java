package com.example.hackathon.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String landing() {
        return "landing"; // templates/landing.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // templates/login.html
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro"; // templates/registro.html
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile"; // templates/profile.html
    }

    @GetMapping("/publicar")
    public String publicar() {
        return "publicar_donacion";
    }

    @GetMapping("/donador")
    public String donador() {
        return "formularioDonador";
    }

    @GetMapping("/beneficiario")
    public String beneficiario() {
        return "formularioBeneficiario";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "compartidas/acceso-denegado";
    }
}