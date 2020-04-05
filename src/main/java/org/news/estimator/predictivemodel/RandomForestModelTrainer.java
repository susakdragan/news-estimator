package org.news.estimator.predictivemodel;

import lombok.Builder;
import lombok.Getter;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.RandomForestClassifier;
import org.news.estimator.data.DataColumn;

@Builder
@Getter
public class RandomForestModelTrainer extends AbstractPredictiveModelTrainer {

	private DataColumn labelColumn;

	private DataColumn featuresColumn;

	private int numberOfTrees;

	@Override
	protected PipelineStage createPredictiveModel() {
		return new RandomForestClassifier().setLabelCol(labelColumn.getName()).setFeaturesCol(featuresColumn.getName()).setNumTrees(numberOfTrees);
	}
}
