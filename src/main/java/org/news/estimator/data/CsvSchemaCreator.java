package org.news.estimator.data;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class CsvSchemaCreator {

	public static StructType createTrainingSchema(){
		final StructType schema = DataTypes.createStructType(new StructField[]{
				DataTypes.createStructField(DataColumn.ID.getName(), DataColumn.ID.getType(),false),
				DataTypes.createStructField(DataColumn.TITLE.getName(), DataColumn.TITLE.getType(),false),
				DataTypes.createStructField(DataColumn.URL.getName(), DataColumn.URL.getType(),false),
				DataTypes.createStructField(DataColumn.PUBLISHER.getName(), DataColumn.PUBLISHER.getType(),false),
				DataTypes.createStructField(DataColumn.CATEGORY.getName(), DataColumn.CATEGORY.getType(),false),
				DataTypes.createStructField(DataColumn.STORY.getName(), DataColumn.STORY.getType(),false),
				DataTypes.createStructField(DataColumn.HOSTNAME.getName(), DataColumn.HOSTNAME.getType(),false),
				DataTypes.createStructField(DataColumn.PUBLISH_DATE.getName(), DataColumn.PUBLISH_DATE.getType(),false),
		});

		return schema;
	}

	public static StructType createPredictionSchema(){
		final StructType schema = DataTypes.createStructType(new StructField[]{
				DataTypes.createStructField(DataColumn.ID.getName(), DataColumn.ID.getType(),false),
				DataTypes.createStructField(DataColumn.TITLE.getName(), DataColumn.TITLE.getType(),false),
				DataTypes.createStructField(DataColumn.URL.getName(), DataColumn.URL.getType(),false),
				DataTypes.createStructField(DataColumn.PUBLISHER.getName(), DataColumn.PUBLISHER.getType(),false),
				DataTypes.createStructField(DataColumn.STORY.getName(), DataColumn.STORY.getType(),false),
				DataTypes.createStructField(DataColumn.HOSTNAME.getName(), DataColumn.HOSTNAME.getType(),false),
				DataTypes.createStructField(DataColumn.PUBLISH_DATE.getName(), DataColumn.PUBLISH_DATE.getType(),false),
		});

		return schema;
	}

}
