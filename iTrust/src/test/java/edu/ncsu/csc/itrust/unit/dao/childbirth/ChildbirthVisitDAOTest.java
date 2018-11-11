package edu.ncsu.csc.itrust.unit.dao.childbirth;

import junit.framework.TestCase;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Timestamp;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ChildbirthVisitDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsVisitDAO;
import edu.ncsu.csc.itrust.model.old.beans.ChildbirthVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.DeliveryRecordBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ChildbirthVisitDAOTest extends TestCase {
	ChildbirthVisitDAO childbirthVisitDAO = TestDAOFactory.getTestInstance().getChildbirthVisitDAO();
	TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
	}
		
	public void testAddChildbirthVisit() throws Exception {
		// define childbirth bean information
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		long visitID = 1;
		long patientID = 1;
		String preferredChildbirthMethod = "vaginal delivery";
		String drugs = "Pitocin";
		Timestamp scheduledDate = new Timestamp(System.currentTimeMillis());
		boolean preScheduled = false;
		List<DeliveryRecordBean> deliveryRecords = new ArrayList<DeliveryRecordBean>();
		
		// set delivery bean information
		DeliveryRecordBean deliveryRecordBean = new DeliveryRecordBean();
		deliveryRecordBean.setID(1);
		deliveryRecordBean.setPatientID(patientID);
		deliveryRecordBean.setChildbirthVisitID(visitID);
		deliveryRecordBean.setDeliveryDateTime(scheduledDate);
		deliveryRecordBean.setDeliveryMethod(preferredChildbirthMethod);
		deliveryRecords.add(deliveryRecordBean);
		
		// set childbirth bean information
		ChildbirthVisitBean cbvb = new ChildbirthVisitBean();
		cbvb.setVisitID(visitID);
		cbvb.setPatientID(patientID);	
		cbvb.setPreferredChildbirthMethod(preferredChildbirthMethod);
		cbvb.setDrugs(drugs);
		cbvb.setScheduledDate(scheduledDate);
		cbvb.setPreScheduled(preScheduled);
		cbvb.setDeliveryRecord(deliveryRecords);
		
		// add childbirth visit to the database
		childbirthVisitDAO.addChildbirthVisit(cbvb);
		childbirthVisitDAO.addDeliveryRecord(deliveryRecordBean);
		
		// test getAllChildbirthVisits method
		List<ChildbirthVisitBean> cbvList = childbirthVisitDAO.getAllChildbirthVisits(patientID);
		assertEquals(1, cbvList.size());
		ChildbirthVisitBean cbvb1 = cbvList.get(0);
		
		assertEquals(visitID, cbvb1.getVisitID());
		assertEquals(patientID, cbvb1.getPatientID());
		assertEquals(preferredChildbirthMethod, cbvb1.getPreferredChildbirthMethod());
		assertEquals(drugs, cbvb1.getDrugs());	
		assertEquals(dateFormat.format(scheduledDate), dateFormat.format(cbvb1.getScheduledDate()));
		assertEquals(preScheduled, cbvb1.isPreScheduled());
		assertEquals(deliveryRecords.get(0).getID(), cbvb1.getDeliveryRecord().get(0).getID());
		
		// test getChildbirthVisit method
		cbvb1 = childbirthVisitDAO.getChildbirthVisit(visitID);
		assertEquals(visitID, cbvb1.getVisitID());
		assertEquals(patientID, cbvb1.getPatientID());
		assertEquals(preferredChildbirthMethod, cbvb1.getPreferredChildbirthMethod());
		assertEquals(drugs, cbvb1.getDrugs());	
		assertEquals(dateFormat.format(scheduledDate), dateFormat.format(cbvb1.getScheduledDate()));
		assertEquals(preScheduled, cbvb1.isPreScheduled());
		assertEquals(deliveryRecords.get(0).getID(), cbvb1.getDeliveryRecord().get(0).getID());
	}
	
	public void testUpdateChildbirthVisit() throws Exception {
		// define childbirth bean information
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		long visitID = 1;
		long patientID = 1;
		String preferredChildbirthMethod = "vaginal delivery";
		String drugs = "Pitocin";
		Timestamp scheduledDate = new Timestamp(System.currentTimeMillis());
		boolean preScheduled = false;
		List<DeliveryRecordBean> deliveryRecords = new ArrayList<DeliveryRecordBean>();
		
		// set delivery bean information
		DeliveryRecordBean deliveryRecordBean = new DeliveryRecordBean();
		deliveryRecordBean.setID(1);
		deliveryRecordBean.setPatientID(patientID);
		deliveryRecordBean.setChildbirthVisitID(visitID);
		deliveryRecordBean.setDeliveryDateTime(scheduledDate);
		deliveryRecordBean.setDeliveryMethod(preferredChildbirthMethod);
		deliveryRecords.add(deliveryRecordBean);
		
		// set childbirth bean information
		ChildbirthVisitBean cbvb = new ChildbirthVisitBean();
		cbvb.setVisitID(visitID);
		cbvb.setPatientID(patientID);	
		cbvb.setPreferredChildbirthMethod(preferredChildbirthMethod);
		cbvb.setDrugs(drugs);
		cbvb.setScheduledDate(scheduledDate);
		cbvb.setPreScheduled(preScheduled);
		cbvb.setDeliveryRecord(deliveryRecords);
		
		// add childbirth visit to the database
		childbirthVisitDAO.addChildbirthVisit(cbvb);
		childbirthVisitDAO.addDeliveryRecord(deliveryRecordBean);
		
		// test updateDeliveryRecord method
		cbvb.setDrugs("test");
		childbirthVisitDAO.updateChildbirthVisit(cbvb);
		
		// test getChildbirthVisit method
		ChildbirthVisitBean cbvb1 = childbirthVisitDAO.getChildbirthVisit(visitID);
		assertEquals(visitID, cbvb1.getVisitID());
		assertEquals(patientID, cbvb1.getPatientID());
		assertEquals(preferredChildbirthMethod, cbvb1.getPreferredChildbirthMethod());
		assertEquals("test", cbvb1.getDrugs());	
		assertEquals(dateFormat.format(scheduledDate), dateFormat.format(cbvb1.getScheduledDate()));
		assertEquals(preScheduled, cbvb1.isPreScheduled());
		assertEquals(deliveryRecords.get(0).getID(), cbvb1.getDeliveryRecord().get(0).getID());
	}	
}
