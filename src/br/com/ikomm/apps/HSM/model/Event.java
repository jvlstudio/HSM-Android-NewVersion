package br.com.ikomm.apps.HSM.model;

import br.com.ikomm.apps.HSM.database.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Event.data class.
 * 
 * @author Rodrigo Cericatto
 * @since July 30, 2014
 */
@DatabaseTable
public class Event extends Entity {

	//----------------------------------------------
	// Attributes
	//----------------------------------------------
	
	@DatabaseField(id = true)
	public int id;
	@DatabaseField
	public String name;
	@DatabaseField
	public String slug;
	@DatabaseField
	public String description;
	@DatabaseField
	public String tiny_description;
	@DatabaseField
	public String info_dates;
	@DatabaseField
	public String info_hours;
	@DatabaseField
	public String info_locale;
	@DatabaseField
	public String image_list;
	@DatabaseField
	public String image_single;
	
	//----------------------------------------------
	// Web Service Constants
	//----------------------------------------------
	
	public static final String SERVICE_URL = "/events.php";
	
	//----------------------------------------------
	// JSON Keys
	//----------------------------------------------

	public static final String KEY_ROOT = "data";
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_SLUG = "slug";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_TINY_DESCRIPTION = "tiny_description";
	public static final String KEY_INFO_DATES = "info_dates";
	public static final String KEY_INFO_HOURS = "info_hours";
	public static final String KEY_INFO_LOCALE = "info_locale";
	public static final String KEY_IMAGE_LIST = "image_list";
	public static final String KEY_IMAGE_SINGLE = "image_single";
	
	//----------------------------------------------
	// Constructor
	//----------------------------------------------
	
	public Event() {}
	
	public Event(int id, String name, String slug, String description, String tinyDescription, String infoDates, String infoHours,
			String infoLocale, String imageList, String imageSingle) {
		super();
		this.id = id;
		this.name = name;
		this.slug = slug;
		this.description = description;
		this.tiny_description = tinyDescription;
		this.info_dates = infoDates;
		this.info_hours = infoHours;
		this.info_locale = infoLocale;
		this.image_list = imageList;
		this.image_single = imageSingle;
	}
	
	//----------------------------------------------
	// To String
	//----------------------------------------------
	
	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", slug=" + slug + ", description=" + description + ", tiny_description="
			+ tiny_description + ", info_dates=" + info_dates + ", info_hours=" + info_hours + ", info_locale=" + info_locale
			+ ", image_list=" + image_list + ", image_single=" + image_single + "]";
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
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTinyDescription() {
		return tiny_description;
	}
	public void setTinyDescription(String tinyDescription) {
		this.tiny_description = tinyDescription;
	}
	
	public String getInfoDates() {
		return info_dates;
	}
	public void setInfoDates(String infoDates) {
		this.info_dates = infoDates;
	}
	
	public String getInfoHours() {
		return info_hours;
	}
	public void setInfoHours(String infoHours) {
		this.info_hours = infoHours;
	}
	
	public String getInfoLocale() {
		return info_locale;
	}
	public void setInfoLocale(String infoLocale) {
		this.info_locale = infoLocale;
	}
	
	public String getImageList() {
		return image_list;
	}
	public void setImageList(String imageList) {
		this.image_list = imageList;
	}
	
	public String getImageSingle() {
		return image_single;
	}
	public void setImageSingle(String imageSingle) {
		this.image_single = imageSingle;
	}
}