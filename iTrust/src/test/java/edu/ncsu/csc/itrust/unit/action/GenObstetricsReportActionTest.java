package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.GenObstetricsReportAction;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class GenObstetricsReportActionTest {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private GenObstetricsReportAction action;
	private DataSource ds;
	
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.patient1();
		gen.uc95();
		ds = ConverterDAO.getDataSource();
		action = new GenObstetricsReportAction(factory, 9000000003L, "1", ds);
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
		List<ObstetricsVisitBean> obList = action.getSortedObstetricsVisits();
		assertEquals(1, obList.size());
	}
	
	@Test
	public void testGetObstetricsVisit() throws Exception {

	}
	
	@Test
	public void testGetAllObstetrics() {
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

}
