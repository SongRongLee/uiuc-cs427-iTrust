package edu.ncsu.csc.itrust.unit.model.delivery;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.model.old.beans.forms.DeliveryRecordForm;

public class DeliveryRecordFormTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		
	}

	public void testForm() {
		// the dateFormat still passes with ("MM/dd/yyy")
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
		DeliveryRecordForm drf = new DeliveryRecordForm("", "", "", "");
		// the form does not include the (record) ID
		String patientID = "17";
		String childbirthVisitID = "17";
		String deliveryDateTime = dateFormat.format(new Timestamp(System.currentTimeMillis()));
		String deliveryMethod = "caesarean section";
		
		drf.setPatientID(patientID);	
		drf.setChildbirthVisitID(childbirthVisitID);
		drf.setDeliveryDateTime(deliveryDateTime);
		drf.setDeliveryMethod(deliveryMethod);
		
		assertEquals(patientID, drf.getPatientID());
		assertEquals(childbirthVisitID, drf.getChildbirthVisitID());
		assertEquals(deliveryDateTime, drf.getDeliveryDateTime());
		assertEquals(deliveryMethod, drf.getDeliveryMethod());
		
	}
}
