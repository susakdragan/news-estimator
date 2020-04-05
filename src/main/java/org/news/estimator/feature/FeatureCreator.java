package org.news.estimator.feature;

import lombok.Builder;
import lombok.Getter;
import org.apache.spark.ml.feature.FeatureHasher;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.news.estimator.bean.CleanPredictionData;
import org.news.estimator.data.DataColumn;
import org.news.estimator.data.DataFeature;

import java.util.stream.Stream;

@Builder
@Getter
public class FeatureCreator {

	private DataColumn outputColumn;

	private  DataFeature[] featureColumns;

	public <T extends CleanPredictionData> Dataset<Row> createFeatures(final Dataset<T> cleanData) {

		final Dataset<Row> featurizedData = hashFeatures(cleanData, featureColumns, outputColumn);

		return featurizedData;
	}

	private <T extends CleanPredictionData> Dataset<Row> hashFeatures(final Dataset<T> data, final DataFeature[] featureColumns, final DataColumn featureColumn) {
		final FeatureHasher featureHasher = new FeatureHasher().setInputCols(Stream.of(featureColumns).map(f -> f.getName()).toArray(size -> new String[size])).setOutputCol(featureColumn.getName());

		final Dataset<Row> featurizedDataset = featureHasher.transform(data);

		return featurizedDataset;
	}
}