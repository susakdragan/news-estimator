package org.news.estimator.label;

import lombok.Builder;
import lombok.Getter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.news.estimator.data.Category;
import org.news.estimator.data.DataColumn;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.when;

@Builder
@Getter
public class LabelEncoder {

	private DataColumn inputLabelColumn;

	private DataColumn outputLabelColumn;

	public Dataset<Row> encodeLabels(final Dataset<Row> data){
		return data.withColumn(outputLabelColumn.getName(),
				 when(col(inputLabelColumn.getName()).equalTo(Category.B.name().toLowerCase()),1D)
				.when(col(inputLabelColumn.getName()).equalTo(Category.T.name().toLowerCase()),2D)
			    .when(col(inputLabelColumn.getName()).equalTo(Category.E.name().toLowerCase()),3D)
			    .when(col(inputLabelColumn.getName()).equalTo(Category.M.name().toLowerCase()),4D)
				.otherwise(0D));
	}
}
