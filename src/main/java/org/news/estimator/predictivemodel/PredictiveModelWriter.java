package org.news.estimator.predictivemodel;

import lombok.Builder;
import lombok.Getter;
import org.apache.spark.ml.util.MLWritable;

import java.io.IOException;

@Builder
@Getter
public class PredictiveModelWriter {

	private String outputLocation;


	public void writePredictiveModel(final MLWritable model) throws IOException {
		model.write().overwrite().save(outputLocation);
	}
}
