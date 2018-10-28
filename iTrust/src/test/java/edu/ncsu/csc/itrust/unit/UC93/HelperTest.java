package edu.ncsu.csc.itrust.unit.UC93;

import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.TransactionBean;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.selenium.Driver;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class HelperTest extends TestCase{
	/*
	 * The URL for iTrust, change as needed
	 */
	/** ADDRESS */
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	/** gen */
	protected TestDataGenerator gen = new TestDataGenerator();

	/** Default timeout for Selenium webdriver */
	public static final int DEFAULT_TIMEOUT = 2;

	/**
	 * Name of the value attribute of html tags. Used for getting the value from
	 * a form input with .getAttribute("value"). Was previously
	 * .getAttribute(VALUE) before being removed by s selenium.
	 */
	public static final String VALUE = "value";

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
	}

	/**
	 * Helper method for logging in to iTrust
	 * 
	 * Also creates an explicit WebDriverWait for optional use.
	 * 
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @return {@link WebConversation}
	 * @throws Exception
	 */
	public WebConversation login(String username, String password) throws Exception {
		// begin at the iTrust home page
		WebConversation wc = new WebConversation();

		WebResponse wr = wc.getResponse(ADDRESS);
		
		// log in using the given username and password
		WebForm form = wr.getForms()[0];
		form.setParameter("j_username", username);
		form.setParameter("j_password", password);
		WebResponse homepage = wr.getForms()[0].submit();
		
		if (homepage.getTitle().equals("iTrust - Login")) {
			throw new IllegalArgumentException("Error logging in, user not in database?");
		}

		return wc;
	}

	/**
	 * assertLogged
	 * 
	 * @param code
	 *            code
	 * @param loggedInMID
	 *            loggedInMID
	 * @param secondaryMID
	 *            secondaryMID
	 * @param addedInfo
	 *            addedInfo
	 * @throws DBException
	 */
	public static void assertLogged(TransactionType code, long loggedInMID, long secondaryMID, String addedInfo)
			throws DBException, InterruptedException {

		// Selenium on jenkins sometimes has issues finding a log the first
		// time.
		// The proper solution would be to add explicit waits, but it is easier
		// to wait a second and try again.
		int i = 0;
		while (i < 3) {
			List<TransactionBean> transList = TestDAOFactory.getTestInstance().getTransactionDAO().getAllTransactions();
			for (TransactionBean t : transList) {
				if ((t.getTransactionType() == code) && (t.getLoggedInMID() == loggedInMID)
						&& (t.getSecondaryMID() == secondaryMID)) {
					assertTrue(t.getTransactionType() == code);
					if (!t.getAddedInfo().trim().contains(addedInfo.trim())) {
						fail("Additional Information is not logged correctly.");
					}
					return;
				}
			}

			i++;
			Thread.sleep(1000);
		}

		fail("Event not logged as specified.");
	}

	/**
	 * assertLogged
	 * 
	 * @param code
	 *            code
	 * @param loggedInMID
	 *            loggedInMID
	 * @param secondaryMID
	 *            secondaryMID not used
	 * @param addedInfo
	 *            addedInfo
	 * @throws DBException
	 */
	public static void assertLoggedNoSecondary(TransactionType code, long loggedInMID, long secondaryMID,
			String addedInfo) throws DBException {
		List<TransactionBean> transList = TestDAOFactory.getTestInstance().getTransactionDAO().getAllTransactions();
		for (TransactionBean t : transList) {
			if ((t.getTransactionType() == code) && (t.getLoggedInMID() == loggedInMID)) {
				assertTrue(t.getTransactionType() == code);
				if (!t.getAddedInfo().trim().contains(addedInfo.trim())) {
					fail("Additional Information is not logged correctly.");
				}
				return;
			}
		}
		fail("Event not logged as specified.");
	}

	/**
	 * assertNotLogged
	 * 
	 * @param code
	 *            code
	 * @param loggedInMID
	 *            loggedInMID
	 * @param secondaryMID
	 *            secondaryMID
	 * @param addedInfo
	 *            addedInfo
	 * @throws DBException
	 */
	public static void assertNotLogged(TransactionType code, long loggedInMID, long secondaryMID, String addedInfo)
			throws DBException {
		List<TransactionBean> transList = TestDAOFactory.getTestInstance().getTransactionDAO().getAllTransactions();
		for (TransactionBean t : transList) {
			if ((t.getTransactionType() == code) && (t.getLoggedInMID() == loggedInMID)
					&& (t.getSecondaryMID() == secondaryMID) && (t.getAddedInfo().trim().contains(addedInfo))) {
				fail("Event was logged, but should NOT have been logged");
				return;
			}
		}
	}

}
