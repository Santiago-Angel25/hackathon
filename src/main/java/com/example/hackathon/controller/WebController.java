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
        return "landing";
    }

    @GetMapping("/profile")
    public String profile() {
        return "landing";
    }

    @GetMapping("/publicar")
    public String publicar() {
        return "publicar_donacion";
    }

    @GetMapping("/donador")
    public String donador() {
        return "formularioDonador";
    }

    @GetMapping("/donador/dashboard")
    public String donadorDashboard() {
        return "donador";
    }

    @GetMapping("/beneficiario")
    public String beneficiario() {
        return "formularioBeneficiario";
    }

    @GetMapping("/beneficiario/dashboard")
    public String beneficiarioDashboard() {
        return "beneficiario";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "landing";
    }
}
