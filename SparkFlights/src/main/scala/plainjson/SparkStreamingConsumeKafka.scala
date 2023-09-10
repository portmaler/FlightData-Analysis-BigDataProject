package com.diremund.scalaapp
package plainjson

import com.diremund.scalaapp.plainjson.services.SparkStreamProcess
import com.diremund.scalaapp.plainjson.utils.Constants.brokers


object SparkStreamingConsumeKafka {
  def main(args: Array[String]): Unit = {
    new SparkStreamProcess(brokers).process()
  }
}

