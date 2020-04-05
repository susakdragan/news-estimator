package org.news.estimator.training;

import lombok.Builder;
import lombok.Getter;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.news.estimator.bean.CleanTrainingData;
import org.news.estimator.cleaner.ColumnCleaner;
import org.news.estimator.cleaner.TrainingDataCleaner;
import org.news.estimator.data.CsvDataReader;
import org.news.estimator.data.CsvSchemaCreator;
import org.news.estimator.data.DataColumn;
import org.news.estimator.data.DataFeature;
import org.news.estimator.data.DataManager;
import org.news.estimator.feature.FeatureCreator;
import org.news.estimator.label.LabelEncoder;
import org.news.estimator.predictivemodel.LogisticModelTrainer;
import org.news.estimator.predictivemodel.PredictiveModelEvaluator;
import org.news.estimator.predictivemodel.PredictiveModelWriter;

import java.io.IOException;

@Builder
@Getter
public class AggregatorTrainer {

	private String inputFileName;

	private String outputModelLocation;

	private SparkSession driver;

	public void startTraining() throws IOException {

		final CsvDataReader csvDataReader = CsvDataReader.builder()
				.sparkSession(driver)
				.fileName(inputFileName)
				.delimiter("\t")
				.schema(CsvSchemaCreator.createTrainingSchema())
				.build();

		final TrainingDataCleaner dataCleaner = TrainingDataCleaner.builder()
				.dropColumns(new DataColumn[]{ DataColumn.URL})
				.build();

		final Dataset<CleanTrainingData> cleanData = dataCleaner.cleanData(csvDataReader.readData());

		final FeatureCreator featureCreator = FeatureCreator.builder()
				.featureColumns(DataFeature.values())
				.outputColumn(DataColumn.FEATURES)
				.build();

		final Dataset<Row> featureData = featureCreator.createFeatures(cleanData);

		final LabelEncoder labelEncoder = LabelEncoder.builder()
				.inputLabelColumn(DataColumn.CATEGORY)
				.outputLabelColumn(DataColumn.LABEL)
				.build();

		final Dataset<Row> encodedData = labelEncoder.encodeLabels(featureData);

		final ColumnCleaner columnCleaner = ColumnCleaner.builder()
				.keepColumns(new DataColumn[] { DataColumn.ID, DataColumn.LABEL, DataColumn.FEATURES})
				.build();

		final Dataset<Row> preparedData = columnCleaner.cleanColumns(encodedData);

		final DataManager dataManager = DataManager.builder()
				.data(preparedData)
				.trainingPart(0.7D)
				.testPart(0.3D)
				.build();

		final LogisticModelTrainer modelTrainer = LogisticModelTrainer.builder()
				.maxIterations(100)
				.regParameter(0.001D)
				.build();

//		final NaiveBayesModelTrainer modelTrainer = NaiveBayesModelTrainer.builder()
//				.build();

//		final NaiveBayesModelTrainer modelTrainer = NaiveBayesModelTrainer.builder()
//				.smoothing(05.D)
//				.build();

		final PipelineModel predictiveModel = modelTrainer.trainModel(dataManager.getTrainingData());

		final PredictiveModelEvaluator predictiveModelEvaluator = PredictiveModelEvaluator.builder()
				.labelColumn(DataColumn.LABEL)
				.predictionColumn(DataColumn.PREDICTION)
				.build();

		final double modelAccuracy = predictiveModelEvaluator.evaluate(predictiveModel, dataManager.getTestData());

		final PredictiveModelWriter modelWriter = PredictiveModelWriter.builder()
				.outputLocation(outputModelLocation)
				.build();

		modelWriter.writePredictiveModel(predictiveModel);

		System.out.println("MODEL ACCURACY: " + modelAccuracy);
	}

}
