package org.news.estimator.data;

public enum Category {

	B("Business"), M("Health"), T("Science and Technology"), E("Entertainment"), U("UNKNOW");

	private String title;

	Category(final String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return name() + " (" + title + ")";
	}
}
