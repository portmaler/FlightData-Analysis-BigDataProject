package com.sisdba.SparkClient.dto;

public class FlightSpeedestDTO {
    private String hex;
    private int max_speed;

    public FlightSpeedestDTO() {
        // Default constructor is required for serialization/deserialization by Kafka
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public int getMax_speed() {
        return max_speed;
    }

    public void setMax_speed(int max_speed) {
        this.max_speed = max_speed;
    }
}
