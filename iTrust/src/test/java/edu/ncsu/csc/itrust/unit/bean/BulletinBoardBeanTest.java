package edu.ncsu.csc.itrust.unit.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.model.old.beans.BulletinBoardBean;
import edu.ncsu.csc.itrust.model.old.beans.CommentBean;

public class BulletinBoardBeanTest extends TestCase {
	@Override
	protected void setUp() throws Exception {

	}

	public void testBean() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

		long ID = 1;
		String title = "Test";
		String posterFirstName = "First";
		String posterLastName = "Last";
		String content = "Test";
		Date createdOn = new Date();
		List<CommentBean> comments = null;
		
		BulletinBoardBean bbb = new BulletinBoardBean();
		bbb.setID(ID);
		bbb.setTitle(title);
		bbb.setPosterFirstName(posterFirstName);
		bbb.setPosterLastName(posterLastName);
		bbb.setContent(content);
		bbb.setCreatedOn(createdOn);
		bbb.setComments(comments);
				
		assertEquals(ID, bbb.getID());
		assertEquals(title, bbb.getTitle());
		assertEquals(posterFirstName, bbb.getPosterFirstName());
		assertEquals(posterLastName, bbb.getPosterLastName());
		assertEquals(content, bbb.getContent());
		assertEquals(createdOn, bbb.getCreatedOn());
		assertEquals(dateFormat.format(createdOn), bbb.getCreatedOnString());
		assertEquals(null, bbb.getComments());
	}
	
	public void testCompareTo() throws Exception {
		BulletinBoardBean bbb1 = new BulletinBoardBean();
		bbb1.setID(1);
		BulletinBoardBean bbb2 = new BulletinBoardBean();
		bbb2.setID(2);
		assertEquals(0, bbb1.compareTo(bbb1));
		assertEquals(1, bbb1.compareTo(bbb2));
	}

}