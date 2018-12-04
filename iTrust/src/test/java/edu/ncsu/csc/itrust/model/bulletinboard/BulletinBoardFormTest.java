package edu.ncsu.csc.itrust.model.bulletinboard;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.model.old.beans.forms.BulletinBoardForm;

public class BulletinBoardFormTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		
	}

	public void testForm() {
		BulletinBoardForm f = new BulletinBoardForm("","","","");
		f.setTitle("Test");
		f.setPosterFirstName("First");
		f.setPosterLastName("Last");
		f.setContent("TestTest");
		
		assertEquals("Test", f.getTitle());
		assertEquals("First", f.getPosterFirstName());
		assertEquals("Last", f.getPosterLastName());
		assertEquals("TestTest", f.getContent());
	}
}
