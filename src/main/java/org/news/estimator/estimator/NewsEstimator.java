package org.news.estimator.estimator;

import lombok.Builder;
import lombok.Getter;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

@Builder
@Getter
public class NewsEstimator {

	private PipelineModel predictiveModel;

	public Dataset<Row> estimate(final Dataset<Row> unpredictedData) {
		return predictiveModel.transform(unpredictedData);
	}

}
