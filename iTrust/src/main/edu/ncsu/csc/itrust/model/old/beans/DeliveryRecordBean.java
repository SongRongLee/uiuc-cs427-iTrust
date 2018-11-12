package edu.ncsu.csc.itrust.model.old.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A bean for storing data about delivery records.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class DeliveryRecordBean implements Serializable, Comparable<DeliveryRecordBean> {
	
	private long ID = 0;
	private long patientID = 0;
	private long childbirthVisitID = 0;
	private Date deliveryDateTime;
	private String deliveryMethod = "";
	
	public void setID(long ID) {
		this.ID = ID;
	}
	public long getID() {
		return ID;
	}
	
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}
	public long getPatientID() {
		return patientID;
	}
	
	public void setChildbirthVisitID(long childbirthVisitID) {
		this.childbirthVisitID = childbirthVisitID;
	}
	public long getChildbirthVisitID() {
		return childbirthVisitID;
	}
	
	public void setDeliveryDateTime(Date deliveryDateTime) {
		this.deliveryDateTime = deliveryDateTime;
	}
	
	public Date getDeliveryDateTime() {
		return deliveryDateTime;
	}
	
	public String getDeliveryDateTimeString() {
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		return f.format(deliveryDateTime);
	}
	
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	
	@Override
	public int compareTo(DeliveryRecordBean d) {
		return (int)(d.ID-this.ID);
	}
	
}
