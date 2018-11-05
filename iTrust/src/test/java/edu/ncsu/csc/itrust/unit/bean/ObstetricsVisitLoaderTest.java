package edu.ncsu.csc.itrust.unit.bean;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.easymock.classextension.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.ObstetricsVisitLoader;
import junit.framework.TestCase;

public class ObstetricsVisitLoaderTest extends TestCase {
	private IMocksControl ctrl;
	private ResultSet rs;
	private ObstetricsVisitLoader load;

	@Override
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new ObstetricsVisitLoader();
	}

	@Test
	public void testLoadList() throws SQLException {
		List<ObstetricsVisitBean> l = load.loadList(rs);
		assertEquals(0, l.size());
	}
	
	@Test
	public void testLoadSingle() {

		try {
			// this just sets the value for a method call (kinda hard coding I
			// assume)
			expect(rs.getInt("ID")).andReturn(1234321);
			expect(rs.getInt("PatientID")).andReturn(4321234);
			expect(rs.getTimestamp("scheduledDate")).andReturn(new java.sql.Timestamp(new Date(0).getTime()));
			expect(rs.getTimestamp("createdDate")).andReturn(new java.sql.Timestamp(new Date(0).getTime()));
			expect(rs.getString("numWeeks")).andReturn("17");
			expect(rs.getFloat("weight")).andReturn((float)150.0);
			expect(rs.getString("bloodPressure")).andReturn("120/50");
			expect(rs.getInt("FHR")).andReturn(60);
			expect(rs.getInt("numChildren")).andReturn(4);
			expect(rs.getBoolean("LLP")).andReturn(false);
			ctrl.replay();

			ObstetricsVisitBean r = load.loadSingle(rs);
			assertEquals(1234321, r.getID());
			assertEquals(4321234, r.getPatientID());
			assertEquals(new java.sql.Timestamp(new Date(0).getTime()), r.getScheduledDate());
			assertEquals(new java.sql.Timestamp(new Date(0).getTime()), r.getCreatedDate());
			assertEquals("17", r.getNumWeeks());
			assertEquals((float)150.0, r.getWeight());
			assertEquals("120/50", r.getBloodPressure());
			assertEquals(60, r.getFHR());
			assertEquals(4, r.getNumChildren());
			assertEquals((Boolean)false, r.getLLP());

		} catch (SQLException e) {
			// TODO
		}
	}
	
	@Test
	public void testLoadParameters() {
		try {

			load.loadParameters(null, null);
			fail();
		} catch (Exception e) {
		}
	}
}