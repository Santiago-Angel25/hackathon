package com.example.hackathon.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String landing() {
        return "compartidas/landing";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "auth/registro";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "compartidas/acceso-denegado";
    }

    @GetMapping("/ejemplo")
    public String ejemplo() {
        return "ejemplo-uso";
    }
}