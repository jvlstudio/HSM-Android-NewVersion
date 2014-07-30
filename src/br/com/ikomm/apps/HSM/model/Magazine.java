package br.com.ikomm.apps.HSM.model;

import br.com.ikomm.apps.HSM.database.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Magazine.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 30, 2014
 */
@DatabaseTable
public class Magazine extends Entity {

	//----------------------------------------------
	// Web Service Constants
	//----------------------------------------------
	
	public static final String SERVICE_URL = "/magazines.php";

	//----------------------------------------------
	// JSON Keys
	//----------------------------------------------
	
	public static final String KEY_ROOT = "data";
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_PICTURE = "picture";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_LINK = "link";
	
	//----------------------------------------------
	// Attributes
	//----------------------------------------------
	
	@DatabaseField(id = true)
	public int id;
	@DatabaseField
	public String name;
	@DatabaseField
	public String picture;
	@DatabaseField
	public String description;
	@DatabaseField
	public String link;
	
	//----------------------------------------------
	// Constructor
	//----------------------------------------------

	public Magazine() {}
	
	public Magazine(int id, String name, String picture, String description, String link) {
		super();
		this.id = id;
		this.name = name;
		this.picture = picture;
		this.description = description;
		this.link = link;
	}
	
	//----------------------------------------------
	// To String
	//----------------------------------------------
	
	@Override
	public String toString() {
		return "Magazine [id=" + id + ", name=" + name + ", picture=" + picture + ", description=" + description + ", link=" + link + "]";
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

	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}