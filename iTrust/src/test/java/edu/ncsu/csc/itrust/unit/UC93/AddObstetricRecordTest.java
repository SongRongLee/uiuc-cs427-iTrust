package edu.ncsu.csc.itrust.unit.UC93;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddObstetricRecordTest extends HelperSeleniumTest{
	
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
	 * Tests the ability for the OB/GYN HCP that is logged in to add a patients Obstetrics information if that obstetric information exists
	 * if any exists
	 * @throws Exception
	 */
	@Test
	public void testAddObstetricsRecords() throws Exception{
		//log in as OBHCP0
		WebDriver wd = login("9000000012","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
				
		wd.findElement(By.linkText("Patient Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
				
		//Select a Patient and view the Patient's Obstetrics Records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");/////////change to PatientID that has Ob info!
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
				
		//get the Obstetrics Records Table
		WebElement tableElem = wd.findElements(By.tagName("table")).get(0);

		//click on an existing record and enter the view page
		tableElem.findElements(By.id("viewButton")).get(0).click();
		assertEquals("iTrust - View an Obstetrics Record", wd.getTitle());
				
		//Click on the add button which has the id "addButton"
		tableElem.findElement(By.id("addButton")).click();
		assertEquals("iTrust - Add an Obstetrics Record", wd.getTitle());
				
		// Get the date
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		
		//Enter info
		WebElement form = wd.findElement(By.id("addObRecordForm"));
		assertTrue(form.findElement(By.name("Date")).toString().contains(dateFormat.format(date)));
		form.findElement(By.name("LMP")).sendKeys("08/05/2018");
		form.findElement(By.name("YearOfConception")).sendKeys("1992");
		form.findElement(By.name("hoursLabor")).sendKeys("25.0");
		form.findElement(By.name("weightGain")).sendKeys("10");
		form.findElement(By.name("deliveryType")).sendKeys("Vaginal Delivery");
		form.findElement(By.name("numbChildren")).sendKeys("2");
		form.submit();
		
		//Check if redirected to the obstetrics records page
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
		
		tableElem = wd.findElements(By.tagName("table")).get(0);
		tableElem.findElements(By.id("viewButton")).get(0).click();
		assertEquals("iTrust - View an Obstetrics Record", wd.getTitle());
				
		//Verify info was saved
		tableElem = wd.findElements(By.tagName("table")).get(0);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		
		//first row is a title of the table
		WebElement row = rowsOnTable.next();
		//fields starts from second row
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Date"));
		assertTrue(row.getText().contains("10/29/2018"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("EDD"));
		assertTrue(row.getText().contains("05/11/2019"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Year of conception"));
		assertTrue(row.getText().contains("1992"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Number of weeks pregnant"));
		assertTrue(row.getText().contains("12-2"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Number of hours in labor"));
		assertTrue(row.getText().contains("25.0"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Weight gain during pregnancy"));
		assertTrue(row.getText().contains("10"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Delivery type"));
		assertTrue(row.getText().contains("Vaginal Delivery"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Number of children"));
		assertTrue(row.getText().contains("2"));
	}
	

}

