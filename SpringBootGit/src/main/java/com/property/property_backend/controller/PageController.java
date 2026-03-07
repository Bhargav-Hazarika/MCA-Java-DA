/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.property.property_backend.controller;

import com.property.property_backend.service.PropertyService;
import com.property.property_backend.model.Property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.property.property_backend.repository.PropertyRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.Map;
import java.util.HashMap;

@Controller
public class PageController {

    @Autowired
    private PropertyService propertyService;
    
    @Autowired
    private PropertyRepository propertyRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        var result = propertyService.getPaginatedProperties(page);

        model.addAttribute("items", result.get("items"));
        model.addAttribute("page", result.get("page"));
        model.addAttribute("totalPages", result.get("totalPages"));

        return "index";
    }

    @GetMapping("/filterPage")
    public String filterPage(
            @RequestParam(required=false) String city,
            @RequestParam(required=false) Integer bedrooms,
            @RequestParam(required=false) Integer person_capacity,
            @RequestParam(required=false) Double realSum,
            Model model){

        var properties = propertyService.filter(city, bedrooms, person_capacity, realSum);

        model.addAttribute("items", properties);

        return "index";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteProperty(@PathVariable int id){

        propertyRepository.deleteProperty(id);

        return "redirect:/";
    }
    
    @GetMapping("/update/{id}")
    public String updatePage(@PathVariable int id, Model model){

        String sql = "SELECT * FROM properties WHERE id=?";
        Property property = jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(Property.class),
                id
        );

        model.addAttribute("item", property);

        return "update";
    }
    
    @PostMapping("/update/{id}")
    public String updateProperty(
            @PathVariable int id,
            @RequestParam Map<String, String> params){

        Map<String,Object> updates = new HashMap<>();

        updates.put("city", params.get("city"));
        updates.put("realSum", params.get("realSum"));
        updates.put("person_capacity", params.get("person_capacity"));
        updates.put("cleanliness_rating", params.get("cleanliness_rating"));
        updates.put("guest_satisfaction_overall", params.get("guest_satisfaction_overall"));
        updates.put("bedrooms", params.get("bedrooms"));
        updates.put("dist", params.get("dist"));
        updates.put("metro_dist", params.get("metro_dist"));
        updates.put("attr_index_norm", params.get("attr_index_norm"));
        updates.put("rest_index_norm", params.get("rest_index_norm"));
        updates.put("lng", params.get("lng"));
        updates.put("lat", params.get("lat"));

        updates.put("room_private", params.containsKey("room_private") ? 1 : 0);
        updates.put("host_is_superhost", params.containsKey("host_is_superhost") ? 1 : 0);
        updates.put("biz", params.containsKey("biz") ? 1 : 0);
        updates.put("time_of_week", params.containsKey("time_of_week") ? 1 : 0);

        propertyService.updateProperty(id, updates);

        return "redirect:/";
    }
}