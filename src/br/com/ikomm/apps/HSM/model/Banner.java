package br.com.ikomm.apps.HSM.model;

/**
 * Banner.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 30, 2014
 */
public class Banner {
	
	//----------------------------------------------
	// Attributes
	//----------------------------------------------
	
	public String url;
	public String banner;
	
	//----------------------------------------------
	// Constructor
	//----------------------------------------------
	
	public Banner() {}
	
	public Banner(String url, String banner) {
		super();
		this.url = url;
		this.banner = banner;
	}	
	
	//----------------------------------------------
	// To String
	//----------------------------------------------
	
	@Override
	public String toString() {
		return "Banner [url=" + url + ", banner=" + banner + "]";
	}
}