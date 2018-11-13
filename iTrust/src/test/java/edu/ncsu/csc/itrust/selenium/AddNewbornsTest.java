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

public class AddNewbornsTest extends iTrustSeleniumTest {
	@Override
	protected void setUp() throws Exception {
		//super.setUp();
		//gen.clearAllTables();
		//gen.standardData();
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
		
		wd.findElement(By.linkText("View Childbirth Office Visit")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		
		// Select a patient and go to the add childbirth visits records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Childbirth Office Visits", wd.getTitle());
		
		// Get the childbirth visits table
		wd.findElement(By.id("addButton")).click();
		assertEquals("iTrust - Add Newborns", wd.getTitle());
		
		// Fill in form and send
		WebElement form = wd.findElement(By.id("addNewborns"));
		WebElement dm = wd.findElement(By.name("deliveryMethod"));
		Select dm_dropdown= new Select(dm);
		dm.clear();
		dm_dropdown.selectByVisibleText("miscarriage");
		form.findElement(By.name("deliveryDateTime")).clear();
		form.findElement(By.name("deliveryDateTime")).sendKeys("11/15/2018 10:00");
		form.findElement(By.name("childFirstName")).clear();
		form.findElement(By.name("childFirstName")).sendKeys("lee");
		form.findElement(By.name("childLastName")).clear();
		form.findElement(By.name("childLastName")).sendKeys("sin");
		form.submit();
		
		assertEquals("iTrust - Add Newborns", wd.getTitle());
		WebElement table = wd.findElement(By.id("CBlist"));
		List<WebElement> tableData = table.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		WebElement row = rowsOnTable.next();
		assertTrue(row.getText().contains("Delivery Records"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Child ID"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Male"));
		assertTrue(row.getText().contains("11/15/2018 10:00"));
		assertTrue(row.getText().contains("miscarriage"));
		assertTrue(row.getText().contains("true"));
		
		
	}
}
