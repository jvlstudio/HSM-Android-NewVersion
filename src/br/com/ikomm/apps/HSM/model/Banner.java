package br.com.ikomm.apps.HSM.model;

import br.com.ikomm.apps.HSM.database.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Banner.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 30, 2014
 */
@DatabaseTable
public class Banner extends Entity {
	
	//----------------------------------------------
	// Web Service Constants
	//----------------------------------------------
	
	public static final String SERVICE_URL = "/ads.php?os=android";

	//----------------------------------------------
	// JSON Keys
	//----------------------------------------------
	
	public static final String KEY_ROOT = "data";
	public static final String KEY_ID = "id";
	public static final String KEY_POS = "pos";
	public static final String KEY_TYPE = "type";
	public static final String KEY_SUBTYPE = "subtype";
	public static final String KEY_IMAGE = "image";
	public static final String KEY_IMAGE_EXP = "image_exp";
	
	//----------------------------------------------
	// Attributes
	//----------------------------------------------
	
	@DatabaseField(id = true)
	private Integer id;
	@DatabaseField	
	private Integer pos;
	@DatabaseField
	private String type;
	@DatabaseField
	private String subtype;
	@DatabaseField
	private String image;
	@DatabaseField
	private String image_exp;
	
	//----------------------------------------------
	// Constructor
	//----------------------------------------------
	
	public Banner() {}
	
	public Banner(Integer id, Integer pos, String type, String subtype, String image, String image_exp) {
		super();
		this.id = id;
		this.pos = pos;
		this.type = type;
		this.subtype = subtype;
		this.image = image;
		this.image_exp = image_exp;
	}
	
	//----------------------------------------------
	// To String
	//----------------------------------------------
	
	@Override
	public String toString() {
		return "Banner [id=" + id + ", pos=" + pos + ", type=" + type + ", subtype="
			+ subtype + ", image=" + image + ", image_exp=" + image_exp + "]";
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
	
	public Integer getPos() {
		return pos;
	}
	public void setPos(Integer pos) {
		this.pos = pos;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getImageExp() {
		return image_exp;
	}
	public void setImageExp(String imageExp) {
		this.image_exp = imageExp;
	}
}