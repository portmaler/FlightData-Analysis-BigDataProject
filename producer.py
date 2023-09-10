import requests
from confluent_kafka import Producer
import json
import time

WEBSITE_URL = "https://airlabs.co/api/v9/flights?api_key=595a933f-71d6-4044-91a7-3e9522fa7554&airline_icao=RAM"
KAFKA_SERVER = "localhost:9092"
KAFKA_TOPIC = "flights"

def fetch_data():
    response = requests.get(WEBSITE_URL)
    if response.status_code == 200:
        response_data = response.json()
        flights = response_data.get("response")  # Return the first 20 flights
        return flights
    else:
        print(f"Failed to fetch data: {response.status_code}")
        return None

def produce_to_kafka(data, producer):
    data_json = json.dumps(data)
    producer.produce(KAFKA_TOPIC, value=data_json.encode('utf-8'))
    producer.flush()
    print("Data produced to Kafka")

if __name__ == "__main__":
    producer = Producer({"bootstrap.servers": KAFKA_SERVER})
    while True:
        data = fetch_data()
        if data:
            if isinstance(data, list) and len(data) > 0:
                produce_to_kafka(data, producer)  # Send the entire list of flights as one message
            else:
                print("No data available")
        else:
            print("Failed to fetch data from the API")
        time.sleep(20)

