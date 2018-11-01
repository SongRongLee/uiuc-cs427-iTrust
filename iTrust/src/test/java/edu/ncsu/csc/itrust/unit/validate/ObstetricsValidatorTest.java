package edu.ncsu.csc.itrust.unit.validate;

import junit.framework.TestCase;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO;
import edu.ncsu.csc.itrust.model.old.validate.ObstetricsValidator;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ObstetricsValidatorTest extends TestCase {
	
	ObstetricsValidator validator = ObstetricsValidator();

		
	public void testSubmitObstetric() throws Exception {
		ObstetricsForm o = new ObstetricsForm("","","");
		o.setPatientID("1");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		o.setLMP(dateFormat.format(this.today));
		o.setCreated_on(dateFormat.format(this.today));
		
		Exception ex;
		try {
            validator.validate(p);
        } catch (Exception e) {
            ex = e;
        }
		assertEquals(null, ex);
	}
	
	public void testSubmitPregnancy() throws Exception {
		PregnancyForm p = new PregnancyForm("","","","","","","","");
		p.setPatientID("1");
		p.setWeight_gain("1");
		p.setNum_children("1");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		p.setDate_delivery(dateFormat.format(this.today));
		p.setNum_weeks_pregnant("1");
		p.setNum_hours_labor("1");
		p.setDelivery_type("caesarean section");
		p.setYOC("2018");
		
		Exception ex;
		try {
            validator.validatePregnancy(p);
        } catch (Exception e) {
            ex = e;
        }
		assertEquals(null, ex);
	}
}
