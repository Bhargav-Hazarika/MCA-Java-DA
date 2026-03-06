/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.property.property_backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    private int id;

    private double realSum;
    private int room_private;
    private double person_capacity;
    private int host_is_superhost;

    private int multi;
    private int biz;

    private double cleanliness_rating;
    private double guest_satisfaction_overall;

    private int bedrooms;

    private double dist;
    private double metro_dist;

    private double attr_index_norm;
    private double rest_index_norm;

    private double lng;
    private double lat;

    private String city;

    private int time_of_week;
}