package br.ikomm.hsm.model;

public class Palestra {
	public String id;
	public String author;
	public String content;
	public String day;
	public String gmtc;
	public String hour_final;
	public String hour_init;
	public String minutes;
	public String slug;
	public String subtitle;
	public String title;
	public String type;
	
	@Override
	public String toString() {
		return "Palestra [id=" + id + ", author=" + author + ", content="
			+ content + ", day=" + day + ", gmtc=" + gmtc + ", hour_final="
			+ hour_final + ", hour_init=" + hour_init + ", minutes="
			+ minutes + ", slug=" + slug + ", subtitle=" + subtitle
			+ ", title=" + title + ", type=" + type + "]";
	}	
}