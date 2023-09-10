package com.sisdba.SparkClient.controllers;

import com.sisdba.SparkClient.consumer.KafkaConsumer;
import com.sisdba.SparkClient.dto.FlightSpeedestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping("/api")
public class FlightSpeedestController {

    @Autowired
    private KafkaConsumer kafkaConsumer;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/flight-speedest")
    @ResponseBody
    public ArrayList<FlightSpeedestDTO> getFastestFlights() {
        return kafkaConsumer.getFlightSpeedestDTO();
    }
}