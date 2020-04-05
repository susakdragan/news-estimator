package org.news.estimator.predictivemodel;

import lombok.Builder;
import lombok.Getter;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;

@Builder
@Getter
public class LogisticModelTrainer extends AbstractPredictiveModelTrainer {

	private int maxIterations;

	private double regParameter;

	@Override
	protected PipelineStage createPredictiveModel() {
		return new LogisticRegression().setMaxIter(maxIterations).setRegParam(regParameter);
	}
}
