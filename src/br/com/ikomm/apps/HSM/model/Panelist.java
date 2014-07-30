package br.com.ikomm.apps.HSM.model;

import br.com.ikomm.apps.HSM.database.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Panelist.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 30, 2014
 */
@DatabaseTable
public class Panelist extends Entity {

	//----------------------------------------------
	// Web Service Constants
	//----------------------------------------------
	
	public static final String SERVICE_URL = "/panelist.php";

	//----------------------------------------------
	// JSON Keys
	//----------------------------------------------
	
	public static final String KEY_ROOT = "data";
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_SLUG = "slug";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_PICTURE = "picture";
	
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
	public String picture;
	
	//----------------------------------------------
	// Constructor
	//----------------------------------------------

	public Panelist() {}
	
	public Panelist(int id, String name, String slug, String description, String picture) {
		super();
		this.id = id;
		this.name = name;
		this.slug = slug;
		this.description = description;
		this.picture = picture;
	}
	
	//----------------------------------------------
	// To String
	//----------------------------------------------
	
	@Override
	public String toString() {
		return "Panelist [id=" + id + ", name=" + name + ", slug=" + slug + ", description=" + description + ", picture=" + picture + "]";
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

	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
}