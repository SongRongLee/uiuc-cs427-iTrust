package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import edu.ncsu.csc.itrust.model.old.validate.BulletinBoardValidator;
import edu.ncsu.csc.itrust.model.old.validate.ValidationFormat;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.forms.BulletinBoardForm;

public class BulletinBoardValidatorTest extends TestCase {
	
	BulletinBoardValidator validator = new BulletinBoardValidator();

	@Override
	protected void setUp() throws Exception {
		
	}
		
	public void testSubmitComment() throws Exception {
		BulletinBoardForm f = new BulletinBoardForm("","","","");
		f.setTitle("Test");
		f.setPosterFirstName("First");
		f.setPosterLastName("Last");
		f.setContent("TestTest");
		
		Exception ex = null;
		try {
            validator.validate(f);
        } catch (Exception e) {
            ex = e;
        }
		assertEquals(null, ex);
	}
	
	public void testSubmitCommentAllErrors() throws Exception {
		BulletinBoardForm f = new BulletinBoardForm("","","","");
		Exception ex = null;
		try {
            validator.validate(f);
        } catch (FormValidationException e) {
            ex = e;
            assertEquals("Title: " + ValidationFormat.QUESTION.getDescription(), e.getErrorList().get(0));
            assertEquals("PosterFirstName: " + ValidationFormat.NAME.getDescription(), e.getErrorList().get(1));
    		assertEquals("PosterLastName: " + ValidationFormat.NAME.getDescription(), e.getErrorList().get(2));
    		assertEquals("Content: " + ValidationFormat.MESSAGES_BODY.getDescription(), e.getErrorList().get(3));
        }
	}
}
