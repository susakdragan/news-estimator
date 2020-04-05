package org.news.estimator.classification;

import lombok.Builder;
import lombok.Getter;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.news.estimator.bean.CleanPredictionData;
import org.news.estimator.cleaner.ColumnCleaner;
import org.news.estimator.cleaner.PredictionDataCleaner;
import org.news.estimator.data.CsvDataReader;
import org.news.estimator.data.CsvSchemaCreator;
import org.news.estimator.data.DataColumn;
import org.news.estimator.data.DataFeature;
import org.news.estimator.data.TextFileDataWriter;
import org.news.estimator.estimator.NewsEstimator;
import org.news.estimator.feature.FeatureCreator;
import org.news.estimator.label.LabelDecoder;
import org.news.estimator.predictivemodel.PredictiveModelReader;

@Builder
@Getter
public class AggregatorClassifier {

	private String inputFileName;

	private String outputFileName;

	private String inputModelLocation;

	private SparkSession driver;

	public void startPrediction() {

		final PredictiveModelReader modelReader = PredictiveModelReader.builder()
				.inputLocation(inputModelLocation)
				.build();

		final PipelineModel predictiveModel = modelReader.readPredictiveModel();

		final CsvDataReader csvDataReader = CsvDataReader.builder()
				.sparkSession(driver)
				.fileName(inputFileName)
				.delimiter("\t")
				.schema(CsvSchemaCreator.createPredictionSchema())
				.build();

		final PredictionDataCleaner dataCleaner = PredictionDataCleaner.builder()
				.dropColumns(new DataColumn[]{ DataColumn.URL})
				.build();

		final Dataset<CleanPredictionData> cleanData = dataCleaner.cleanData(csvDataReader.readData());

		final FeatureCreator featureCreator = FeatureCreator.builder()
				.featureColumns(DataFeature.values())
				.outputColumn(DataColumn.FEATURES)
				.build();

		final Dataset<Row> featureData = featureCreator.createFeatures(cleanData);

		final ColumnCleaner columnCleaner = ColumnCleaner.builder()
				.keepColumns(new DataColumn[]{ DataColumn.ID, DataColumn.FEATURES})
				.build();

		final Dataset<Row> preparedData = columnCleaner.cleanColumns(featureData);

		final NewsEstimator newsEstimator = NewsEstimator.builder()
				.predictiveModel(predictiveModel)
				.build();

		final Dataset<Row> predictedData = newsEstimator.estimate(preparedData);

		final LabelDecoder labelDecoder = LabelDecoder.builder()
				.inputLabelColumn(DataColumn.PREDICTION)
				.outputLabelColumn(DataColumn.PREDICTED_LABEL)
				.build();

		final Dataset<Row> resultData = labelDecoder.decodeLabels(predictedData);

		resultData.show();

		final TextFileDataWriter fileDataWriter = TextFileDataWriter.builder()
				.fileLocation(outputFileName)
				.build();

		fileDataWriter.write(resultData);

	}

}
