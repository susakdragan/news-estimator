package org.news.estimator.cleaner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.news.estimator.bean.CleanPredictionData;
import org.news.estimator.data.DataColumn;

import java.io.Serializable;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public abstract class AbstractDataCleaner<O extends CleanPredictionData> implements Serializable {

	protected DataColumn[] dropColumns;

	public Dataset<O> cleanData(final Dataset<Row> file) {

		//odbacivanje url parametra i konverzija u izlazni tip
		Dataset<O> cleanDatasetDtoDataset = file.drop(Stream.of(dropColumns).map(c -> c.getName()).toArray(size -> new String[size])).as(Encoders.bean((Class<O>) getOutputBeanInstance().getClass()));

		return cleanDatasetDtoDataset;
	}

	protected abstract O getOutputBeanInstance();
}
