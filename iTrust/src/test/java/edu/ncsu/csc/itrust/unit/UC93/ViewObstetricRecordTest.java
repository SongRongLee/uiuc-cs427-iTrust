package edu.ncsu.csc.itrust.unit.UC93;


import org.junit.Test;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

public class ViewObstetricRecordTest extends HelperTest{

	@Test
	public void Sampletest() throws Exception{
		WebConversation wc = login("9000000003","pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Patient Information").click();
		assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		wr.getForms()[1].setParameter("FIRST_NAME", "Random");
		wr.getForms()[1].setParameter("LAST_NAME", "Person");
		wr.getForms()[1].getButtons()[0].click();
		wr = wc.getCurrentPage();
		
		WebTable wt = wr.getTableStartingWith("MID");
		assertEquals("MID", wt.getCellAsText(0, 0));
		assertEquals("", wt.getCellAsText(1, 0));
		assertEquals("Random", wt.getCellAsText(1, 1));
		assertEquals("Person", wt.getCellAsText(1, 2));
		wr.getForms()[2].getButtons()[0].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Edit Patient", wr.getTitle());

	}
	
	@Test
	public void ViewObstetricsRecordsTest() throws Exception{
		WebConversation wc = login("9000000003","pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Obstetrics Information").click();
		assertEquals("iTrust - Please Select an Obstetrics Patient", wr.getTitle());
		wr.getForms()[1].setParameter("FIRST_NAME", "Random");
		wr.getForms()[1].setParameter("LAST_NAME", "Person");
		wr.getForms()[1].getButtons()[0].click();
		wr = wc.getCurrentPage();
		
		WebTable wt = wr.getTableStartingWith("MID");
		assertEquals("MID", wt.getCellAsText(0, 0));
		assertEquals("", wt.getCellAsText(1, 0));
		assertEquals("Random", wt.getCellAsText(1, 1));
		assertEquals("Person", wt.getCellAsText(1, 2));
		wr.getForms()[2].getButtons()[0].click();
		wr = wc.getCurrentPage();
		
		assertEquals("iTrust - Obstetrics Records", wr.getTitle());
		WebTable ObsTable = wr.getTableStartingWith("Date");
		//Check the fields of the table
		assertEquals("Date", ObsTable.getCellAsText(0, 0));
		assertEquals("LMP", ObsTable.getCellAsText(0, 1));
		assertEquals("EDD", ObsTable.getCellAsText(0, 2));
		assertEquals("Number of Weeks Pregnant", ObsTable.getCellAsText(0, 3));
		
		//Check the datas of the table
		/*
		assertEquals("", ObsTable.getCellAsText(1, 0));
		assertEquals("", ObsTable.getCellAsText(1, 1));
		assertEquals("", ObsTable.getCellAsText(1, 2));
		assertEquals("", ObsTable.getCellAsText(1, 3));
		*/
		
		/*
		//click on a single record
		wr.getForms()[0].getButtons()[0].click();
		wr = wc.getCurrentPage();
		
		assertEquals("iTrust - Obstetrics Record", wr.getTitle());
		WebTable ObTable = wr.getTables()[0];
		//Check the datas of the table
		assertEquals("", ObTable.getCellAsText(0, 0));
		assertEquals("", ObTable.getCellAsText(0, 1));
		assertEquals("", ObTable.getCellAsText(0, 2));
		assertEquals("", ObTable.getCellAsText(0, 3));
		*/
	}
	
	@Test
	public void EditObstetricsRecordsTest() throws Exception{
		WebConversation wc = login("9000000012","pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Obstetrics Information").click();
		assertEquals("iTrust - Please Select an Obstetrics Patient", wr.getTitle());
		wr.getForms()[1].setParameter("FIRST_NAME", "Random");
		wr.getForms()[1].setParameter("LAST_NAME", "Person");
		wr.getForms()[1].getButtons()[0].click();
		wr = wc.getCurrentPage();
		
		wr.getForms()[2].getButtons()[0].click();
		wr = wc.getCurrentPage();
		
		assertEquals("iTrust - Obstetrics Records", wr.getTitle());
		//Click on Edit button
		wr.getForms()[0].getButtons()[0].click();//put the button in the form!
		wr = wc.getCurrentPage();
		
		assertEquals("iTrust - Edit Obstetrics Record", wr.getTitle());
		WebTable ObsTable = wr.getTableStartingWith("Date");
		//Check the fields of the table
		assertEquals("Date", ObsTable.getCellAsText(0, 0));
		assertEquals("LMP", ObsTable.getCellAsText(0, 1));
		assertEquals("EDD", ObsTable.getCellAsText(0, 2));
		assertEquals("Number of Weeks Pregnant", ObsTable.getCellAsText(0, 3));
		assertEquals("10/27/2018", ObsTable.getCellAsText(1, 0));
	}


}
