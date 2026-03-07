/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.property.property_backend.service;

import com.property.property_backend.model.Property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

@Service
public class PropertyService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateProperty(int id, Map<String, Object> updates) {

        StringBuilder sql = new StringBuilder("UPDATE properties SET ");

        int i = 0;
        Object[] values = new Object[updates.size() + 1];

        for (Map.Entry<String, Object> entry : updates.entrySet()) {

            if (i > 0) sql.append(", ");

            sql.append(entry.getKey()).append(" = ?");
            values[i] = entry.getValue();

            i++;
        }

        sql.append(" WHERE id = ?");
        values[i] = id;

        jdbcTemplate.update(sql.toString(), values);
    }
    
//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    public List<Property> filter(String city,
                                 Integer bedrooms,
                                 Integer person_capacity,
                                 Double realSum) {

        StringBuilder sql = new StringBuilder("SELECT * FROM properties WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (city != null && !city.isEmpty()) {
            sql.append(" AND city = ?");
            params.add(city);
        }

        if (bedrooms != null) {
            sql.append(" AND bedrooms = ?");
            params.add(bedrooms);
        }

        if (person_capacity != null) {
            sql.append(" AND person_capacity = ?");
            params.add(person_capacity);
        }

        if (realSum != null) {
            sql.append(" AND realSum <= ?");
            params.add(realSum);
        }

        return jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                new BeanPropertyRowMapper<>(Property.class)
        );
    }
    
    
    
    public Map<String,Object> getPaginatedProperties(int page){

        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        String sql = "SELECT * FROM properties LIMIT ? OFFSET ?";
        List<Property> properties = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Property.class),
                pageSize,
                offset
        );

        Integer totalCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM properties",
                Integer.class
        );

        int totalPages = (int)Math.ceil((double)totalCount / pageSize);

        Map<String,Object> result = new HashMap<>();
        result.put("items", properties);
        result.put("page", page);
        result.put("totalPages", totalPages);

        return result;
    }
}