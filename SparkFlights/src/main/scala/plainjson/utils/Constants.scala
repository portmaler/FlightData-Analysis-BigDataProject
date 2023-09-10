package com.diremund.scalaapp
package plainjson.utils

import java.time.format.DateTimeFormatter

object Constants {
  val flightsTopic = "flights"
  val personsAvroTopic = "persons-avro"
  val agesTopic = "ages"
  val brokers = "localhost:9092"

  val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
}