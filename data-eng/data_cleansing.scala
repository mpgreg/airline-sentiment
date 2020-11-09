//import org.apache.spark.sql._
//spark.sql("show databases").show()
//spark.sql("use raw")
//spark.sql("show tables").show()
//spark.sql("select * from project1_lz limit 10").show()

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object Anonymize {
  //Logger.getLogger("org").setLevel(Level.WARN)
  //Logger.getLogger("akka").setLevel(Level.WARN)
  def main(args: Array[String])
    val spark = SparkSession.builder.appName("anonymize").getOrCreate()

    val input_s3_uri = args(0) 
    //val input_s3_uri = "s3://project1-lz/upload/Tweets.csv"
    val file_name = input_s3_uri.split("//*").last.split("\\.")(0)
    val output_s3_uri = args(1) 
    //val output_s3_uri = "s3://project1-lz/raw/"

    val df = spark.read.option("header", true).csv(input_s3_uri)

    //replace each tweeters name with crc bigint
    val dfAnnocrc =  df.withColumn("annonym",crc32($"name")).select("annonym", "tweet_id", "airline_sentiment","text")
    dfAnnocrc.write.mode("append").option("header", true).csv(output_s3_uri.concat(file_name).concat("-anon"))

    //spark.read.option("header", true).csv(input_s3_uri).withColumn("annonym",crc32($"name")).select("annonym", "tweet_id", "airline_sentiment","text").write.mode("append").option("header", true).csv(output_s3_uri.concat(file_name).concat("-anon"))

  }

}