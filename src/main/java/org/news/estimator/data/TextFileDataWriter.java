package org.news.estimator.data;

import lombok.Builder;
import lombok.Getter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

@Builder
@Getter
public class TextFileDataWriter {

	private String fileLocation;

	public void write(final Dataset<Row> data) {
		data.javaRDD().saveAsTextFile(fileLocation);
	}

}
