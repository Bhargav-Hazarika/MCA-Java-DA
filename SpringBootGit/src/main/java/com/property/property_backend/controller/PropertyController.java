/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.property.property_backend.controller;

import com.property.property_backend.model.Property;
import com.property.property_backend.repository.PropertyRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertyController {

    @Autowired
    private PropertyRepository repo;

    @GetMapping("/properties")
    public List<Property> getAllProperties() {
        return repo.getAllProperties();
    }
}