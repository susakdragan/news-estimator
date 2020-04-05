package org.news.estimator.data;

import lombok.Builder;
import lombok.Getter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

@Getter
public class DataManager {

	private Dataset<Row> trainingData;
	private Dataset<Row> testData;

	@Builder
	public DataManager(final Dataset<Row> data, final double trainingPart, final double testPart) {
		Dataset<Row>[] randomSplit = data.randomSplit(new double[] { trainingPart, testPart });
		this.trainingData = randomSplit[0];
		this.testData = randomSplit[1];
	}



	public Dataset<Row> getTrainingData() {
		return trainingData;
	}

	public void setTrainingData(final Dataset<Row> trainingData) {
		this.trainingData = trainingData;
	}

	public Dataset<Row> getTestData() {
		return testData;
	}

	public void setTestData(final Dataset<Row> testData) {
		this.testData = testData;
	}
}
