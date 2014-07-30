package br.com.ikomm.apps.HSM.model;

import br.com.ikomm.apps.HSM.database.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Agenda.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 30, 2014
 */
@DatabaseTable
public class Agenda extends Entity {

	//----------------------------------------------
	// Attributes
	//----------------------------------------------
	
	@DatabaseField(id = true)
	public int id;
	@DatabaseField
	public String type;
	@DatabaseField
	public int event_id;
	@DatabaseField
	public int panelist_id;
	@DatabaseField
	public int date;
	@DatabaseField
	public String date_start;
	@DatabaseField
	public String date_end;
	@DatabaseField
	public String theme_title;
	@DatabaseField
	public String theme_description;
	@DatabaseField
	public String label;
	@DatabaseField
	public String sublabel;
	@DatabaseField
	public String image;
	
	//----------------------------------------------
	// Web Service Constants
	//----------------------------------------------
	
	public static final String SERVICE_URL = "/agenda.php";

	//----------------------------------------------
	// JSON Keys
	//----------------------------------------------
	
	public static final String KEY_ROOT = "data";
	public static final String KEY_ID = "id";
	public static final String KEY_TYPE = "type";
	public static final String KEY_EVENT_ID = "event_id";
	public static final String KEY_PANELIST_ID = "panelist_id";
	public static final String KEY_DATE = "date";
	public static final String KEY_DATE_START = "date_start";
	public static final String KEY_DATE_END = "date_end";
	public static final String KEY_LABEL = "label";
	public static final String KEY_SUBLABEL = "sublabel";
	public static final String KEY_THEME_TITLE = "theme_title";
	public static final String KEY_THEME_DESCRIPTION = "theme_description";
	public static final String KEY_IMAGE = "image";
	
	//----------------------------------------------
	// Constructor
	//----------------------------------------------
	
	public Agenda() {}
	
	public Agenda(int id, String type, int event_id, int panelist_id, int date, String date_start, String date_end, String theme_title,
			String theme_description, String label, String sublabel, String image) {
		super();
		this.id = id;
		this.type = type;
		this.event_id = event_id;
		this.panelist_id = panelist_id;
		this.date = date;
		this.date_start = date_start;
		this.date_end = date_end;
		this.theme_title = theme_title;
		this.theme_description = theme_description;
		this.label = label;
		this.sublabel = sublabel;
		this.image = image;
	}
	
	//----------------------------------------------
	// To String
	//----------------------------------------------
	
	@Override
	public String toString() {
		return "Agenda [id=" + id + ", type=" + type + ", event_id=" + event_id + ", panelist_id=" + panelist_id + ", date=" + date
			+ ", date_start=" + date_start + ", date_end=" + date_end + ", theme_title=" + theme_title + ", theme_description="
			+ theme_description + ", label=" + label + ", sublabel=" + sublabel + ", image=" + image + "]";
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
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public int getEventId() {
		return event_id;
	}
	public void setEventId(int eventId) {
		this.event_id = eventId;
	}

	public int getPanelistId() {
		return panelist_id;
	}
	public void setPanelistId(int panelistId) {
		this.panelist_id = panelistId;
	}

	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}

	public String getDateStart() {
		return date_start;
	}
	public void setDateStart(String dateStart) {
		this.date_start = dateStart;
	}

	public String getDateEnd() {
		return date_end;
	}
	public void setDateEnd(String dateEnd) {
		this.date_end = dateEnd;
	}

	public String getThemeTitle() {
		return theme_title;
	}
	public void setThemeTitle(String themeTitle) {
		this.theme_title = themeTitle;
	}

	public String getThemeDescription() {
		return theme_description;
	}

	public void setThemeDescription(String themeDescription) {
		this.theme_description = themeDescription;
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public String getSublabel() {
		return sublabel;
	}
	public void setSublabel(String sublabel) {
		this.sublabel = sublabel;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}