package org.news.estimator.data;

public enum DataFeature {

	TITLE(DataColumn.TITLE.getName()),
	PUBLISHER(DataColumn.PUBLISHER.getName()),
	STORY(DataColumn.STORY.getName()),
	PUBLISH_DATE(DataColumn.PUBLISH_DATE.getName()),
	HOSTNAME(DataColumn.HOSTNAME.getName());

	private String name;

	DataFeature(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
