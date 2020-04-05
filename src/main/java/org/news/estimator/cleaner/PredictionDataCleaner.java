package org.news.estimator.cleaner;

import lombok.Builder;
import org.news.estimator.bean.CleanPredictionData;
import org.news.estimator.data.DataColumn;

public class PredictionDataCleaner extends AbstractDataCleaner<CleanPredictionData> {

	@Builder
	public PredictionDataCleaner(final DataColumn[] dropColumns) {
		super(dropColumns);
	}

	@Override
	protected CleanPredictionData getOutputBeanInstance() {
		return new CleanPredictionData();
	}
}
