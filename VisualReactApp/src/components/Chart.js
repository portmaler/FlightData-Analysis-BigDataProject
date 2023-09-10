import React, { useState, useEffect } from "react";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Bar } from "react-chartjs-2";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

function Chart(props) {
  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: "top",
      },
      title: {
        display: true,
        text: props.title,
      },
    },
    scales: {
      y: {
        min: props.yAxisMin,
        max: props.yAxisMax,
      },
    },
  };

  const data = {
    labels: props.labels,
    datasets: [
      {
        label: "Speed (km/h)",
        data: props.data1,
        backgroundColor: props.backgroundColor1,
      },
    ],
  };

  return (
    <>
      <div style={{ width: "80%", margin: "auto" }}>
        <Bar options={options} data={data} />
      </div>
    </>
  );
}

export default Chart;
