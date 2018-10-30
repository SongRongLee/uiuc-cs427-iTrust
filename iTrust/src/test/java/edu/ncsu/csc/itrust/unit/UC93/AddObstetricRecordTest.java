package edu.ncsu.csc.itrust.unit.UC93;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import edu.ncsu.csc.itrust.selenium.iTrustSeleniumTest;

public class AddObstetricRecordTest extends iTrustSeleniumTest{
	
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
	 * Tests the ability for the OB/GYN HCP that is logged in to add a patients Obstetrics information if that obstetric information exists
	 * if any exists
	 * @throws Exception
	 */
	@Test
	public void testAddObstetricsRecords() throws Exception{
		// Log in as OBHCP0
		WebDriver wd = login("9000000012","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
				
		wd.findElement(By.linkText("Patient Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
				
		// Select a Patient and view the Patient's Obstetrics Records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
				
		// Click on the add button which has the id "addButton"
		wd.findElement(By.id("addButton")).click();
		assertEquals("iTrust - Add an Obstetrics Record", wd.getTitle());
				
		// Get the date
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		
		// Enter info
		WebElement form = wd.findElement(By.id("addObRecordForm"));
		assertTrue(form.findElement(By.name("Date")).toString().contains(dateFormat.format(date)));
		form.findElement(By.name("LMP")).sendKeys("08/05/2018");
		form.findElement(By.name("YearOfConception")).sendKeys("2012");
		form.findElement(By.name("WeeksPregnant")).sendKeys("40-0");
		form.findElement(By.name("HoursLabor")).sendKeys("25.0");
		form.findElement(By.name("WeightGain")).sendKeys("10");
		form.findElement(By.name("DeliveryType")).sendKeys("vaginal delivery");
		form.findElement(By.name("NumChildren")).sendKeys("2");
		form.submit();
		
		// Check if redirected to the obstetrics records page
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
		
		WebElement tableElem = wd.findElements(By.tagName("table")).get(0);
		tableElem.findElements(By.id("viewButton")).get(0).click();
		assertEquals("iTrust - View an Obstetrics Record", wd.getTitle());
				
		// Verify info was saved
		tableElem = wd.findElements(By.tagName("table")).get(0);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		
		WebElement row = rowsOnTable.next();
		assertTrue(row.getText().contains("Date"));
		assertTrue(row.getText().contains("10/29/2018"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("EDD"));
		assertTrue(row.getText().contains("05/11/2019"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Current number of weeks pregnant"));
		assertTrue(row.getText().contains("12-2"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Year of conception"));
		assertTrue(row.getText().contains("2012"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Number of weeks pregnant"));
		assertTrue(row.getText().contains("40-0"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Number of hours in labor"));
		assertTrue(row.getText().contains("25.0"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Weight gain during pregnancy"));
		assertTrue(row.getText().contains("10"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Delivery type"));
		assertTrue(row.getText().contains("vaginal delivery"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Number of children"));
		assertTrue(row.getText().contains("2"));
	}
	
	/**
	 * Tests if error message is printed when enter blank input
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBlankObstetricsInput() throws Exception{
		// Log in as OBHCP0
		WebDriver wd = login("9000000012","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
				
		wd.findElement(By.linkText("Patient Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
				
		// Select a Patient and view the Patient's Obstetrics Records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
				
		// Click on the add button which has the id "addButton"
		wd.findElement(By.id("addButton")).click();
		assertEquals("iTrust - Add an Obstetrics Record", wd.getTitle());
		
		// Enter blank LMP
		WebElement form = wd.findElement(By.id("addObRecordForm"));
		form.findElement(By.name("YearOfConception")).sendKeys("2012");
		form.findElement(By.name("WeeksPregnant")).sendKeys("40-0");
		form.findElement(By.name("HoursLabor")).sendKeys("25.0");
		form.findElement(By.name("WeightGain")).sendKeys("10");
		form.findElement(By.name("DeliveryType")).sendKeys("vaginal delivery");
		form.findElement(By.name("NumChildren")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter blank YearOfConception
		form = wd.findElement(By.id("addObRecordForm"));
		form.findElement(By.name("LMP")).sendKeys("08/05/2018");
		form.findElement(By.name("WeeksPregnant")).sendKeys("40-0");
		form.findElement(By.name("HoursLabor")).sendKeys("25.0");
		form.findElement(By.name("WeightGain")).sendKeys("10");
		form.findElement(By.name("DeliveryType")).sendKeys("vaginal delivery");
		form.findElement(By.name("NumChildren")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter blank WeeksPregnant
		form = wd.findElement(By.id("addObRecordForm"));
		form.findElement(By.name("LMP")).sendKeys("08/05/2018");
		form.findElement(By.name("YearOfConception")).sendKeys("2012");
		form.findElement(By.name("HoursLabor")).sendKeys("25.0");
		form.findElement(By.name("WeightGain")).sendKeys("10");
		form.findElement(By.name("DeliveryType")).sendKeys("vaginal delivery");
		form.findElement(By.name("NumChildren")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter blank HoursLabor
		form = wd.findElement(By.id("addObRecordForm"));
		form.findElement(By.name("LMP")).sendKeys("08/05/2018");
		form.findElement(By.name("YearOfConception")).sendKeys("2012");
		form.findElement(By.name("WeeksPregnant")).sendKeys("40-0");
		form.findElement(By.name("WeightGain")).sendKeys("10");
		form.findElement(By.name("DeliveryType")).sendKeys("vaginal delivery");
		form.findElement(By.name("NumChildren")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter blank WeightGain
		form = wd.findElement(By.id("addObRecordForm"));
		form.findElement(By.name("LMP")).sendKeys("08/05/2018");
		form.findElement(By.name("YearOfConception")).sendKeys("2012");
		form.findElement(By.name("WeeksPregnant")).sendKeys("40-0");
		form.findElement(By.name("HoursLabor")).sendKeys("25.0");
		form.findElement(By.name("DeliveryType")).sendKeys("vaginal delivery");
		form.findElement(By.name("NumChildren")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter blank DeliveryType
		form = wd.findElement(By.id("addObRecordForm"));
		form.findElement(By.name("LMP")).sendKeys("08/05/2018");
		form.findElement(By.name("YearOfConception")).sendKeys("2012");
		form.findElement(By.name("WeeksPregnant")).sendKeys("40-0");
		form.findElement(By.name("HoursLabor")).sendKeys("25.0");
		form.findElement(By.name("WeightGain")).sendKeys("10");
		form.findElement(By.name("NumChildren")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter blank NumChildren
		form = wd.findElement(By.id("addObRecordForm"));
		form.findElement(By.name("LMP")).sendKeys("08/05/2018");
		form.findElement(By.name("YearOfConception")).sendKeys("2012");
		form.findElement(By.name("WeeksPregnant")).sendKeys("40-0");
		form.findElement(By.name("HoursLabor")).sendKeys("25.0");
		form.findElement(By.name("WeightGain")).sendKeys("10");
		form.findElement(By.name("DeliveryType")).sendKeys("vaginal delivery");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));		
	}
	
	/**
	 * Tests invalid input.
	 * Case 1: LMP 123
	 * Case 2: YearOfConception 992
	 * Case 3: WeeksPregnant 37-50
	 * Case 4: HoursLabor 250.0
	 * Case 5: NumChildren 20
	 * Valid case: LMP 08/05/2018, YearOfConception 1992, WeeksPregnant 37-5, HoursLabor 25.0, NumChildren 2
	 * @throws Exception
	 */
	@Test
	public void testBadObstetricsInput() throws Exception{
		// Log in as OBHCP0
		WebDriver wd = login("9000000012","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
				
		wd.findElement(By.linkText("Patient Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
				
		// Select a Patient and view the Patient's Obstetrics Records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
				
		// Click on the add button which has the id "addButton"
		wd.findElement(By.id("addButton")).click();
		assertEquals("iTrust - Add an Obstetrics Record", wd.getTitle());
		
		// Enter bad LMP
		WebElement form = wd.findElement(By.id("addObRecordForm"));
		form.findElement(By.name("LMP")).sendKeys("123");
		form.findElement(By.name("YearOfConception")).sendKeys("1992");
		form.findElement(By.name("WeeksPregnant")).sendKeys("37-5");
		form.findElement(By.name("HoursLabor")).sendKeys("25.0");
		form.findElement(By.name("WeightGain")).sendKeys("10");
		form.findElement(By.name("DeliveryType")).sendKeys("vaginal delivery");
		form.findElement(By.name("NumChildren")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter bad YearOfConception
		form = wd.findElement(By.id("addObRecordForm"));
		form.findElement(By.name("LMP")).sendKeys("08/05/2018");
		form.findElement(By.name("YearOfConception")).sendKeys("992");
		form.findElement(By.name("WeeksPregnant")).sendKeys("37-5");
		form.findElement(By.name("HoursLabor")).sendKeys("25.0");
		form.findElement(By.name("WeightGain")).sendKeys("10");
		form.findElement(By.name("DeliveryType")).sendKeys("vaginal delivery");
		form.findElement(By.name("NumChildren")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter bad WeeksPregnant
		form = wd.findElement(By.id("addObRecordForm"));
		form.findElement(By.name("LMP")).sendKeys("08/05/2018");
		form.findElement(By.name("YearOfConception")).sendKeys("1992");
		form.findElement(By.name("WeeksPregnant")).sendKeys("37-50");
		form.findElement(By.name("HoursLabor")).sendKeys("25.0");
		form.findElement(By.name("WeightGain")).sendKeys("10");
		form.findElement(By.name("DeliveryType")).sendKeys("vaginal delivery");
		form.findElement(By.name("NumChildren")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter bad HoursLabor
		form = wd.findElement(By.id("addObRecordForm"));
		form.findElement(By.name("LMP")).sendKeys("08/05/2018");
		form.findElement(By.name("YearOfConception")).sendKeys("1992");
		form.findElement(By.name("WeeksPregnant")).sendKeys("37-5");
		form.findElement(By.name("HoursLabor")).sendKeys("250.0");
		form.findElement(By.name("WeightGain")).sendKeys("10");
		form.findElement(By.name("DeliveryType")).sendKeys("vaginal delivery");
		form.findElement(By.name("NumChildren")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));

		// Enter bad NumChildren
		form = wd.findElement(By.id("addObRecordForm"));
		form.findElement(By.name("LMP")).sendKeys("08/05/2018");
		form.findElement(By.name("YearOfConception")).sendKeys("1992");
		form.findElement(By.name("WeeksPregnant")).sendKeys("37-5");
		form.findElement(By.name("HoursLabor")).sendKeys("25.0");
		form.findElement(By.name("WeightGain")).sendKeys("10");
		form.findElement(By.name("DeliveryType")).sendKeys("vaginal delivery");
		form.findElement(By.name("NumChildren")).sendKeys("20");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));		
	}
	

}

