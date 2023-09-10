import React, { useState } from "react";
import ReactMapGL from "react-map-gl";
import mapboxgl from "mapbox-gl";
import Markers from "./Markers";

export const Map = () => {
  // Set your Mapbox access token here

  const [mapViewport, setMapViewport] = useState({
    height: "100vh",
    width: "100vh",
    longitude: 2.571606,
    latitude: 45.226913,
    zoom: 1,
  });

  return (
    <ReactMapGL
      {...mapViewport}
      mapboxAccessToken="pk.eyJ1IjoiamV1bmFuZ2UiLCJhIjoiY2xrYnAzbXU5MGthbTNkcWRibDlrM2E4YyJ9.-cf2IcsCBZy2kN31Yje9zA"
      mapStyle="mapbox://styles/mapbox/dark-v10"
      onViewportChange={setMapViewport}
    >
      <Markers />
    </ReactMapGL>
  );
};
