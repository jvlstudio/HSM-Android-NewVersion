package br.ikomm.hsm.model;

public class Panelist {
	public int id;
	public String name;
	public String slug;
	public String description;
	public String picture;
	
	@Override
	public String toString() {
		return "Panelist [id=" + id + ", name=" + name + ", slug=" + slug
			+ ", description=" + description + ", picture=" + picture + "]";
	}	
}