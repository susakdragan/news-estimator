package org.news.estimator.bean;

import java.io.Serializable;

public class CleanPredictionData implements Serializable {

	private Long id;

	private String title;

	private String publisher;

	private String story;

	private String hostname;

	private Long publishDate;

	public CleanPredictionData() {
	}

	public CleanPredictionData(final Long id, final String title, final String publisher, final String story, final String hostname, final Long publishDate) {
		this.id = id;
		this.title = title;
		this.publisher = publisher;
		this.story = story;
		this.hostname = hostname;
		this.publishDate = publishDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(final String publisher) {
		this.publisher = publisher;
	}

	public String getStory() {
		return story;
	}

	public void setStory(final String story) {
		this.story = story;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(final String hostname) {
		this.hostname = hostname;
	}

	public Long getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(final Long publishDate) {
		this.publishDate = publishDate;
	}
}
