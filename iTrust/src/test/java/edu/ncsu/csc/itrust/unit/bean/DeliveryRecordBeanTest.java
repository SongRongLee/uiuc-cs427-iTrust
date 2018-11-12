package edu.ncsu.csc.itrust.unit.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.model.old.beans.DeliveryRecordBean;
import java.sql.Timestamp;

public class DeliveryRecordBeanTest extends TestCase {
	
	@Override
	protected void setUp() throws Exception {
		
	}

	public void testBean() {

		long ID = 1;
		long patientID = 1;
		long childbirthVisitID = 1;
		Timestamp deliveryDateTime = new Timestamp(System.currentTimeMillis());
		String deliveryMethod = "caesarean section";
		
		DeliveryRecordBean drb = new DeliveryRecordBean();
		drb.setID(ID);
		drb.setPatientID(patientID);
		drb.setChildbirthVisitID(childbirthVisitID);
		drb.setDeliveryDateTime(deliveryDateTime);
		drb.setDeliveryMethod(deliveryMethod);
				
		assertEquals(ID, drb.getID());
		assertEquals(patientID, drb.getPatientID());
		assertEquals(childbirthVisitID, drb.getChildbirthVisitID());
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
		assertEquals(dateFormat.format(deliveryDateTime), dateFormat.format(drb.getDeliveryDateTime()));
		assertEquals(dateFormat.format(deliveryDateTime), drb.getDeliveryDateTimeString());
		assertEquals(deliveryMethod, drb.getDeliveryMethod());
		
	}
	
	public void testCompareTo() throws Exception {
		DeliveryRecordBean drb1 = new DeliveryRecordBean();
		drb1.setID(1);
		DeliveryRecordBean drb2 = new DeliveryRecordBean();
		drb2.setID(2);
		assertEquals(0, drb1.compareTo(drb1));
		assertEquals(1, drb1.compareTo(drb2));
		
	}

}
