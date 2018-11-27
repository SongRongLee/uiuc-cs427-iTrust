package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.GenObstetricsReportAction;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.icdcode.ICDCode;
import edu.ncsu.csc.itrust.model.old.beans.AllergyBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsVisitDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class GenObstetricsReportActionTest {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private GenObstetricsReportAction action;
	private DataSource ds;
	private ObstetricsVisitDAO obstetricsVisitDAO;
	private ObstetricsVisitBean obTrue = new ObstetricsVisitBean();
	private ObstetricsVisitBean obFalse = new ObstetricsVisitBean();
	
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.patient1();
		gen.uc95();
		ds = ConverterDAO.getDataSource();
		action = new GenObstetricsReportAction(factory, 9000000003L, "1", ds);
		
		obTrue.setPatientID(1);
		obTrue.setScheduledDate(new Timestamp(new Date(0).getTime()));
		obTrue.setCreatedDate(new Timestamp(new Date(0).getTime()));
		obTrue.setNumWeeks("17");
		obTrue.setWeight((float)200);
		obTrue.setBloodPressure("160/100");
		obTrue.setFHR(60);
		obTrue.setNumChildren(4);
		obTrue.setLLP((Boolean)false);
		obstetricsVisitDAO = new ObstetricsVisitDAO(factory);
		obstetricsVisitDAO.addObstetricsVisit(obTrue);
		
	}
	
	@Test
	public void testGetPatient() {
		PatientBean bean1 = null;
		try {
			bean1 = action.getPatient();
		} catch (ITrustException e) {
			fail();
		}
		if (bean1 == null)
			fail();
		assertEquals(bean1.getMID(), 1L); // and just test that the right record came back
		
		PatientBean bean2 = null;
		try {
			bean2 = action.getPatient();
		} catch (ITrustException e) {
			assertNull(bean2);
		}

		PatientBean bean3 = null;
		try {
			bean3 = action.getPatient();
		} catch (ITrustException e) {
			assertNull(bean3);
		}
	}
	
	@Test
	public void testGetSortedObstetricsVisits() throws Exception {
		List<ObstetricsVisitBean> list = null;
		try {
			list = action.getSortedObstetricsVisits();
		} catch (ITrustException e) {
			fail();
		}
		if (list == null) {
			fail();
		} else {
			assertTrue(list.size() > 0 );
		}
	}
	
	/*@Test
	public void testGetObstetricsVisit() throws Exception {

	}*/
	
	
	@Test
	public void testGetAllObstetrics() throws Exception {
		List<ObstetricsBean> list = null;
		try {
			list = action.getAllObstetrics();
		} catch (ITrustException e) {
			fail();
		}
		if (list == null) {
			fail();
		} else {
			assertTrue(list.size() > 0 );
		}
	}
	
	@Test
	public void testAllPatientConditions() throws Exception {

		assertTrue(action.isObstericsPatient());
		assertTrue(action.getRHFlag());
		assertTrue(action.isHighPotentialMiscarriage());
		assertTrue(action.isHyperemesisGravidarum());
		assertTrue(action.isHypothyroidism());
		
	}
	
	@Test
	public void testAllPatientLists() throws Exception {
		List<ICDCode> diabetesList = null;
		List<ICDCode> chronicList = null;
		List<ICDCode> cancerList = null;
		List<ICDCode> stdList = null;
		List<AllergyBean> allergyList = null;
		
		try {
			diabetesList = action.getDiabetes();
			chronicList = action.getChronicIllness();
			cancerList = action.getCancers();
			stdList = action.getSTDs();
			allergyList = action.getAllergies();
		} catch (ITrustException e) {
			fail();
		}
		if (diabetesList == null) {
			fail();
		} 
		if (chronicList == null) {
			fail();
		} 
		if (cancerList == null) {
			fail();
		} 
		if (stdList == null) {
			fail();
		} 
		if (allergyList == null) {
			fail();
		} 
			
		assertTrue(diabetesList.size() > 0 );
		assertTrue(chronicList.size() > 0 );
		assertTrue(cancerList.size() > 0 );
		assertTrue(stdList.size() > 0 );
		assertTrue(allergyList.size() > 0 );
	}
	
	@Test
	public void testAllObstetricsVisitConditionsTrue() throws Exception {
		
		long trueVid = 1;
		
		assertTrue(action.isHighBloodPressure(trueVid));
		//assertTrue(action.isAdvancedMaternalAge(trueVid));
		assertTrue(action.isAbormalHeartRate(trueVid));
		assertTrue(action.isAtypicalWeightChange(trueVid));

	}
	
	
	
	
}
