package com.sisdba.SparkClient.dto;

public class FlightCoordinateDTO {
    private String hex;
    private double lat;
    private double lng;
    private int speed;
    private String flag;

    // Default constructor is required for serialization/deserialization by Kafka
    public FlightCoordinateDTO() {}

    // Constructor with fields
    public FlightCoordinateDTO(String hex, double lat, double lng, int speed, String flag) {
        this.hex = hex;
        this.lat = lat;
        this.lng = lng;
        this.speed = speed;
        this.flag = flag;
    }


    // Getters and setters
    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
