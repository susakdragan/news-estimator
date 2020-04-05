package org.news.estimator.predictivemodel;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public abstract class AbstractPredictiveModelTrainer {

	public PipelineModel trainModel(final Dataset<Row> trainingData) {
		final Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] { createPredictiveModel() });
		return pipeline.fit(trainingData);
	}

	protected abstract PipelineStage createPredictiveModel();

}
