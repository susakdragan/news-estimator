package org.news.estimator.predictivemodel;

import lombok.Builder;
import lombok.Getter;
import org.apache.spark.ml.Transformer;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.news.estimator.data.DataColumn;

@Builder
@Getter
public final class PredictiveModelEvaluator {

	private DataColumn labelColumn;

	private DataColumn predictionColumn;

	public double evaluate(final Transformer transformer, final Dataset<Row> testData) {
		final Dataset<Row> resultDataset = transformer.transform(testData);
		return new MulticlassClassificationEvaluator().setLabelCol(labelColumn.getName()).setPredictionCol(predictionColumn.getName()).setMetricName("accuracy").evaluate(resultDataset);
	}

}
