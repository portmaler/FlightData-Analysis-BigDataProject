import React, { useState, useEffect } from "react";
import { Map } from "./components/Map";
import Mapbox from "./components/MapBox";
import Chart from "./components/Chart";

function App() {
  // State variables to hold the data from APIs
  const [flightSpeedestData, setFlightSpeedestData] = useState([]);
  const [countryTopFlightData, setCountryTopFlightData] = useState([]);

  useEffect(() => {
    fetchFlightSpeedestData();
    fetchCountryTopFlightData();

    // Fetch data every 10 seconds for both charts
    const interval = setInterval(() => {
      fetchFlightSpeedestData();
      fetchCountryTopFlightData();
    }, 10000);

    return () => clearInterval(interval); // Cleanup the interval when the component is unmounted
  }, []);

  const fetchFlightSpeedestData = () => {
    fetch("http://192.168.243.10:9090/api/flight-speedest")
      .then((response) => response.json())
      .then((data) => {
        setFlightSpeedestData(data);
      })
      .catch((error) => {
        console.error("Error fetching flight speedest data:", error);
      });
  };

  const fetchCountryTopFlightData = () => {
    fetch("http://192.168.243.10:9090/api/country-top-flight")
      .then((response) => response.json())
      .then((data) => {
        setCountryTopFlightData(data);
      })
      .catch((error) => {
        console.error("Error fetching country top flight data:", error);
      });
  };

  return (
    <div
      style={{
        display: "grid",
        gridTemplateRows: "1fr 1fr", // Two vertical areas
        gridTemplateColumns: "1fr", // One column
        height: "100vh",
        width: "100vw",
        gridTemplateAreas: `
          "charts"
          "map"
        `,
      }}
    >
      <div
        style={{
          gridArea: "charts",
          display: "grid",
          gridTemplateColumns: "1fr 1fr", // Two columns for the charts
          gap: "16px", // Add some gap between the charts
        }}
      >
        <div>
          <h1 style={{ textAlign: "center" }}>Top Speediest Plans</h1>
          <Chart
            labels={flightSpeedestData.map((item) => item.hex)}
            data1={flightSpeedestData.map((item) => item.max_speed)}
            title="Top Speediest Plans"
            backgroundColor1="rgba(255, 99, 132, 0.5)" // Color for dataset 1
            yAxisMin={0}
            yAxisMax={1200}
          />
        </div>
        <div>
          <h1 style={{ textAlign: "center" }}>Country Top Flight</h1>
          <Chart
            labels={countryTopFlightData.map((item) => item.country)}
            data1={countryTopFlightData.map((item) => item.flightCount)}
            title="Country Top Flight"
            backgroundColor1="rgba(53, 162, 235, 0.5)" // Color for dataset 1
            yAxisMin={0}
            yAxisMax={50}
          />
        </div>
      </div>
      <div style={{ gridArea: "map", overflow: "auto" }}>
        <Mapbox />
      </div>
    </div>
  );
}

export default App;
