package edu.ncsu.csc.itrust.selenium;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddObstetricVisitTest extends iTrustSeleniumTest {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		// Turn off htmlunit warnings
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	}

	/**
	 * Tests the ability for the HCP that is logged in to view a patients Obstetrics Visits
	 * @throws Exception
	 */
	
	@Test
	public void testViewObstetricsVisits() throws Exception{
		// Log in as HCP3
		WebDriver wd = login("9000000012","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		wd.findElement(By.linkText("Document Obstetrics Office Visit")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		
		// Select a patient and go to the add obstetrics visits records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - Add an Obstetrics Visit", wd.getTitle());
		
		// Get the obstetrics visits table
		WebElement tableElem = wd.findElements(By.tagName("table")).get(0);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		
		// Check the title and fields of the table
		WebElement row = rowsOnTable.next();
		assertTrue(row.getText().contains("New Obstetrics Visit"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Patient ID:"));
		
		// Get the add ob visit form
		WebElement form = wd.findElement(By.id("addObVisitForm"));
				
				// Create a new ob visit
				form.findElement(By.name("scheduledDate")).sendKeys("11/03/2019 15:05");
				form.findElement(By.name("weight")).sendKeys("155");
				form.findElement(By.name("bloodPressure")).sendKeys("20");
				form.findElement(By.name("FHR")).sendKeys("10.0");
				form.findElement(By.name("numChildren")).sendKeys("vaginal delivery");
				form.submit();
				
//				// Verify the new prior pregnancy was saved
//				WebElement tableElem = wd.findElement(By.id("AddOBViewPreg"));
//				List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
//				Iterator<WebElement> rowsOnTable = tableData.iterator();
//				WebElement row = rowsOnTable.next();
//				assertTrue(row.getText().contains("Prior Pregnancies"));
//				row = rowsOnTable.next();
//				row = rowsOnTable.next();
//				assertTrue(row.getText().contains("2012"));
//				assertTrue(row.getText().contains("40"));
//				assertTrue(row.getText().contains("20"));
//				assertTrue(row.getText().contains("10.0"));
//				assertTrue(row.getText().contains("vaginal delivery"));
//				assertTrue(row.getText().contains("2"));
//				
//				// Get the date of today
//				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//				Date date = new Date();
//				
//				// Get the add obstetrics record form
//				form = wd.findElement(By.id("addObRecordForm"));
//				
//				// Check if the date field is filled in automatically
//				assertTrue(form.findElement(By.name("created_on")).toString().contains(dateFormat.format(date)));

	
	}
}
