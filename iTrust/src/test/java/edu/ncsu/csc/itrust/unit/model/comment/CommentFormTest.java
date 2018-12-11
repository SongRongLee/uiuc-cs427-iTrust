package edu.ncsu.csc.itrust.unit.model.comment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.CommentForm;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;

public class CommentFormTest extends TestCase {
	private Date today;

	@Override
	protected void setUp() throws Exception {
		today = new Date();
	}

	public void testForm() {
		CommentForm f = new CommentForm("","","","","");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		f.setBulletinBoardID("0");
		f.setPosterFirstName("First");
		f.setPosterLastName("Last");
		f.setText("test");
		f.setCreatedOn(dateFormat.format(today));
		
		assertEquals("0", f.getBulletinBoardID());
		assertEquals("First", f.getPosterFirstName());
		assertEquals("Last", f.getPosterLastName());
		assertEquals("test", f.getText());
		assertEquals(dateFormat.format(this.today), f.getCreatedOn());
	}
}
