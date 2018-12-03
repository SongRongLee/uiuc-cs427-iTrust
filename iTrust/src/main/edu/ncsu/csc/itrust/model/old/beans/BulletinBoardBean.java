package edu.ncsu.csc.itrust.model.old.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A bean for storing data about bulletin boards.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class BulletinBoardBean implements Serializable, Comparable<BulletinBoardBean> {
	
	private long ID = 0;
	private String title = "";
	private String posterFirstName = "";
	private String posterLastName = "";
	private String content = "";
	private Date createdOn;
	private List<CommentBean> comments;
	
	public long getID() {
		return ID;
	}
	public void setID(long ID) {
		this.ID = ID;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
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
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	
	public String getCreatedOnString() {
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
		return f.format(createdOn);
	}
	
	public void setComments(List<CommentBean> comments) {
		this.comments = comments;
	}
	
	public List<CommentBean> getComments() {
		return comments;
	}
	
	@Override
	public int compareTo(BulletinBoardBean bb) {
		return (int)(bb.ID - this.ID);
	}
}