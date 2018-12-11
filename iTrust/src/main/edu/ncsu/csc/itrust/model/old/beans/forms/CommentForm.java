package edu.ncsu.csc.itrust.model.old.beans.forms;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CommentForm{
	private String bulletinBoardID;
	private String posterFirstName;
	private String posterLastName;
	private String text;
	private String createdOn;
	
	public CommentForm(String bulletinBoardID, String posterFirstName, String posterLastName, String text, String createdOn){
		this.bulletinBoardID = bulletinBoardID;
		this.posterFirstName = posterFirstName;
		this.posterLastName = posterLastName;
		this.text = text;
		this.createdOn = createdOn;
	};
	
	public void setBulletinBoardID(String bulletinBoardID) {
		this.bulletinBoardID = bulletinBoardID;
	}
	public String getBulletinBoardID() {
		return bulletinBoardID;
	}
	
	public void setPosterFirstName(String posterFirstName) {
		this.posterFirstName = posterFirstName;
	}
	public String getPosterFirstName() {
		return posterFirstName;
	}

	public void setPosterLastName(String posterLastName) {
		this.posterLastName = posterLastName;
	}
	
	public String getPosterLastName() {
		return posterLastName;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	
	public String getCreatedOn() {
		return createdOn;
	}

}