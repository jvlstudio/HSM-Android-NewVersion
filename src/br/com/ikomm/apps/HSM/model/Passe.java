package br.com.ikomm.apps.HSM.model;

import br.com.ikomm.apps.HSM.database.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Passe.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 30, 2014
 */
@DatabaseTable
public class Passe extends Entity {

	//----------------------------------------------
	// Web Service Constants
	//----------------------------------------------
	
	public static final String SERVICE_URL = "/passes.php";

	//----------------------------------------------
	// JSON Keys
	//----------------------------------------------
	
	public static final String KEY_ROOT = "data";
	public static final String KEY_ID = "id";
	public static final String KEY_EVENT_ID = "event_id";
	public static final String KEY_COLOR = "color";
	public static final String KEY_NAME = "name";
	public static final String KEY_SLUG = "slug";
	public static final String KEY_PRICE_FROM = "price_from";
	public static final String KEY_PRICE_TO = "price_to";
	public static final String KEY_VALID_TO = "valid_to";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_DAYS = "days";
	public static final String KEY_SHOW_DATES = "show_dates";
	public static final String KEY_IS_MULTIPLE = "is_multiple";
	
	//----------------------------------------------
	// Attributes
	//----------------------------------------------
	
	@DatabaseField(id = true)
	public int id;
	@DatabaseField
	public int event_id;
	@DatabaseField
	public String color;
	@DatabaseField
	public String name;
	@DatabaseField
	public String slug;
	@DatabaseField
	public String price_from;
	@DatabaseField
	public String price_to;
	@DatabaseField
	public String valid_to;
	@DatabaseField
	public String email;
	@DatabaseField
	public String description;
	@DatabaseField
	public String days;
	@DatabaseField
	public String show_dates;
	@DatabaseField
	public String is_multiple;
	
	//----------------------------------------------
	// Constructor
	//----------------------------------------------

	public Passe() {}
	
	public Passe(int id, int event_id, String color, String name, String slug, String price_from, String price_to, String valid_to, String email,
			String description, String days, String show_dates, String is_multiple) {
		super();
		this.id = id;
		this.event_id = event_id;
		this.color = color;
		this.name = name;
		this.slug = slug;
		this.price_from = price_from;
		this.price_to = price_to;
		this.valid_to = valid_to;
		this.email = email;
		this.description = description;
		this.days = days;
		this.show_dates = show_dates;
		this.is_multiple = is_multiple;
	}
	
	//----------------------------------------------
	// To String
	//----------------------------------------------
	
	@Override
	public String toString() {
		return "Passe [id=" + id + ", event_id=" + event_id + ", color=" + color + ", name=" + name + ", slug=" + slug + ", price_from="
			+ price_from + ", price_to=" + price_to + ", valid_to=" + valid_to + ", email=" + email + ", description="
			+ description + ", days=" + days + ", show_dates=" + show_dates + ", is_multiple=" + is_multiple + "]";
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

	public int getEventId() {
		return event_id;
	}
	public void setEventId(int eventId) {
		this.event_id = eventId;
	}

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
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

	public String getPriceFrom() {
		return price_from;
	}
	public void setPriceFrom(String priceFrom) {
		this.price_from = priceFrom;
	}

	public String getPriceTo() {
		return price_to;
	}
	public void setPriceTo(String priceTo) {
		this.price_to = priceTo;
	}

	public String getValidTo() {
		return valid_to;
	}
	public void setValidTo(String validTo) {
		this.valid_to = validTo;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}

	public String getShowDates() {
		return show_dates;
	}
	public void setShowDates(String showDates) {
		this.show_dates = showDates;
	}

	public String getIsMultiple() {
		return is_multiple;
	}
	public void setIsMultiple(String isMultiple) {
		this.is_multiple = isMultiple;
	}
}