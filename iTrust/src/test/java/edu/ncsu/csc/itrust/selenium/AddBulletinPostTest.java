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

public class AddBulletinPostTest extends iTrustSeleniumTest {
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
	 * Tests the ability for the HCP that is logged in to post on the bulletin board
	 * @throws Exception
	 */
	
	@Test
	public void testAddBulletinPost() throws Exception{
		// Log in as ObHCP
		WebDriver wd = login("9000000012","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		// Get the bulletin board panel
		wd.findElement(By.linkText("Add New Post")).click();
		assertEquals("iTrust - Add a Bulletin Post", wd.getTitle());
		
		// Get the add bulletin post form
		WebElement form = wd.findElement(By.id("mainForm"));
		
		// Create a new bulletin post
		form.findElement(By.name("subject")).sendKeys("test subject");
		form.findElement(By.name("postBody")).sendKeys("test content");
		// Get the date of today
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String date_s = dateFormat.format(date);
		form.submit();
		
		// verify the bulletin post was saved
		assertEquals("iTrust - HCP Home", wd.getTitle());
		wd.findElement(By.linkText("test subject")).click();
		WebElement pagecontent = wd.findElement(By.id("iTrustContent"));
		assertTrue(pagecontent.getText().contains("Author: Kathryn Evans"));
		assertTrue(pagecontent.getText().contains("Title: test subject"));
		assertTrue(pagecontent.getText().contains("Date: " + date_s));
		assertTrue(pagecontent.getText().contains("Comments:"));
		// verify if the content was saved to the post
		WebElement textarea = wd.findElement(By.name("postBody"));
		String content = textarea.getText();
		assertEquals("test content", content);
	}
}
