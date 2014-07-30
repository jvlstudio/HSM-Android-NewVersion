package br.com.ikomm.apps.HSM.model;

import br.com.ikomm.apps.HSM.database.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Home.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 30, 2014
 */
@DatabaseTable
public class Home extends Entity {

	//----------------------------------------------
	// Attributes
	//----------------------------------------------
	
	@DatabaseField(id = true)
	public int id;
	@DatabaseField
	public String events_title;
	@DatabaseField
	public String events_image_android;
	@DatabaseField
	public String education_title;
	@DatabaseField
	public String education_image_android;
	@DatabaseField
	public String videos_title;
	@DatabaseField
	public String videos_image_android;
	@DatabaseField
	public String magazines_title;
	@DatabaseField
	public String magazines_image_android;
	@DatabaseField
	public String books_title;
	@DatabaseField
	public String books_image_android;
	
	//----------------------------------------------
	// Web Service Constants
	//----------------------------------------------
	
	public static final String SERVICE_URL = "/home.php";
	
	//----------------------------------------------
	// JSON Keys
	//----------------------------------------------
	
	public static final String KEY_ROOT = "data";
	public static final String KEY_ID = "id";
	public static final String KEY_EVENTS_TITLE = "events_title";
	public static final String KEY_EVENTS_IMAGE_ANDROID = "events_image_android";
	public static final String KEY_EDUCATION_TITLE = "education_title";
	public static final String KEY_EDUCATION_IMAGE_ANDROID = "education_image_android";
	public static final String KEY_VIDEOS_TITLE = "videos_title";
	public static final String KEY_VIDEOS_IMAGE_ANDROID = "videos_image_android";
	public static final String KEY_MAGAZINES_TITLE = "magazines_title";
	public static final String KEY_MAGAZINES_IMAGE_ANDROID = "magazines_image_android";
	public static final String KEY_BOOKS_TITLE = "books_title";
	public static final String KEY_BOOKS_IMAGE_ANDROID = "books_image_android";
	
	//----------------------------------------------
	// Constructor
	//----------------------------------------------
	
	public Home() {}
	
	public Home(int id, String eventsTitle, String eventsImageAndroid, String educationTitle, String educationImageAndroid,
			String videosTitle, String videosImageAndroid, String magazinesTitle, String magazinesImageAndroid,
			String booksTitle, String booksImageAndroid) {
		super();
		this.id = id;
		this.events_title = eventsTitle;
		this.events_image_android = eventsImageAndroid;
		this.education_title = educationTitle;
		this.education_image_android = educationImageAndroid;
		this.videos_title = videosTitle;
		this.videos_image_android = videosImageAndroid;
		this.magazines_title = magazinesTitle;
		this.magazines_image_android = magazinesImageAndroid;
		this.books_title = booksTitle;
		this.books_image_android = booksImageAndroid;
	}
	
	//----------------------------------------------
	// To String
	//----------------------------------------------
	
	@Override
	public String toString() {
		return "Home [id=" + id + ", events_title=" + events_title + ", events_image_android=" + events_image_android
			+ ", education_title=" + education_title + ", education_image_android=" + education_image_android
			+ ", videos_title=" + videos_title + ", videos_image_android=" + videos_image_android
			+ ", magazines_title=" + magazines_title + ", magazines_image_android=" + magazines_image_android
			+ ", books_title=" + books_title + ", books_image_android=" + books_image_android + "]";
	}
	
	//----------------------------------------------
	// Getters and Setters
	//----------------------------------------------

	@Override
	public int getId() {
		return id;
	}
	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEventsTitle() {
		return events_title;
	}
	public void setEventsTitle(String eventsTitle) {
		this.events_title = eventsTitle;
	}
	
	public String getEventsImageAndroid() {
		return events_image_android;
	}
	public void setEventsImageAndroid(String eventsImageAndroid) {
		this.events_image_android = eventsImageAndroid;
	}
	
	public String getEducationTitle() {
		return education_title;
	}
	public void setEducationTitle(String educationTitle) {
		this.education_title = educationTitle;
	}
	
	public String getEducationImageAndroid() {
		return education_image_android;
	}
	public void setEducationImageAndroid(String educationImageAndroid) {
		this.education_image_android = educationImageAndroid;
	}
	
	public String getVideosTitle() {
		return videos_title;
	}
	public void setVideosTitle(String videosTitle) {
		this.videos_title = videosTitle;
	}
	
	public String getVideosImageAndroid() {
		return videos_image_android;
	}
	public void setVideosImageAndroid(String videosImageAndroid) {
		this.videos_image_android = videosImageAndroid;
	}
	
	public String getMagazinesTitle() {
		return magazines_title;
	}
	public void setMagazinesTitle(String magazinesTitle) {
		this.magazines_title = magazinesTitle;
	}
	
	public String getMagazinesImageAndroid() {
		return magazines_image_android;
	}
	public void setMagazinesImageAndroid(String magazinesImageAndroid) {
		this.magazines_image_android = magazinesImageAndroid;
	}
	
	public String getBooksTitle() {
		return books_title;
	}
	public void setBooksTitle(String booksTitle) {
		this.books_title = booksTitle;
	}
	
	public String getBooksImageAndroid() {
		return books_image_android;
	}
	public void setBooksImageAndroid(String booksImageAndroid) {
		this.books_image_android = booksImageAndroid;
	}
}