package edu.ncsu.csc.itrust.unit.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.model.old.beans.CommentBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;
import java.sql.Timestamp;

public class CommentBeanTest extends TestCase {
	@Override
	protected void setUp() throws Exception {

	}

	public void testBean() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

		long ID = 1;
		long bulletinBoardID = 0;
		String posterFirstName = "FIRST";
		String posterLastName = "LAST";
		String text = "HAHA";
		Date createdOn = new Date();
		
		CommentBean c = new CommentBean();
		c.setID(ID);
		c.setBulletinBoardID(bulletinBoardID);
		c.setPosterFirstName(posterFirstName);
		c.setPosterLastName(posterLastName);
		c.setText(text);
		c.setCreatedOn(createdOn);
		
		assertEquals(ID, c.getID());
		assertEquals(bulletinBoardID, c.getBulletinBoardID());
		assertEquals(posterFirstName, c.getPosterFirstName());
		assertEquals(posterLastName, c.getPosterLastName());
		assertEquals(text, c.getText());
		assertEquals(createdOn, c.getCreatedOn());
		assertEquals(dateFormat.format(createdOn), c.getCreatedOnString());
	}
	
	public void testCompareTo() throws Exception {
		CommentBean c1 = new CommentBean();
		c1.setID(1);
		CommentBean c2 = new CommentBean();
		c2.setID(2);
		assertEquals(0, c1.compareTo(c1));
		assertEquals(1, c1.compareTo(c2));
	}

}
