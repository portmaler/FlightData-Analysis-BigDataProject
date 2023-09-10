import React, { Fragment } from "react";
import { Marker } from "react-map-gl";

const Markers = () => {
  const markers = [
    { latitude: 41.893748, longitude: -87.661557 }, // Chicago
    { latitude: 40.712776, longitude: -74.005974 }, // New York
    { latitude: 34.052235, longitude: -118.243683 }, // Los Angeles
    { latitude: 51.5074, longitude: -0.1278 }, // London
    { latitude: 48.8566, longitude: 2.3522 }, // Paris
  ];

  return (
    <Fragment>
      {markers.map((marker, index) => (
        <Marker
          key={index}
          offsetTop={-24}
          offsetLeft={-12}
          latitude={marker.latitude}
          longitude={marker.longitude}
        >
          <img src="https://img.icons8.com/color/48/000000/marker.png" alt={`Marker ${index}`} />
        </Marker>
      ))}
    </Fragment>
  );
};

export default Markers;
