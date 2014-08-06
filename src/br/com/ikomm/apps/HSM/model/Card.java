package br.com.ikomm.apps.HSM.model;

/**
 * Banner.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 30, 2014
 */
public class Card {
	
	//----------------------------------------------
	// Attributes
	//----------------------------------------------
	
	public String name;
	public String email;
	public String phone;
	public String mobilePhone;
	public String company;
	public String role;
	public String website;
	
	//----------------------------------------------
	// Constructor
	//----------------------------------------------
	
	public Card() {}
	
	public Card(String name, String email, String phone, String mobilePhone, String company, String role, String website) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.mobilePhone = mobilePhone;
		this.company = company;
		this.role = role;
		this.website = website;
	}
	
	//----------------------------------------------
	// To String
	//----------------------------------------------
	
	@Override
	public String toString() {
		return "Card [name=" + name + ", email=" + email + ", phone=" + phone + ", mobilePhone="
			+ mobilePhone + ", company=" + company + ", role=" + role + ", website=" + website + "]";
	}
	
	//----------------------------------------------
	// Getters and Setters
	//----------------------------------------------

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
}