package edu.ncsu.csc.itrust.model.old.beans.forms;

public class PregnancyForm{
	private String PatientID = "";
	private String Date_delivery = "";
	private String num_weeks_pregnant = "";
	private String num_hours_labor = "";
	private String delivery_type = "";
	private String YOC = "";
		
	public PregnancyForm(String PatientID, String Date_delivery,String num_weeks_pregnant,
			String num_hours_labor, String delivery_type, String YOC){
		this.PatientID = PatientID;
		this.Date_delivery = Date_delivery;
		this.num_weeks_pregnant = num_weeks_pregnant;
		this.num_hours_labor = num_hours_labor;
		this.delivery_type = delivery_type;
		this.YOC = YOC;
	};
	
	public String getPatientID(){
		return PatientID;
	}
	
	public void setPatientID(String PatientID){
		this.PatientID = PatientID;
	}
	public String getDate_delivery(){
		return Date_delivery;
	}
	
	public void setDate_delivery(String Date_delivery){
		this.Date_delivery = Date_delivery;
	}

	public String getNum_weeks_pregnant(){
		return num_weeks_pregnant;
	}
	
	public void setNum_weeks_pregnant(String num_weeks_pregnant){
		this.num_weeks_pregnant = num_weeks_pregnant;
	}
	public String getNum_hours_labor(){
		return this.num_hours_labor;
	}
	public void setNum_hours_labor(String num_hours_labor){
		this.num_hours_labor = num_hours_labor;
	}
	public String getDelivery_type(){
		return this.delivery_type;
	}
	public void setDelivery_type(String delivery_type){
		this.delivery_type = delivery_type;
	}
	public String getYOC(){
		return this.YOC;
	}
	public void setYOC(String YOC){
		this.YOC = YOC;
	}
}