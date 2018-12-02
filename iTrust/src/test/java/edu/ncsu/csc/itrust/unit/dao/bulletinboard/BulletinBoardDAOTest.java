package edu.ncsu.csc.itrust.unit.dao.bulletinboard;

import junit.framework.TestCase;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Timestamp;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.dao.mysql.BulletinBoardDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsVisitDAO;
import edu.ncsu.csc.itrust.model.old.beans.CommentBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class BulletinBoardDAOTest extends TestCase {
	BulletinBoardDAO bbDAO = TestDAOFactory.getTestInstance().getBulletinBoardDAO();
	TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
	}
		
	public void testGetComment() throws Exception {
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
		
		CommentBean cResult = bbDAO.getComment(1);

		assertEquals(bulletinBoardID, cResult.getBulletinBoardID());
		assertEquals(posterFirstName, cResult.getPosterFirstName());
		assertEquals(posterLastName, cResult.getPosterLastName());
		assertEquals(text, cResult.getText());
		assertEquals(dateFormat.format(createdOn), cResult.getCreatedOnString());
	}
	
	public void testGetAllComment() throws Exception {
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
		
		List<CommentBean> cResults = bbDAO.getAllComment(0);
		assertEquals(1, cResults.size());

		assertEquals(bulletinBoardID, cResults.get(0).getBulletinBoardID());
		assertEquals(posterFirstName, cResults.get(0).getPosterFirstName());
		assertEquals(posterLastName, cResults.get(0).getPosterLastName());
		assertEquals(text, cResults.get(0).getText());
		assertEquals(dateFormat.format(createdOn), cResults.get(0).getCreatedOnString());
	}
	
	public void testUpdateComment() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

		long bulletinBoardID = 0;
		String posterFirstName = "FIRST";
		String posterLastName = "LAST";
		String text = "HAHA";
		Date createdOn = new Date();
		
		CommentBean c = new CommentBean();
		c.setID(1);
		c.setBulletinBoardID(bulletinBoardID);
		c.setPosterFirstName(posterFirstName);
		c.setPosterLastName(posterLastName);
		c.setText(text);
		c.setCreatedOn(createdOn);
		
		bbDAO.addComment(c);
		
		c.setText("CHANGED");
		bbDAO.updateComment(c);
		CommentBean cResult = bbDAO.getComment(1);

		assertEquals(bulletinBoardID, cResult.getBulletinBoardID());
		assertEquals(posterFirstName, cResult.getPosterFirstName());
		assertEquals(posterLastName, cResult.getPosterLastName());
		assertEquals("CHANGED", cResult.getText());
		assertEquals(dateFormat.format(createdOn), cResult.getCreatedOnString());
	}
	
	public void testDeleteComment() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

		long bulletinBoardID = 0;
		String posterFirstName = "FIRST";
		String posterLastName = "LAST";
		String text = "HAHA";
		Date createdOn = new Date();
		
		CommentBean c = new CommentBean();
		c.setID(1);
		c.setBulletinBoardID(bulletinBoardID);
		c.setPosterFirstName(posterFirstName);
		c.setPosterLastName(posterLastName);
		c.setText(text);
		c.setCreatedOn(createdOn);
		
		bbDAO.addComment(c);
		bbDAO.deleteComment(1);
		CommentBean cResult = bbDAO.getComment(1);

		assertEquals(null, cResult);
	}
}
