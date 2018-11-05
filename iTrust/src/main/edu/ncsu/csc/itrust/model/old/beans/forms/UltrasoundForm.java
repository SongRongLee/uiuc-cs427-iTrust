package edu.ncsu.csc.itrust.model.old.beans.forms;

public class UltrasoundForm{
	
	private String patientID = "";
	private String created_on = "";
	private String image = "";
	
	public UltrasoundForm(String patientID, String created_on, String image){
		this.patientID = patientID;
		this.created_on = created_on;
		this.image = image;
	};
	
	public String getPatientID(){
		return patientID;
	}
	public void setPatientID(String patientID){
		this.patientID = patientID;
	}
	
	public String getCreated_on(){
		return created_on;
	}
	public void setCreated_on(String created_on){
		this.created_on = created_on;
	}
	
	public String getImage(){
		return image;
	}
	public void setImage(String image){
		this.image = image;
	}
}