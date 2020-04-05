package org.news.estimator.predictivemodel;

import lombok.Builder;
import lombok.Getter;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.DecisionTreeClassifier;
import org.news.estimator.data.DataColumn;

@Builder
@Getter
public class DecisionTreeClassifierModelTrainer extends AbstractPredictiveModelTrainer {

	private DataColumn labelColumn;

	private DataColumn featuresColumn;
	@Override
	protected PipelineStage createPredictiveModel() {
		return new DecisionTreeClassifier().setLabelCol(labelColumn.getName()).setFeaturesCol(featuresColumn.getName());
	}
}
