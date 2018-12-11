package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GenObstetricsReportTest extends iTrustSeleniumTest {

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
	 * Test a current labor and delivery report is generated and displayed containing informations
	 * @throws Exception
	 */
	@Test
	public void testGenObstetricsReport() throws Exception{
		// Log in as HCP3
		WebDriver wd = login("9000000003","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		// Go to the Generate Report Page
		wd.findElement(By.linkText("Generate Report")).click();
		
		// Select a patient and view the patient's labor and delivery report
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Labor and Delivery Report", wd.getTitle());
		
		// Get the patient information table
		WebElement tableElem = wd.findElements(By.tagName("table")).get(0);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		
		// Check the title and fields of the table
		WebElement row = rowsOnTable.next();
		assertEquals("Patient Information", row.getText());
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Pre-existing conditions"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Diabetes"));
		assertTrue(row.getText().contains("[E10 - Type 1 diabetes mellitus - Not Chronic]"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Autoimmune disorders"));
		assertTrue(row.getText().contains("[D6851 - Factor V Leiden mutation - Chronic, O211 - Hyperemesis gravidarum with me - Chronic, E039 - Hypothyroidism, unspecified - Chronic]"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Cancer"));
		assertTrue(row.getText().contains("[C50 - Malignant neoplasm of breast - Not Chronic]"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("STDs"));
		assertTrue(row.getText().contains("[A568 - Sexually transmitted chlamydia - Not Chronic]"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Common Drug Allergies"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Penicillin"));
		
		// Get the past pregnancies table
		tableElem = wd.findElements(By.tagName("table")).get(1);
		tableData = tableElem.findElements(By.tagName("tr"));
		rowsOnTable = tableData.iterator();
		
		// Check the title and fields of the table
		row = rowsOnTable.next();
		assertEquals("Past Pregnancies", row.getText());
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Pregnancy term"));
		assertTrue(row.getText().contains("Delivery type"));
		assertTrue(row.getText().contains("Conception year"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("41 weeks"));
		assertTrue(row.getText().contains("caesarean section"));
		assertTrue(row.getText().contains("2011"));
		
		// Get the estimated delivery date table
		tableElem = wd.findElements(By.tagName("table")).get(2);
		tableData = tableElem.findElements(By.tagName("tr"));
		rowsOnTable = tableData.iterator();
				
		// Check the title and fields of the table
		row = rowsOnTable.next();
		assertEquals("Estimated Delivery Date", row.getText());
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("07/08/2019"));
		
		// Get the blood type table
		tableElem = wd.findElements(By.tagName("table")).get(3);
		tableData = tableElem.findElements(By.tagName("tr"));
		rowsOnTable = tableData.iterator();
						
		// Check the title and fields of the table
		row = rowsOnTable.next();
		assertEquals("Blood Type", row.getText());
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("AB+"));
		
		// Get the obstretrics office visits table
		tableElem = wd.findElement(By.id("OBlist"));
		tableData = tableElem.findElements(By.tagName("tr"));
		rowsOnTable = tableData.iterator();
				
		// Check the title and fields of the table
		row = rowsOnTable.next();
		assertEquals("Obstretrics Office Visits", row.getText());
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Weeks Pregnant"));
		assertTrue(row.getText().contains("Weight(pounds)"));
		assertTrue(row.getText().contains("Blood Pressure"));
		assertTrue(row.getText().contains("Fetal heart rate"));
		assertTrue(row.getText().contains("Num Children"));
		assertTrue(row.getText().contains("Low Lying Placenta"));
		assertTrue(row.getText().contains("Pregnancy complication warning flags"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("11-2"));
		assertTrue(row.getText().contains("25.0"));
		assertTrue(row.getText().contains("120/180"));
		assertTrue(row.getText().contains("250"));
		assertTrue(row.getText().contains("2"));
		assertTrue(row.getText().contains("false"));
		assertTrue(row.getText().contains("High Blood Pressure"));
		assertTrue(row.getText().contains("Advanced Maternal Age"));
		assertTrue(row.getText().contains("High genetic potential for miscarriage"));
		assertTrue(row.getText().contains("Abnormal fetal heart rate"));
		assertTrue(row.getText().contains("Multiples in current pregnancy"));
		assertTrue(row.getText().contains("Atypical weight change"));
		assertTrue(row.getText().contains("Hyperemesis gravidarum"));
		assertTrue(row.getText().contains("Hypothyroidism"));
	}
	
	/**
	 * Generate report for patient that is not eligible
	 * @throws Exception
	 */
	@Test
	public void testGenReportNotEligible() throws Exception{
		// Log in as HCP3
		WebDriver wd = login("9000000003","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		// Go to the Generate Report Page
		wd.findElement(By.linkText("Generate Report")).click();
		
		// Select a patient and view the patient's labor and delivery report
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Labor and Delivery Report", wd.getTitle());
		
		// The page should show error message for not eligible patient
		assertTrue(wd.findElement(By.id("GenReportError")).getText().contains("Selected patient does not have an obstetrics record."));

	}

}
