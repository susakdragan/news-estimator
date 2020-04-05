package org.news.estimator.data;

import lombok.Builder;
import lombok.Getter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

@Builder
@Getter
public class CsvDataReader {

	private SparkSession sparkSession;

	private String fileName;

	private StructType schema;

	private String delimiter;


	public Dataset<Row> readData() {
		// Read the CSV file from the disk
		Dataset<Row> file = sparkSession.read().option("mode", "DROPMALFORMED")    // And drop all the marlformed rows
				.option("delimiter", delimiter).schema(schema).csv(fileName);// Specify the file path

		return file;

	}

}
