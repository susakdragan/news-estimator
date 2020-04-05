package org.news.estimator.predictivemodel;

import lombok.Builder;
import lombok.Getter;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.NaiveBayes;

@Builder
@Getter
public class NaiveBayesModelTrainer extends AbstractPredictiveModelTrainer {

	private String modelType;

	private double smoothing;

	@Override
	protected PipelineStage createPredictiveModel() {
		return new NaiveBayes();
	}
}
