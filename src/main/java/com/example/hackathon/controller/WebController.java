package com.example.hackathon.controller;

import com.example.hackathon.model.RolUsuario;
import com.example.hackathon.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final UsuarioRepository usuarioRepository;

    public WebController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/")
    public String landing(Authentication authentication, Model model) {
        boolean autenticado = authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getName());
        model.addAttribute("autenticado", autenticado);

        String panelUrl = "/login";
        if (autenticado) {
            panelUrl = usuarioRepository.findByEmail(authentication.getName())
                    .map(usuario -> usuario.getRol() == RolUsuario.DONADOR ? "/donador/dashboard" : "/beneficiario/dashboard")
                    .orElse("/");
        }
        model.addAttribute("panelUrl", panelUrl);
        return "landing";
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
