package br.com.ikomm.apps.HSM.model;

public class Event {
	public int id;
	public String name;
	public String slug;
	public String description;
	public String tiny_description;
	public String info_dates;
	public String info_hours;
	public String info_locale;
	public String image_list;
	public String image_single;
	
	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", slug=" + slug
			+ ", description=" + description + ", tiny_description="
			+ tiny_description + ", info_dates=" + info_dates
			+ ", info_hours=" + info_hours + ", info_locale=" + info_locale
			+ ", image_list=" + image_list + ", image_single="
			+ image_single + "]";
	}	
}