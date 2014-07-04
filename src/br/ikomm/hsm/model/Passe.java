package br.ikomm.hsm.model;

public class Passe {
	public int id;
	public int event_id;
	public String event_name;
	public String event_slug;
	public String color;
	public String name;
	public String slug;
	public String price_from;
	public String price_to;
	public String valid_to;
	public String email;
	public String description;
	public String days;
	public String dates;
	public String show_dates;
	public String is_multiple;
	
	@Override
	public String toString() {
		return "Passe [id=" + id + ", event_id=" + event_id + ", event_name="
			+ event_name + ", event_slug=" + event_slug + ", color="
			+ color + ", name=" + name + ", slug=" + slug + ", price_from="
			+ price_from + ", price_to=" + price_to + ", valid_to="
			+ valid_to + ", email=" + email + ", description="
			+ description + ", days=" + days + ", dates=" + dates
			+ ", show_dates=" + show_dates + ", is_multiple=" + is_multiple
			+ "]";
	}
}