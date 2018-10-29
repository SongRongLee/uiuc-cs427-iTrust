package edu.ncsu.csc.itrust.unit.UC93;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

public class ViewObstetricRecordTest extends HelperSeleniumTest{
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		// turn off htmlunit warnings
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	}
	
	/**
	 * Just a sample test
	 * Please ignore this test
	 * @throws Exception
	 */
	@Test
	public void testSample() throws Exception{
		WebDriver wd = login("9000000003","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		wd.findElement(By.linkText("Patient Information")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		
		//fill in search form
		//wd.findElement(By.id("searchBox")).sendKeys("1");
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		
		/*
		wd.findElement(By.name("FIRST_NAME")).clear();
		wd.findElement(By.name("FIRST_NAME")).sendKeys("Random");
		wd.findElement(By.name("LAST_NAME")).clear();
		wd.findElement(By.name("FIRST_NAME")).sendKeys("Person");
		wd.findElement(By.cssSelector("input[type='submit']")).click();
		*/
		//WebTable table = wd.findElement(By.ByClassName.className("fTable"));
		//WebElement Table = wd.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[3]/table"));
		//WebElement Table = wd.findElement(By.ByClassName.className("fTable"));
		//wd.findElement(By.cssSelector("input[value='1']")).click();
		//wd.findElement(By.xpath("//input[@value='1']")).click();
		//assertEquals("iTrust - Edit Patient", wd.getTitle());
		//assertEquals("http://localhost:8080/iTrust/auth/hcp-uap/editPatient.jsp", wd.getCurrentUrl());
		//wd.findElements(By.tagName("input")).get(1).click();
		assertEquals("iTrust - Edit Patient", wd.getTitle());
		
		WebElement tableElem = wd.findElements(By.tagName("table")).get(1);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		
		WebElement row = rowsOnTable.next();
		assertTrue(row.getText().contains("Patient Information"));

		row = rowsOnTable.next();
		assertTrue(row.getText().contains("First Name:"));
		assertTrue(row.findElement(By.tagName("input")).toString().contains("Random"));
		
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Last Name:"));
		
		
		
		/*
		wd.findElement(By.name("FIRST_NAME")).clear();
		wd.findElement(By.name("FIRST_NAME")).sendKeys("Random");
		wd.findElement(By.name("LAST_NAME")).clear();
		wd.findElement(By.name("FIRST_NAME")).sendKeys("Person");
		wd.findElement(By.cssSelector("input[type='submit']")).click();
		
		wd.findElement(By.xpath("//input[@value='1']")).submit();
		assertEquals("iTrust - Edit Patient", wd.getTitle());
		*/
		/*
		//WebElement Table = wd.findElement(By.ByClassName.className("Table.fTable"));
		assertEquals("MID", wd.findElement(By.xpath("//table/tbody/tr[0]/td[0]")).getText());
		assertEquals("", wd.findElement(By.xpath("//table/tbody/tr[2]/td[1]")).getText());
		assertEquals("Random", wd.findElement(By.xpath("//table/tbody/tr[2]/td[2]")).getText());
		assertEquals("Person", wd.findElement(By.xpath("//table/tbody/tr[2]/td[3]")).getText());
		wd.findElement(By.xpath("//button[contains(text(),'1')]")).click();
		*/
		

	}
	
	
	/**
	 * Tests the ability for the HCP that is logged in to view a patients Obstetrics information if that obstetric information exists
	 * if any exists
	 * @throws Exception
	 */
	
	@Test
	public void testViewObstetricsRecords() throws Exception{
		//log in as HCP3
		WebDriver wd = login("9000000003","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		wd.findElement(By.linkText("Patient Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		
		//Select a Patient and view the Patient's Obstetrics Records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");/////////change to PatientID that has Ob info!
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
		
		//get the Obstetrics Records Table
		WebElement tableElem = wd.findElements(By.tagName("table")).get(0);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		
		WebElement row = rowsOnTable.next();
		//Check the fields of the table
		//field1 : date - list the date of each record 
		//field2 : (blank) - for each record there is a "view" button which has the id "viewButton"  
		assertTrue(row.getText().contains("Date"));
		
		//click on an existing record and enter the view page
		tableElem.findElements(By.id("viewButton")).get(0).click();
		assertEquals("iTrust - View an Obstetrics Record", wd.getTitle());
		
		//Check the fields of the table
		tableElem = wd.findElements(By.tagName("table")).get(0);
		tableData = tableElem.findElements(By.tagName("tr"));
		rowsOnTable = tableData.iterator();
		
		//first row is a title of the table
		row = rowsOnTable.next();
		//fields starts from second row
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Date"));
		assertTrue(row.getText().contains(""));/////////fill in the data
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("EDD"));
		assertTrue(row.getText().contains(""));/////////fill in the data
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Year of conception"));
		assertTrue(row.getText().contains(""));/////////fill in the data
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Number of weeks pregnant"));
		assertTrue(row.getText().contains(""));/////////fill in the data
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Number of hours in labor"));
		assertTrue(row.getText().contains(""));/////////fill in the data
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Weight gain during pregnancy"));
		assertTrue(row.getText().contains(""));/////////fill in the data
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Delivery type"));
		assertTrue(row.getText().contains(""));/////////fill in the data
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Number of children"));
		assertTrue(row.getText().contains(""));/////////fill in the data
	}
	
	/**
	 * View Obstetrics information for a female patient who doesn't have any obstetrics information
	 * @throws Exception
	 */
	public void testViewObstetricsEmptyRequest() throws Exception{
		WebDriver wd = login("9000000003","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		wd.findElement(By.linkText("Patient Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		
		//Select a female Patient who doesn't have any obstetrics information
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");/////////change to PatientID of a female patient and has no Ob info!
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
		
		//there should be no Obstetrics Record
		assertEquals("No Obstetric Information", wd.findElement(By.id("obRecordError")).getText());
}
	
	/**
	 * View Obstetrics information for a male patient who doesn't have any obstetrics information
	 * @throws Exception
	 */
	
	public void testViewObstetricsMaleRequest() throws Exception{
		WebDriver wd = login("9000000003","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		wd.findElement(By.linkText("Patient Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		
		//Select a male Patient and view the Patient's Obstetrics Records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("108");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
		
		//there should be no Obstetrics Record
		assertEquals("The patient is not eligible for obstetric care.", wd.findElement(By.id("obRecordError")).getText());
	}

}
