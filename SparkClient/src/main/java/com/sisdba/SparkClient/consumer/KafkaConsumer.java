package com.sisdba.SparkClient.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sisdba.SparkClient.dto.FlightCoordinateDTO;
import com.sisdba.SparkClient.dto.FlightSpeedestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private ArrayList<FlightCoordinateDTO> flightCoordinateDTO = new ArrayList<>();
    private ArrayList<FlightSpeedestDTO> flightSpeedestDTO = new ArrayList<>();

    @KafkaListener(topics = "flight-coordinate", groupId = "coordinate_group_id")
    public void consumeCoordinate(String message) {
        // Assuming the message is in JSON format

        System.out.println("flight-coordinate: " + message);
        flightCoordinateDTO.add(parseMessageToCoordinateDTO(message));
    }

    @KafkaListener(topics = "flight-speedest", groupId = "speedest_group_id")
    public void consumeSpeedest(String message) {
        // Assuming the message is in JSON format
        if(flightSpeedestDTO.size()>=10) flightSpeedestDTO.remove(0);
        System.out.println("flight-speedest: " + message);
        flightSpeedestDTO.add(parseMessageToSpeedestDTO(message));
    }

    private FlightCoordinateDTO parseMessageToCoordinateDTO(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Deserialize the JSON message to FlightCoordinateDTO
            return objectMapper.readValue(message, FlightCoordinateDTO.class);
        } catch (Exception e) {
            logger.error("Error parsing flight-coordinate message: {}", message, e);
            // You can also throw an exception here or handle it as appropriate
            return null;
        }
    }

    private FlightSpeedestDTO parseMessageToSpeedestDTO(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Deserialize the JSON message to FlightSpeedestDTO
            return objectMapper.readValue(message, FlightSpeedestDTO.class);
        } catch (Exception e) {
            logger.error("Error parsing flight-speedest message: {}", message, e);
            // You can also throw an exception here or handle it as appropriate
            return null;
        }
    }

    public ArrayList<FlightCoordinateDTO> getFlightCoordinateDTO() {
        return flightCoordinateDTO;
    }

    public ArrayList<FlightSpeedestDTO> getFlightSpeedestDTO() {
        return flightSpeedestDTO;
    }
}
