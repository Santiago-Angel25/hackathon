package com.example.hackathon.dto;

import com.example.hackathon.model.RolUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistroUsuarioDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "El correo no es valido")
    @NotBlank(message = "El correo es obligatorio")
    private String email;

    @NotBlank(message = "La contrasena es obligatoria")
    @Size(min = 6, message = "La contrasena debe tener minimo 6 caracteres")
    private String password;

    @NotBlank(message = "La confirmacion de contrasena es obligatoria")
    private String confirmPassword;

    @NotBlank(message = "El telefono es obligatorio")
    private String telefono;

    private String direccion;
    private String documento;
    private String tipoPersona;

    @NotNull(message = "El rol es obligatorio")
    private RolUsuario rol;
}
