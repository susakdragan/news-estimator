package org.news.estimator.data;

import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;

public enum DataColumn {
	ID("id", DataTypes.LongType),
	TITLE("title", DataTypes.StringType),
	PUBLISHER("publisher", DataTypes.StringType),
	STORY("story", DataTypes.StringType),
	PUBLISH_DATE("publishDate", DataTypes.LongType),
	CATEGORY("category", DataTypes.StringType),
	URL("url", DataTypes.StringType),
	LABEL("label", DataTypes.StringType),
	FEATURES("features", DataTypes.BinaryType),
	PREDICTION("prediction", DataTypes.DoubleType),
	PREDICTED_LABEL("predictedLabel", DataTypes.StringType),
	HOSTNAME("hostname", DataTypes.StringType);

	private String name;

	private DataType type;

	DataColumn(final String name, final DataType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public DataType getType() {
		return type;
	}
}
