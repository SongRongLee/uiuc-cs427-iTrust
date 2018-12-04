package edu.ncsu.csc.itrust.unit.bean;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.easymock.classextension.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.model.old.beans.BulletinBoardBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.BulletinBoardLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class BulletinBoardLoaderTest extends TestCase {
	private IMocksControl ctrl;
	private ResultSet rs;
	private BulletinBoardLoader loader;
	private DAOFactory factory;

	@Override
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		loader = new BulletinBoardLoader();
		factory = TestDAOFactory.getTestInstance();
	}

	@Test
	public void testLoadList() throws SQLException {
		List<BulletinBoardBean> list = loader.loadList(rs);
		assertEquals(0, list.size());
	}
	
	@Test
	public void testLoadSingle() {

		try {
			expect(rs.getInt("ID")).andReturn(1234321);
			expect(rs.getString("Title")).andReturn("Test");
			expect(rs.getString("PosterFirstName")).andReturn("First");
			expect(rs.getString("PosterLastName")).andReturn("Last");
			expect(rs.getString("Content")).andReturn("TestTestTest");
			expect(rs.getTimestamp("CreatedOn")).andReturn(new java.sql.Timestamp(new Date(0).getTime()));
			ctrl.replay();

			BulletinBoardBean bbb = loader.loadSingle(rs);
			assertEquals(1234321, bbb.getID());
			assertEquals("Test", bbb.getTitle());
			assertEquals("First", bbb.getPosterFirstName());
			assertEquals("Last", bbb.getPosterLastName());
			assertEquals("TestTestTest", bbb.getContent());
			assertEquals(new java.sql.Timestamp(new Date(0).getTime()), bbb.getCreatedOn());

		} catch (SQLException e) {
			// TODO
		}
	}
	
	@Test
	public void testLoadParameters() {
		try {
			long ID = 1;
			String title = "Test";
			String posterFirstName = "FIRST";
			String posterLastName = "LAST";
			String content = "TestTestTest";
			Date createdOn = new Date(0);
			
			BulletinBoardBean bbb = new BulletinBoardBean();
			bbb.setID(ID);
			bbb.setTitle(title);
			bbb.setPosterFirstName(posterFirstName);
			bbb.setPosterLastName(posterLastName);
			bbb.setContent(content);
			bbb.setCreatedOn(createdOn);
			System.out.println(bbb.getCreatedOnString());
			Connection conn = factory.getConnection();
			
			PreparedStatement stmt = loader.loadParameters(conn.prepareStatement(
					"INSERT INTO bulletin (Title, PosterFirstName, PosterLastName, Content,"
					+ " CreatedOn) VALUES (?, ?, ?, ?, ?)"),
					bbb);
			
			String strSTMT = "INSERT INTO bulletin (Title, PosterFirstName, PosterLastName, Content,"
					+ " CreatedOn) VALUES ('Test', 'FIRST', 'LAST', 'TestTestTest', '1969-12-31 18:00:00')";
			
			boolean contains = (stmt.toString()).contains(strSTMT);
			System.out.println(stmt.toString());
			System.out.println(strSTMT);
			assertEquals(true, contains);
					
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testLoadParametersUpdate() {
		try {
			long ID = 1;
			String title = "Test112";
			String posterFirstName = "FIRST112";
			String posterLastName = "LAST112";
			String content = "TestTestTest112";
			Date createdOn = new Date(0);
						
			BulletinBoardBean bbb = new BulletinBoardBean();
			bbb.setID(ID);
			bbb.setTitle(title);
			bbb.setPosterFirstName(posterFirstName);
			bbb.setPosterLastName(posterLastName);
			bbb.setContent(content);
			bbb.setCreatedOn(createdOn);
			
			Connection conn = factory.getConnection();
			
			PreparedStatement stmt = loader.loadParametersUpdate(conn.prepareStatement(
					"UPDATE bulletin SET Title=?, PosterFirstName=?, PosterLastName=?, Content=?,"
					+ " CreatedOn=? WHERE ID=?"),
					bbb);
			
			String strSTMT = "UPDATE bulletin SET Title='Test112', PosterFirstName='FIRST112', PosterLastName='LAST112', Content='TestTestTest112',"
					+ " CreatedOn='1969-12-31 18:00:00' WHERE ID=1";
			
			boolean contains = (stmt.toString()).contains(strSTMT);
			assertEquals(true, contains);
					
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
}
