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
		/*
		WebElement tableElem = wd.findElements(By.tagName("table")).get(0);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		WebElement row = rowsOnTable.next();
		assertEquals("MID", row.getText());
		assertTrue(row.getText().contains("First Name"));
		assertTrue(row.getText().contains("Last Name"));
		*/
		
		
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
		assertEquals("iTrust - Edit Patient", wd.getTitle());

	}
	
	
	
	@Test
	public void ViewObstetricsRecordsTest() throws Exception{
		WebDriver wd = login("9000000003","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		wd.findElement(By.linkText("Patient Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - Obstetrics Records", wd.getTitle());
		/*
		WebTable ObsTable = wr.getTableStartingWith("Date");
		//Check the fields of the table
		assertEquals("Date", ObsTable.getCellAsText(0, 0));
		assertEquals("LMP", ObsTable.getCellAsText(0, 1));
		assertEquals("EDD", ObsTable.getCellAsText(0, 2));
		assertEquals("Number of Weeks Pregnant", ObsTable.getCellAsText(0, 3));
		
		//Check the datas of the table
		
		assertEquals("", ObsTable.getCellAsText(1, 0));
		assertEquals("", ObsTable.getCellAsText(1, 1));
		assertEquals("", ObsTable.getCellAsText(1, 2));
		assertEquals("", ObsTable.getCellAsText(1, 3));
		
		
		
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
	
	/*
	@Test
	public void EditObstetricsRecordsTest() throws Exception{
		WebConversation wc = login("9000000012","pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Patient Records").click();
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
	*/

}
