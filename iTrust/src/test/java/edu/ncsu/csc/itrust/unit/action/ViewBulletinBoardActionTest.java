package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import edu.ncsu.csc.itrust.action.AddChildbirthVisitAction;
import edu.ncsu.csc.itrust.action.AddObstetricsAction;
import edu.ncsu.csc.itrust.action.AddPatientAction;
import edu.ncsu.csc.itrust.action.EditChildbirthVisitAction;
import edu.ncsu.csc.itrust.action.ViewBulletinBoardAction;
import edu.ncsu.csc.itrust.action.ViewChildbirthVisitAction;
import edu.ncsu.csc.itrust.action.ViewObstetricsAction;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.BulletinBoardBean;
import edu.ncsu.csc.itrust.model.old.beans.CommentBean;
import edu.ncsu.csc.itrust.model.old.beans.ChildbirthVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.BulletinBoardDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ChildbirthVisitDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ViewBulletinBoardActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ViewBulletinBoardAction action;

	/**
	 * Sets up defaults
	 */
	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.transactionLog();
		gen.ucCustom();
		action = new ViewBulletinBoardAction(factory, 9000000012L, "1");
	}
	
	@Test
	public void testGetPatient() {
		PatientBean bean1 = null;
		try {
			bean1 = action.getPatient();
		} catch (ITrustException e) {
			fail();
		}
		if (bean1 == null)
			fail();
		assertEquals(bean1.getMID(), 1L); // and just test that the right record came back
		
		PatientBean bean2 = null;
		try {
			bean2 = action.getPatient();
		} catch (ITrustException e) {
			assertNull(bean2);
		}

		PatientBean bean3 = null;
		try {
			bean3 = action.getPatient();
		} catch (ITrustException e) {
			assertNull(bean3);
		}
	}
	
	@Test
	public void testGetBulletinBoard() throws Exception {		
		BulletinBoardBean bean1 = null;
		String title = "Test";
		String posterFirstName = "FIRST";
		String posterLastName = "LAST";
		String content = "TestTest";
		Date createdOn = new Date();
		
		BulletinBoardBean bbb = new BulletinBoardBean();
		bbb.setID(1);
		bbb.setTitle(title);
		bbb.setPosterFirstName(posterFirstName);
		bbb.setPosterLastName(posterLastName);
		bbb.setContent(content);
		bbb.setCreatedOn(createdOn);
		bbb.setComments(null);
		BulletinBoardDAO bbDAO = TestDAOFactory.getTestInstance().getBulletinBoardDAO();	 
		bbDAO.addBulletinBoard(bbb);
		try {
			bean1 = action.getBulletinBoard(1);
		} catch (ITrustException e) {
			fail();
		}
		if (bean1 == null)
			fail();
		assertEquals(bean1.getID(), 1); // and just test that the right record came back
		
		BulletinBoardBean bean2 = null;
		try {
			bean2 = action.getBulletinBoard(1);
		} catch (ITrustException e) {
			assertNull(bean2);
		}

		BulletinBoardBean bean3 = null;
		try {
			bean3 =  action.getBulletinBoard(1);
		} catch (ITrustException e) {
			assertNull(bean3);
		}
		
	}

	public void testGetAllBulletinBoard() throws Exception {
		List<BulletinBoardBean> list = null;
		try {
			list = action.getAllBulletinBoards();
		} catch (ITrustException e) {
			fail();
		}
		if (list == null) {
			fail();
		} else {
			assertTrue(list.size() >= 0 );
		}
	}
	
	
}
