package edu.ncsu.csc.itrust.model.old.beans.forms;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DeliveryRecordForm{
	private String patientID;
	private String childbirthVisitID;
	private String deliveryDateTime;
	private String deliveryMethod;
	
	public DeliveryRecordForm(String patientID, String childbirthVisitID, String deliveryDateTime, String deliveryMethod){
		this.patientID = patientID;
		this.childbirthVisitID = childbirthVisitID;
		this.deliveryDateTime = deliveryDateTime;
		this.deliveryMethod = deliveryMethod;
	};
	
	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}
	public String getPatientID() {
		return patientID;
	}
	
	public void setChildbirthVisitID(String childbirthVisitID) {
		this.childbirthVisitID = childbirthVisitID;
	}
	public String getChildbirthVisitID() {
		return childbirthVisitID;
	}

	public void setDeliveryDateTime(String deliveryDateTime) {
		this.deliveryDateTime = deliveryDateTime;
	}
	
	public String getDeliveryDateTime() {
		return deliveryDateTime;
	}
	
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	
}