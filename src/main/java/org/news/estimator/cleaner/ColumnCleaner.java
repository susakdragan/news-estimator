package org.news.estimator.cleaner;

import lombok.Builder;
import lombok.Getter;
import org.apache.spark.ml.feature.ColumnPruner;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.news.estimator.data.DataColumn;
import scala.collection.JavaConverters;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class ColumnCleaner {

	private Set<String> keepColumns;

	@Builder
	public ColumnCleaner(final DataColumn[] keepColumns) {
		this.keepColumns = Stream.of(keepColumns).map(c ->c.getName()).collect(Collectors.toSet());;
	}

	public Dataset<Row> cleanColumns(final Dataset<Row> data) {
		final Set<String> columnsToRemove = Stream.of(data.columns()).filter(column -> !keepColumns.contains(column)).collect(Collectors.toSet());
		final scala.collection.immutable.Set<String> stringSet = JavaConverters.asScalaSetConverter(columnsToRemove).asScala().toSet();
		return new ColumnPruner(stringSet).transform(data);
	}
}
