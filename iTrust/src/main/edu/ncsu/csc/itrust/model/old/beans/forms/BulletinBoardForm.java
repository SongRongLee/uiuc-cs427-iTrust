package edu.ncsu.csc.itrust.model.old.beans.forms;

public class BulletinBoardForm{
	private String Title = "";
	private String PosterFirstName = "";
	private String PosterLastName = "";
	private String Content = "";
	
	public BulletinBoardForm(String Title, String PosterFirstName, String PosterLastName, String Content){
		this.Title = Title;
		this.PosterFirstName = PosterFirstName;
		this.PosterLastName = PosterLastName;
		this.Content = Content;
	}
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getPosterFirstName() {
		return PosterFirstName;
	}
	public void setPosterFirstName(String posterFirstName) {
		PosterFirstName = posterFirstName;
	}
	public String getPosterLastName() {
		return PosterLastName;
	}
	public void setPosterLastName(String posterLastName) {
		PosterLastName = posterLastName;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	
	
	
}