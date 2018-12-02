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

import edu.ncsu.csc.itrust.model.old.beans.CommentBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.CommentLoader;
import edu.ncsu.csc.itrust.model.old.beans.loaders.ObstetricsVisitLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class CommentLoaderTest extends TestCase {
	private IMocksControl ctrl;
	private ResultSet rs;
	private CommentLoader load;
	private DAOFactory factory;

	@Override
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new CommentLoader();
		factory = TestDAOFactory.getTestInstance();
	}

	@Test
	public void testLoadList() throws SQLException {
		List<CommentBean> l = load.loadList(rs);
		assertEquals(0, l.size());
	}
	
	@Test
	public void testLoadSingle() {

		try {
			expect(rs.getInt("ID")).andReturn(1234321);
			expect(rs.getInt("BulletinBoardID")).andReturn(4321234);
			expect(rs.getString("PosterFirstName")).andReturn("First");
			expect(rs.getString("PosterLastName")).andReturn("Last");
			expect(rs.getString("Text")).andReturn("test");
			expect(rs.getTimestamp("CreatedOn")).andReturn(new java.sql.Timestamp(new Date(0).getTime()));
			ctrl.replay();

			CommentBean r = load.loadSingle(rs);
			assertEquals(1234321, r.getID());
			assertEquals(4321234, r.getBulletinBoardID());
			assertEquals("First", r.getPosterFirstName());
			assertEquals("Last", r.getPosterLastName());
			assertEquals("test", r.getText());
			assertEquals(new java.sql.Timestamp(new Date(0).getTime()), r.getCreatedOn());

		} catch (SQLException e) {
			// TODO
		}
	}
	
	@Test
	public void testLoadParameters() {
		try {
			long ID = 1;
			long bulletinBoardID = 0;
			String posterFirstName = "FIRST";
			String posterLastName = "LAST";
			String text = "HAHA";
			Date createdOn = new Date(0);
			
			CommentBean c = new CommentBean();
			c.setID(ID);
			c.setBulletinBoardID(bulletinBoardID);
			c.setPosterFirstName(posterFirstName);
			c.setPosterLastName(posterLastName);
			c.setText(text);
			c.setCreatedOn(createdOn);
			
			Connection conn = factory.getConnection();
			
			PreparedStatement stmt = load.loadParameters(conn.prepareStatement(
					"INSERT INTO comments (BulletinBoardID, PosterFirstName, PosterLastName, Text,"
					+ " CreatedOn) VALUES (?, ?, ?, ?, ?)"),
					c);
			
			String strSTMT = "INSERT INTO comments (BulletinBoardID, PosterFirstName, PosterLastName, Text,"
					+ " CreatedOn) VALUES (0, 'FIRST', 'LAST', 'HAHA', '1969-12-31 18:00:00')";
			
			boolean contains = (stmt.toString()).contains(strSTMT);
			assertEquals(true, contains);
					
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testLoadParametersUpdate() {
		try {
			long ID = 1;
			long bulletinBoardID = 0;
			String posterFirstName = "FIRST";
			String posterLastName = "LAST";
			String text = "HAHA";
			Date createdOn = new Date(0);
			
			CommentBean c = new CommentBean();
			c.setID(ID);
			c.setBulletinBoardID(bulletinBoardID);
			c.setPosterFirstName(posterFirstName);
			c.setPosterLastName(posterLastName);
			c.setText(text);
			c.setCreatedOn(createdOn);
			
			Connection conn = factory.getConnection();
			
			PreparedStatement stmt = load.loadParametersUpdate(conn.prepareStatement(
					"UPDATE comments SET BulletinBoardID=?, PosterFirstName=?, PosterLastName=?, Text=?,"
					+ " CreatedOn=? WHERE ID=?"),
					c);
			
			String strSTMT = "UPDATE comments SET BulletinBoardID=0, PosterFirstName='FIRST', PosterLastName='LAST', Text='HAHA',"
					+ " CreatedOn='1969-12-31 18:00:00' WHERE ID=1";
			
			boolean contains = (stmt.toString()).contains(strSTMT);
			assertEquals(true, contains);
					
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
}