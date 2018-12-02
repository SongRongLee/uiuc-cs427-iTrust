package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import edu.ncsu.csc.itrust.action.AddBulletinBoardAction;
import edu.ncsu.csc.itrust.action.AddChildbirthVisitAction;
import edu.ncsu.csc.itrust.action.AddObstetricsAction;
import edu.ncsu.csc.itrust.action.AddPatientAction;
import edu.ncsu.csc.itrust.action.EditBulletinBoardAction;
import edu.ncsu.csc.itrust.action.EditChildbirthVisitAction;
import edu.ncsu.csc.itrust.action.ViewBulletinBoardAction;
import edu.ncsu.csc.itrust.action.ViewChildbirthVisitAction;
import edu.ncsu.csc.itrust.action.ViewObstetricsAction;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.ChildbirthVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.CommentBean;
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

public class EditBulletinBoardActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditBulletinBoardAction action;

	/**
	 * Sets up defaults
	 */
	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.transactionLog();
		gen.ucCustom();
		action = new EditBulletinBoardAction(factory, 9000000012L, "1");
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
	public void testEditComment() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		BulletinBoardDAO bbDAO = factory.getBulletinBoardDAO();

		long bulletinBoardID = 0;
		String posterFirstName = "FIRST";
		String posterLastName = "LAST";
		String text = "HAHA";
		Date createdOn = new Date();
		
		CommentBean c = new CommentBean();
		c.setBulletinBoardID(bulletinBoardID);
		c.setPosterFirstName(posterFirstName);
		c.setPosterLastName(posterLastName);
		c.setText(text);
		c.setCreatedOn(createdOn);
		
		bbDAO.addComment(c);
		c.setText("CHANGED");
		
		action.editComment(c, "1", "0", posterFirstName, posterLastName, "CHANGED", "06/25/2015 10:10");
		CommentBean cResult = bbDAO.getComment(1);
		assertEquals(cResult.getID(), 1);
		assertEquals(cResult.getText(), "CHANGED");

	}
	
	@Test
	public void testGetComment() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		BulletinBoardDAO bbDAO = factory.getBulletinBoardDAO();

		long bulletinBoardID = 0;
		String posterFirstName = "FIRST";
		String posterLastName = "LAST";
		String text = "HAHA";
		Date createdOn = new Date();
		
		CommentBean c = new CommentBean();
		c.setBulletinBoardID(bulletinBoardID);
		c.setPosterFirstName(posterFirstName);
		c.setPosterLastName(posterLastName);
		c.setText(text);
		c.setCreatedOn(createdOn);
		
		bbDAO.addComment(c);
		
		CommentBean cResult = action.getComment(1);
		assertEquals(cResult.getID(), 1);
	}
	
	@Test
	public void testDeleteComment() throws Exception {
		BulletinBoardDAO bbDAO = factory.getBulletinBoardDAO();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

		long bulletinBoardID = 0;
		String posterFirstName = "FIRST";
		String posterLastName = "LAST";
		String text = "HAHA";
		Date createdOn = new Date();
		
		CommentBean c = new CommentBean();
		c.setBulletinBoardID(bulletinBoardID);
		c.setPosterFirstName(posterFirstName);
		c.setPosterLastName(posterLastName);
		c.setText(text);
		c.setCreatedOn(createdOn);

		bbDAO.addComment(c);
		action.deleteComment(1);
		assertEquals(null, action.getComment(1));
	}
}
