import React, { useState, useEffect } from "react";
import Plan from "../assets/plan.png";
import ReactMapGL, { Marker, Popup } from "react-map-gl";
import axios from "axios";

function Mapbox() {
  const [viewport, setViewport] = useState({
    width: "50vw",
    height: "50vh",
    latitude: 10.86195853994233,
    longitude: 106.74362380706191,
    zoom: 1,
  });

  useEffect(() => {
    function handleResize() {
      setViewport((prevViewport) => ({
        ...prevViewport,
        width: `${window.innerWidth}px`,
        height: `${window.innerHeight}px`,
      }));
    }

    window.addEventListener("resize", handleResize);

    return () => window.removeEventListener("resize", handleResize);
  }, []);

  const [showPopup, togglePopup] = useState(false);
  const [flightCoordinates, setFlightCoordinates] = useState([]);

  const fetchFlightCoordinates = () => {
    axios
      .get("http://192.168.243.10:9090/api/flight-coordinate")
      .then((response) => {
        setFlightCoordinates(response.data);
      })
      .catch((error) => {
        console.error("Error fetching flight coordinates:", error);
      });
  };

  useEffect(() => {
    fetchFlightCoordinates(); // Call initially when the component mounts

    // Call the fetchFlightCoordinates function every 10 seconds
    const interval = setInterval(fetchFlightCoordinates, 10000);

    // Clean up the interval when the component unmounts
    return () => clearInterval(interval);
  }, []);

  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
      }}
    >
      <ReactMapGL
        {...viewport}
        mapStyle="mapbox://styles/mapbox/streets-v11"
        onViewportChange={(viewport) => setViewport(viewport)}
        mapboxApiAccessToken="pk.eyJ1IjoidHJ1bmdwaGFuOTkiLCJhIjoiY2txZmI3cDl5MG42ODJvc2N1emRqcndqYyJ9.-QdtnY-bLP8PSXMwwXuQEA"
      >
        {showPopup && (
          <Popup
            latitude={10.86195853994233}
            longitude={106.74362380706191}
            closeButton={true}
            closeOnClick={true}
            onClose={() => togglePopup(false)}
            anchor="top-right"
          >
            <div>Pop up marker</div>
          </Popup>
        )}

        {flightCoordinates.map((coordinate) => (
          <Marker
            key={coordinate.hex}
            latitude={coordinate.lat}
            longitude={coordinate.lng}
            offsetLeft={-20}
            offsetTop={-30}
          >
            <img
              onClick={() => togglePopup(true)}
              style={{ height: 50, width: 50 }}
              src={Plan}
              alt="Flight marker"
            />
          </Marker>
        ))}
      </ReactMapGL>
      <img style={{ height: 50, width: 50 }} src={Plan} alt="Plan icon" />
    </div>
  );
}

export default Mapbox;
