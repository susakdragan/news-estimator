package org.news.estimator.cleaner;

import lombok.Builder;
import lombok.Getter;
import org.news.estimator.bean.CleanTrainingData;
import org.news.estimator.data.DataColumn;

@Getter
public class TrainingDataCleaner extends AbstractDataCleaner<CleanTrainingData> {

	@Builder
	public TrainingDataCleaner(final DataColumn[] dropColumns) {
		super(dropColumns);
	}

	@Override
	protected CleanTrainingData getOutputBeanInstance() {
		return new CleanTrainingData();
	}

}
