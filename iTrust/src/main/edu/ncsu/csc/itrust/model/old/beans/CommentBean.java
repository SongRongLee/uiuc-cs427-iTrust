package edu.ncsu.csc.itrust.model.old.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A bean for storing data about bulletin board comments.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class CommentBean implements Serializable, Comparable<CommentBean> {
	
	private long ID = 0;
	private long bulletinBoardID = 0;
	private String posterFirstName = "";
	private String posterLastName = "";
	private String text = "";
	private Date createdOn;
	
	public long getID() {
		return ID;
	}
	public void setID(long ID) {
		this.ID = ID;
	}
	
	public long getBulletinBoardID() {
		return bulletinBoardID;
	}
	public void setBulletinBoardID(long ID) {
		this.bulletinBoardID = ID;
	}
	
	public String getPosterFirstName() {
		return posterFirstName;
	}
	public void setPosterFirstName(String posterFirstName) {
		this.posterFirstName = posterFirstName;
	}
	
	public String getPosterLastName() {
		return posterLastName;
	}
	public void setPosterLastName(String posterLastName) {
		this.posterLastName = posterLastName;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	
	public String getCreatedOnString() {
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		return f.format(createdOn);
	}
	
	@Override
	public int compareTo(CommentBean cb) {
		return (int)(cb.ID - this.ID);
	}
}