/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.property.property_backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;

import java.net.URL;
import java.net.HttpURLConnection;

import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.HashMap;
import org.springframework.stereotype.Controller;

@Controller
public class PredictionController {

    @PostMapping("/predict")
    public String predict(
            @RequestParam Map<String,String> params,
            Model model){

        try {

            URL url = new URL("http://localhost:5000/predict");

            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Convert form → JSON
            Map<String,Object> data = new HashMap<>();

            data.put("city", params.get("city"));
            data.put("bedrooms", params.get("bedrooms"));
            data.put("person_capacity", params.get("person_capacity"));
            data.put("cleanliness_rating", params.get("cleanliness_rating"));
            data.put("guest_satisfaction_overall", params.get("guest_satisfaction_overall"));
            data.put("dist", params.get("dist"));
            data.put("metro_dist", params.get("metro_dist"));
            data.put("attr_index_norm", params.get("attr_index_norm"));
            data.put("rest_index_norm", params.get("rest_index_norm"));
            data.put("price_per_person", params.get("price_per_person"));

            data.put("room_private", params.containsKey("room_private") ? 1 : 0);
            data.put("host_is_superhost", params.containsKey("host_is_superhost") ? 1 : 0);
            data.put("multi", params.containsKey("multi") ? 1 : 0);
            data.put("biz", params.containsKey("biz") ? 1 : 0);
            data.put("time_of_week", params.containsKey("time_of_week") ? 1 : 0);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(data);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();

            BufferedReader reader =
                    new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String line;
            StringBuilder response = new StringBuilder();

            while((line = reader.readLine()) != null){
                response.append(line);
            }

            reader.close();

            Map<String,Object> result =
                    mapper.readValue(response.toString(), Map.class);

            model.addAttribute("prediction", result.get("prediction"));

        } catch(Exception e){
            model.addAttribute("prediction", "Error: " + e.getMessage());
        }

        return "predict";
    }
}