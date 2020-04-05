package org.news.estimator.bean;

public class CleanTrainingData extends CleanPredictionData {

	private String category;

	public CleanTrainingData() {
	}

	public CleanTrainingData(final Long id, final String title, final String publisher, final String category, final String story, final String hostname, final Long publishDate) {
		super(id, title, publisher, story, hostname, publishDate);
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}
}
