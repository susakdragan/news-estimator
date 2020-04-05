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
public class LabelDecoder {

	private DataColumn inputLabelColumn;

	private DataColumn outputLabelColumn;

	public Dataset<Row> decodeLabels(final Dataset<Row> data){
		return data.withColumn(outputLabelColumn.getName(),
				 when(col(inputLabelColumn.getName()).equalTo(1D), Category.B.toString())
				.when(col(inputLabelColumn.getName()).equalTo(2D), Category.T.toString())
			    .when(col(inputLabelColumn.getName()).equalTo(3D), Category.E.toString())
			    .when(col(inputLabelColumn.getName()).equalTo(4D), Category.M.toString())
				.otherwise(Category.U.toString()));
	}
}
