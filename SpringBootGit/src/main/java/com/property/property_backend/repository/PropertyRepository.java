/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.property.property_backend.repository;

import com.property.property_backend.model.Property;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PropertyRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Property> getAllProperties() {

        String sql = "SELECT * FROM properties";

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Property.class)
        );
    }
}
