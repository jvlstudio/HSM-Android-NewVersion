package br.com.ikomm.apps.HSM.model;

public class Agenda {
	public int id;
	public String type;
	public int event_id;
	public int panelist_id;
	public int date;
	public String date_start;
	public String date_end;
	public String theme_title;
	public String theme_description;
	public String label;
	public String sublabel;
	public String image;
	
	@Override
	public String toString() {
		return "Agenda [id=" + id + ", type=" + type + ", event_id=" + event_id
			+ ", panelist_id=" + panelist_id + ", date=" + date
			+ ", date_start=" + date_start + ", date_end=" + date_end
			+ ", theme_title=" + theme_title + ", theme_description="
			+ theme_description + ", label=" + label + ", sublabel="
			+ sublabel + ", image=" + image + "]";
	}
}