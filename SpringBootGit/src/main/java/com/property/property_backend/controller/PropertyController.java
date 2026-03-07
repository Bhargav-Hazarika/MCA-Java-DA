/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.property.property_backend.controller;

import com.property.property_backend.model.Property;
import com.property.property_backend.repository.PropertyRepository;
import com.property.property_backend.service.PropertyService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    private PropertyRepository repo;

    // READ
    @GetMapping
    public List<Property> getAllProperties() {
        return repo.getAllProperties();
    }

    // INSERT
    @PostMapping
    public String addProperty(@RequestBody Property property) {
        System.out.println(property);
        repo.addProperty(property);
        return "Property added";
    }

    // UPDATE
    @PutMapping("/{id}")
    public String updateProperty(@PathVariable int id, @RequestBody Property property) {
        repo.updateProperty(id, property);
        return "Property updated";
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteProperty(@PathVariable int id) {
        repo.deleteProperty(id);
        return "Property deleted";
    }
    
    @Autowired
    private PropertyService propertyService;

    @PatchMapping("/update/{id}")
    public String updateProperty(@PathVariable int id,
                                 @RequestBody Map<String, Object> updates) {

        propertyService.updateProperty(id, updates);
        return "Property updated successfully";
    }
    
    @GetMapping("/filter")
    public List<Property> filterProperties(
            @RequestParam(required=false) String city,
            @RequestParam(required=false) Integer bedrooms,
            @RequestParam(required=false) Integer person_capacity,
            @RequestParam(required=false) Double realSum){

        return propertyService.filter(city,bedrooms,person_capacity,realSum);
    }
    
    @GetMapping("/page")
    public Map<String,Object> getPaginatedProperties(
            @RequestParam(defaultValue = "1") int page){

        return propertyService.getPaginatedProperties(page);
    }
}
