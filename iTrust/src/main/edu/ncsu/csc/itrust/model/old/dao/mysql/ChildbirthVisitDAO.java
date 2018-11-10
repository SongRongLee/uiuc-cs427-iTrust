package edu.ncsu.csc.itrust.model.old.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.ChildbirthVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.ChildbirthVisitLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;

/**
 * Used for managing all static information related to an obstetrics visit record. 
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be
 * reflections of the database, that is, one DAO per table in the database (most
 * of the time). For more complex sets of queries, extra DAOs are added. DAOs
 * can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than
 * a factory. All DAOs should be accessed by DAOFactory (@see
 * {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 */
public class ChildbirthVisitDAO {
	private DAOFactory factory;
	private ChildbirthVisitLoader loader;

	/**
	 * The typical constructor.
	 * 
	 * @param factory
	 *            The {@link DAOFactory} associated with this DAO, which is used
	 *            for obtaining SQL connections, etc.
	 */
	public ChildbirthVisitDAO(DAOFactory factory) {
		this.factory = factory;
		this.loader = new ChildbirthVisitLoader();
	}

	/**
	 * Returns the childbirth visit information for a given ID
	 * 
	 * @param vid
	 *            The childbirth visit ID of the childbirth visit record to retrieve.
	 * @return A ChildbirthVisitBean.
	 * @throws DBException
	 */
	public ChildbirthVisitBean getChildbirthVisit(long vid) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM childbirthvisit WHERE ID = ?");
				) {
			ps.setLong(1, vid);
			ResultSet rs = ps.executeQuery();
			ChildbirthVisitBean cbVisit = rs.next() ? loader.loadSingle(rs) : null;
			rs.close();
			
			return cbVisit;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	/**
	 * Lists every childbirth visit record for a certain patient
	 * 
	 * @return A java.util.List of ChildbirthVisit Beans representing the records.
	 * @throws DBException
	 */
	public List<ChildbirthVisitBean> getAllChildbirthVisits(long pid) throws DBException {
		try (
				Connection conn = factory.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM childbirthvisit WHERE patientID = ? "
						+ "ORDER BY ScheduledDate DESC");
				) {
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			List<ChildbirthVisitBean> cbVisits = loader.loadList(rs);
			rs.close();
					
			return cbVisits;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * Add a childbirth visit
	 * 
	 * @param newVisit
	 *            The ChildbirthVisit bean representing the new information
	 * @throws DBException
	 */
	public void addChildbirthVisit(ChildbirthVisitBean newVisit) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = loader.loadParameters(conn.prepareStatement(
						"INSERT INTO childbirthvisit (PatientID, PreferredChildbirthMethod, Drugs, "
						+ "ScheduledDate, PreScheduled) VALUES (?, ?, ?, ?, ?)"),
						newVisit)) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * update a childbirth visit
	 * 
	 * @param newVisit
	 *            The ChildbirthVisit bean representing the new information
	 * @throws DBException
	 */
	public void updateChildbirthVisit(ChildbirthVisitBean newVisit) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = loader.loadParametersUpdate(conn.prepareStatement(
						"UPDATE childbirthvisit SET PatientID=?, PreferredChildbirthMethod=?,"
						+ "Drugs=?, ScheduledDate=?, PreScheduled=? WHERE ID=?"),
						newVisit)) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	

}
