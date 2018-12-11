package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO;
import edu.ncsu.csc.itrust.model.old.validate.CommentValidator;
import edu.ncsu.csc.itrust.model.old.validate.ObstetricsValidator;
import edu.ncsu.csc.itrust.model.old.validate.ValidationFormat;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.CommentForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsVisitForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.PregnancyForm;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class CommentValidatorTest extends TestCase {
	
	CommentValidator validator = new CommentValidator();
	private Date today;

	@Override
	protected void setUp() throws Exception {
		today = new Date();
	}
		
	public void testSubmitComment() throws Exception {
		CommentForm f = new CommentForm("","","","","");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		f.setBulletinBoardID("0");
		f.setPosterFirstName("First");
		f.setPosterLastName("Last");
		f.setText("test");
		f.setCreatedOn(dateFormat.format(today));
		
		Exception ex = null;
		try {
            validator.validate(f);
        } catch (Exception e) {
            ex = e;
        }
		assertEquals(null, ex);
	}
	
	public void testSubmitCommentAllErrors() throws Exception {
		CommentForm f = new CommentForm("abc","","","","abc");
		Exception ex = null;
		try {
            validator.validate(f);
        } catch (FormValidationException e) {
            ex = e;
            assertEquals("bulletinBoardID: " + ValidationFormat.MID.getDescription(), e.getErrorList().get(0));
    		assertEquals("posterFirstName: " + ValidationFormat.NAME.getDescription(), e.getErrorList().get(1));
    		assertEquals("posterLastName: " + ValidationFormat.NAME.getDescription(), e.getErrorList().get(2));
    		assertEquals("text: " + ValidationFormat.MESSAGES_BODY.getDescription(), e.getErrorList().get(3));
    		assertEquals("createdOn: " + ValidationFormat.DATETIMESTAMP.getDescription(), e.getErrorList().get(4));
        }
	}
}
