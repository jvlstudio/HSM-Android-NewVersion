package br.ikomm.hsm.model;

public class Home {
	public int id;
	public String events_title;
	public String events_image_android;
	public String education_title;
	public String education_image_android;
	public String videos_title;
	public String videos_image_android;
	public String magazines_title;
	public String magazines_image_android;
	public String books_title;
	public String books_image_android;
	
	@Override
	public String toString() {
		return "Home [id=" + id + ", events_title=" + events_title
			+ ", events_image_android=" + events_image_android
			+ ", education_title=" + education_title
			+ ", education_image_android=" + education_image_android
			+ ", videos_title=" + videos_title + ", videos_image_android="
			+ videos_image_android + ", magazines_title=" + magazines_title
			+ ", magazines_image_android=" + magazines_image_android
			+ ", books_title=" + books_title + ", books_image_android="
			+ books_image_android + "]";
	}
}