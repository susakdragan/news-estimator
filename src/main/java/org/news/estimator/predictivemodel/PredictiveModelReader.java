package org.news.estimator.predictivemodel;

import lombok.Builder;
import lombok.Getter;
import org.apache.spark.ml.PipelineModel;

@Builder
@Getter
public class PredictiveModelReader {

	private String inputLocation;

	public PipelineModel readPredictiveModel() {
		return PipelineModel.load(inputLocation);
	}

}
