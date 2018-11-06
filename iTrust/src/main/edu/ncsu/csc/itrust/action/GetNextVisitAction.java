package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import java.sql.Timestamp;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * 
 * Calculate the next Visit date and return
 *
 */
public class GetNextVisitAction {
	/**
	 * 
	 * Approximately calculate next visit date
	 */
	public String GetNextDateString(ObstetricsVisitBean v){
		int numWeeks = Integer.parseInt(v.getNumWeeks().split("-",0)[0]);
		Timestamp oldTs = v.getScheduledDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(oldTs);
		
		if(numWeeks<13){
			cal.add(Calendar.DATE, 28);
		}else if(numWeeks<28){
			cal.add(Calendar.DATE, 14);
		}else if(numWeeks<40){
			cal.add(Calendar.DATE, 7);
		}else if(numWeeks<42){
			cal.add(Calendar.DATE, 2);
		}else if(numWeeks==42){
			
		}
		
		CheckAndUpdate(cal);
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		return f.format(cal.getTime().getTime());
	}
	
	
	/**
	 * 
	 * Check for Holidays or weekends
	 */
	public void CheckAndUpdate(Calendar cal){
		while(!isBusinessDay(cal)){
			cal.add(Calendar.DATE, 1);
		}
	}
	
	public boolean isBusinessDay(Calendar cal){
		// check if weekend
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
			return false;
		}
		
		// check if New Year's Day
		if (cal.get(Calendar.MONTH) == Calendar.JANUARY
			&& cal.get(Calendar.DAY_OF_MONTH) == 1) {
			return false;
		}
		
		// check if Christmas eve
		if (cal.get(Calendar.MONTH) == Calendar.DECEMBER
			&& cal.get(Calendar.DAY_OF_MONTH) == 24) {
			return false;
		}
		
		// check if Christmas
		if (cal.get(Calendar.MONTH) == Calendar.DECEMBER
			&& cal.get(Calendar.DAY_OF_MONTH) == 25) {
			return false;
		}
		
		// check if 4th of July
		if (cal.get(Calendar.MONTH) == Calendar.JULY
			&& cal.get(Calendar.DAY_OF_MONTH) == 4) {
			return false;
		}
		
		// check Thanksgiving (4th Thursday of November)
		if (cal.get(Calendar.MONTH) == Calendar.NOVEMBER
			&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 4
			&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			return false;
		}
		
		// check Memorial Day (last Monday of May)
		if (cal.get(Calendar.MONTH) == Calendar.MAY
			&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
			&& cal.get(Calendar.DAY_OF_MONTH) > (31 - 7) ) {
			return false;
		}
		
		// check Labor Day (1st Monday of September)
		if (cal.get(Calendar.MONTH) == Calendar.SEPTEMBER
			&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 1
			&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			return false;
		}
		
		// check Columbus Day (2nd Monday of October)
		if (cal.get(Calendar.MONTH) == Calendar.OCTOBER
			&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 2
			&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			return false;
		}
		
		// check President's Day (3rd Monday of February)
		if (cal.get(Calendar.MONTH) == Calendar.FEBRUARY
		&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 3
		&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
		return false;
		}
		
		
		// check MLK Day (3rd Monday of January)
		if (cal.get(Calendar.MONTH) == Calendar.JANUARY
		&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 3
		&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
		return false;
		}
		
		// IF NOTHING ELSE, IT'S A BUSINESS DAY
		return true;
	}
}