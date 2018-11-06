package edu.ncsu.csc.itrust.model.old.beans.forms;

public class UltrasoundForm{
	
	private String patientID = "";
	private String created_on = "";
	
	public UltrasoundForm(String patientID, String created_on){
		this.patientID = patientID;
		this.created_on = created_on;
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
	
}