package br.ikomm.hsm.model;

public class Book {
	public int id;
	public String name;
	public String picture;
	public String description;
	public String author_name;
	public String author_description;
	public String link;
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", picture=" + picture
				+ ", description=" + description + ", author_name="
				+ author_name + ", author_description=" + author_description
				+ ", link=" + link + "]";
	}	
}