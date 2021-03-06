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
import org.openqa.selenium.support.ui.Select;

public class AddChildbirthVisitTest extends iTrustSeleniumTest {
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
	public void testViewChildbirthVisits() throws Exception{
		// Log in as ObHCP
		WebDriver wd = login("9000000012","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		wd.findElement(By.linkText("Document Childbirth Office Visit")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		
		// Select a patient and go to the add childbirth visits records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - Add a Childbirth Visit", wd.getTitle());
		
		// Get the childbirth visits table
		WebElement tableElem = wd.findElements(By.tagName("table")).get(0);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		
		// Check the title and fields of the table
		WebElement row = rowsOnTable.next();
		assertTrue(row.getText().contains("New Childbirth Visit"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Patient ID:"));
		
		// Get the add cb visit form
		WebElement form = wd.findElement(By.id("addCbVisitForm"));
				
		// Create a new cb visit
		WebElement pcm = wd.findElement(By.name("preferredChildbirthMethod"));
		Select pcm_dropdown= new Select(pcm);
		pcm_dropdown.selectByVisibleText("caesarean section");
		form.findElement(By.name("drugs")).sendKeys("(t, 5)");
		form.findElement(By.name("scheduledDate")).sendKeys("11/03/2019");
		WebElement chk_prescd = form.findElement(By.id("ER"));
		chk_prescd.click();
		form.submit();
		
		// verify the cb visit was saved
		assertEquals("iTrust - View Childbirth Office Visits", wd.getTitle());
		//tableElem = wd.findElements(By.tagName("table")).get(0);
		tableElem = wd.findElement(By.id("CBlist"));
		tableData = tableElem.findElements(By.tagName("tr"));
		rowsOnTable = tableData.iterator();
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Childbirth Office Visits"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("ID"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("caesarean section"));
		assertTrue(row.getText().contains("(t, 5)"));
		assertTrue(row.getText().contains("11/03/2019"));
		assertTrue(row.getText().contains("false"));
				
				
		// Get the date of today
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		
		//assertTrue(row.getText().contains(dateFormat.format(date)));
	}
}
