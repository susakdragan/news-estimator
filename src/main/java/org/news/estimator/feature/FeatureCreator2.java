package org.news.estimator.feature;

import com.google.common.collect.Sets;
import lombok.Builder;
import lombok.Getter;
import org.apache.spark.ml.feature.FeatureHasher;
import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.ml.feature.Tokenizer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.news.estimator.bean.CleanPredictionData;
import org.news.estimator.data.DataColumn;
import org.news.estimator.data.DataFeature;

import java.util.Set;

@Builder
@Getter
public class FeatureCreator2 {

	private static final String TITLE_FEATURES_COLUMN = "titleFeatures";
	private static final String OTHER_FEATURES_COLUMN = "otherFeatures";
	private static final String TOKENIZED_COLUMN = "words";

	private DataColumn outputColumn;

	private  DataFeature[] featureColumns;

	private DataFeature tokenizeColumn;

	public <T extends CleanPredictionData> Dataset<Row> createFeatures(final Dataset<T> cleanData) {

		Dataset<Row> hashedDataset = tokenizeAndHashFeature(cleanData, tokenizeColumn, TOKENIZED_COLUMN, TITLE_FEATURES_COLUMN);

		final Dataset<Row> featurizedDataset = hashFeatures(hashedDataset, Sets.difference(Sets.newHashSet(featureColumns), Sets.newHashSet(tokenizeColumn)), OTHER_FEATURES_COLUMN);

		return new VectorAssembler().setInputCols(new String[] { TITLE_FEATURES_COLUMN, OTHER_FEATURES_COLUMN }).setOutputCol(outputColumn.getName()).transform(featurizedDataset)
				.drop(TITLE_FEATURES_COLUMN, OTHER_FEATURES_COLUMN);

	}

	private <T extends CleanPredictionData> Dataset<Row> tokenizeAndHashFeature(final Dataset<T> data, final DataFeature tokenizeColumn, final String tokenizedColumn,
			final String featureColumn) {
		Tokenizer tokenizer = new Tokenizer().setInputCol(tokenizeColumn.getName()).setOutputCol(tokenizedColumn);

		final Dataset<Row> tokenizedDataset = tokenizer.transform(data);

		HashingTF hashingTF = new HashingTF().setNumFeatures(100).setInputCol(tokenizer.getOutputCol()).setOutputCol(featureColumn);

		final Dataset<Row> hashedDataset = hashingTF.transform(tokenizedDataset);
		return hashedDataset;
	}

	private Dataset<Row> hashFeatures(final Dataset<Row> data, final Set<DataFeature> featureColumns, final String featureColumn) {
		final FeatureHasher featureHasher = new FeatureHasher().setInputCols(featureColumns.stream().map(f -> f.getName()).toArray(size -> new String[size])).setOutputCol(featureColumn);

		final Dataset<Row> featurizedDataset = featureHasher.transform(data);

		return featurizedDataset;
	}
}