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

    // READ
    public List<Property> getAllProperties() {
        String sql = "SELECT * FROM properties";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Property.class));
    }

    // INSERT
    public int addProperty(Property p) {

        String sql = "INSERT INTO properties (" +
                "realSum, room_private, person_capacity, host_is_superhost, multi, biz," +
                "cleanliness_rating, guest_satisfaction_overall, bedrooms," +
                "dist, metro_dist, attr_index_norm, rest_index_norm," +
                "lng, lat, city, time_of_week" +
                ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        return jdbcTemplate.update(sql,
                p.getRealSum(),
                p.getRoom_private(),
                p.getPerson_capacity(),
                p.getHost_is_superhost(),
                p.getMulti(),
                p.getBiz(),
                p.getCleanliness_rating(),
                p.getGuest_satisfaction_overall(),
                p.getBedrooms(),
                p.getDist(),
                p.getMetro_dist(),
                p.getAttr_index_norm(),
                p.getRest_index_norm(),
                p.getLng(),
                p.getLat(),
                p.getCity(),
                p.getTime_of_week()
        );
    }

    // UPDATE
    public int updateProperty(int id, Property p) {

        String sql = "UPDATE properties SET city=?, bedrooms=? WHERE id=?";

        return jdbcTemplate.update(sql,
                p.getCity(),
                p.getBedrooms(),
                id
        );
    }

    // DELETE
    public int deleteProperty(int id) {

        String sql = "DELETE FROM properties WHERE id=?";

        return jdbcTemplate.update(sql, id);
    }
}
