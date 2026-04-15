package com.example.hackathon.service;

import java.util.List;

import com.example.hackathon.model.Donacion;
import com.example.hackathon.repository.DonacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hackathon.model.TipoDonacion;


@Service
public class DonacionService {

    @Autowired
    private DonacionRepository repo;

    public List<Donacion> listar() {
        return repo.findAll();
    }

    public Donacion guardar(Donacion d) {
        return repo.save(d);
    }
}
