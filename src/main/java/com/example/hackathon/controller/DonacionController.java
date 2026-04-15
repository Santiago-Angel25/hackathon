package com.example.hackathon.controller;

import java.util.List;

import com.example.hackathon.service.DonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.hackathon.model.Donacion;

@RestController
@RequestMapping("/donaciones")
@CrossOrigin("*")
public class DonacionController {

    @Autowired
    private DonacionService service;

    @GetMapping
    public List<Donacion> listar() {
        return service.listar();
    }

    @PostMapping
    public Donacion guardar(@RequestBody Donacion d) {
        return service.guardar(d);
    }
}