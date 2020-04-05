package org.news.estimator.run;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.sql.SparkSession;
import org.news.estimator.classification.AggregatorClassifier;
import org.news.estimator.training.AggregatorTrainer;

public class NewsEstimator {

	private static final String APPLICATION_NAME = "NewsEstimator";

	private static String TRAINING_INPUT_FILE_NAME = "input/newsCorpora.csv";

	private static String MODEL_FILE_NAME = "trained-model";

	private static String PREDICTION_INPUT_FILE_NAME = "input/test_rad.csv";

	private static String PREDICTION_OUTPUT_FILE_NAME = "output";

	private static Action ACTION = Action.TRAIN;

	public static void main(String[] args) throws Exception {

		final long startTime = System.currentTimeMillis();

		readArguments(args);

		final SparkSession driver = SparkSession.builder()
				.appName(APPLICATION_NAME)
				.master("local")
//				.config("spark.driver.bindAddress", "127.0.0.1")
//				.config("spark.local.ip", "127.0.0.1")
				.getOrCreate();

		if(ACTION == Action.TRAIN) {
			final AggregatorTrainer trainer = AggregatorTrainer.builder()
					.inputFileName(TRAINING_INPUT_FILE_NAME)
					.outputModelLocation(MODEL_FILE_NAME)
					.driver(driver)
					.build();

			trainer.startTraining();
		}

		if(ACTION == Action.PREDICT) {
			final AggregatorClassifier classifier = AggregatorClassifier.builder()
					.inputFileName(PREDICTION_INPUT_FILE_NAME)
					.outputFileName(PREDICTION_OUTPUT_FILE_NAME)
					.inputModelLocation(MODEL_FILE_NAME)
					.driver(driver)
					.build();

			classifier.startPrediction();
		}

		driver.stop();

		System.out.println("TOTAL TIME: " + (System.currentTimeMillis() - startTime) / 1000);
	}


	private static void readArguments(final String[] args) {

		if (args.length == 0) {
			return;
		}

		try {
			ACTION = Action.valueOf(args[0]) == null ? ACTION : Action.valueOf(args[0]);

			TRAINING_INPUT_FILE_NAME = StringUtils.isBlank(args[1]) ? TRAINING_INPUT_FILE_NAME : args[1];
			MODEL_FILE_NAME = StringUtils.isBlank(args[2]) ? MODEL_FILE_NAME : args[2];
			PREDICTION_OUTPUT_FILE_NAME = StringUtils.isBlank(args[3]) ? PREDICTION_OUTPUT_FILE_NAME : args[2];
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
