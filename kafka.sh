#!/bin/bash

##

# BASH script that checks the following:

#   - Memory usage

#   - CPU load

#   - Number of TCP connections

#   - Kernel version

##


##
# Memory check
##

server_name=$(hostname)

function create_flights_topic() {
    echo "#######"
    if output=$(~/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic flights 2>&1); then
        echo "Flights topic created successfully"
    else
        echo "Failed to create flights topic: $output"
    fi
}

function create_flights_status_topic() {
    echo "#######"
    if output=$(~/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic flight-status 2>&1); then
        echo "Flights-status topic created successfully"
    else
        echo "Failed to create flights-status topic: $output"
    fi
}

function create_flight_coordinate_topic() {
    echo "#######"
    if output=$(~/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic flight-coordinate 2>&1); then
        echo "Flights-coordinate topic created successfully"
    else
        echo "Failed to create flights-coordinate topic: $output"
    fi
}

function create_flight_speedest_topic() {
    echo "#######"
    if output=$(~/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic flight-speedest 2>&1); then
        echo "Flightspeedest topic created successfully"
    else
        echo "Failed to create flightspeedest topic: $output"
    fi
}

function create_flight_top_country_topic() {
    echo "#######"
    if output=$(~/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic flight-top-country 2>&1); then
        echo "Flight-topcountry topic created successfully"
    else
        echo "Failed to create flight-topcountry topic: $output"
    fi
}

function all_checks() {
    create_flights_topic
    create_flights_status_topic
    create_flight_coordinate_topic
    create_flight_speedest_topic
    create_flight_top_country_topic
}

all_checks

