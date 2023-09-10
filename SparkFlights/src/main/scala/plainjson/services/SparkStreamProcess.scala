package com.diremund.scalaapp
package plainjson.services

import plainjson.models.FlightCoordinates
import plainjson.utils.Constants

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{ArrayType, DataTypes, StructType}

class SparkStreamProcess(brokers: String) {

  def process(): Unit = {

    val spark = SparkSession.builder()
      .appName("SparkFlights")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val inputDf = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", brokers)
      .option("subscribe", Constants.flightsTopic)
      .load()

    val flightJsonDf = inputDf.selectExpr("CAST(value AS STRING)")

    val schema = new StructType()
      .add("hex", DataTypes.StringType)
      .add("lat", DataTypes.DoubleType)
      .add("lng", DataTypes.DoubleType)
      .add("speed", DataTypes.IntegerType) // Add the speed field to the schema
      .add("flag", DataTypes.StringType)   // Add the flag field to the schema

    val flightCoordinatesDf = flightJsonDf.select(from_json($"value", ArrayType(schema)).as("flights"))
      .selectExpr("inline(flights)")
      .as[FlightCoordinates]

    // Convert to JSON format with desired schema, including the "flag" column
    val flightCoordinatesJsonDf = flightCoordinatesDf
      .select(to_json(struct($"hex", $"lat", $"lng", $"speed", $"flag")).as("value"))

    // Write flight coordinates to the "flight-coordinate" topic
    val kafkaOutput = flightCoordinatesJsonDf.writeStream
      .format("kafka")
      .outputMode("append")
      .option("kafka.bootstrap.servers", brokers)
      .option("topic", "flight-coordinate")
      .option("checkpointLocation", "/home/diremund/Desktop/BigData/Spark/checkpoints/flight-coordinate")
      .start()

    // Find the top 10 fastest planes based on their maximum speed
    val topSpeedestDf = flightCoordinatesDf
      .groupBy($"hex")
      .agg(max($"speed").as("max_speed"))
      .orderBy(desc("max_speed"))
      .limit(10)

    // Convert the top 10 fastest planes to JSON format
    val topSpeedestJsonDf = topSpeedestDf
      .select(to_json(struct($"hex", $"max_speed")).as("value"))

    // Write the top 10 fastest planes to the "flight-speedest" topic
    val kafkaSpeedestOutput = topSpeedestJsonDf.writeStream
      .format("kafka")
      .outputMode("complete") // Output mode is complete because we want to rewrite the entire result for every trigger
      .option("kafka.bootstrap.servers", brokers)
      .option("topic", "flight-speedest")
      .option("checkpointLocation", "/home/diremund/Desktop/BigData/Spark/checkpoints/flight-speedest")
      .start()

    // Find the top 10 countries with the maximum count of flights based on the "flag" field
    val topCountriesDf = flightCoordinatesDf
      .groupBy($"flag")
      .agg(count($"hex").as("flight_count"))
      .orderBy(desc("flight_count"))
      .limit(10)

    // Convert the top 10 countries to JSON format
    val topCountriesJsonDf = topCountriesDf
      .select(to_json(struct($"flag", $"flight_count")).as("value"))

    // Write the top 10 countries to the "flight-top-country" topic
    val kafkaTopCountriesOutput = topCountriesJsonDf.writeStream
      .format("kafka")
      .outputMode("complete") // Output mode is complete because we want to rewrite the entire result for every trigger
      .option("kafka.bootstrap.servers", brokers)
      .option("topic", "flight-top-country")
      .option("checkpointLocation", "/home/diremund/Desktop/BigData/Spark/checkpoints/flight-top-country")
      .start()

    // Await termination of all streaming queries
    kafkaOutput.awaitTermination()
    kafkaSpeedestOutput.awaitTermination()
    kafkaTopCountriesOutput.awaitTermination()
  }
}
