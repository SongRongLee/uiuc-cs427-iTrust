package edu.ncsu.csc.itrust.model.old.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.BulletinBoardBean;
import edu.ncsu.csc.itrust.model.old.beans.CommentBean;
import edu.ncsu.csc.itrust.model.old.beans.FetusBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.BulletinBoardLoader;
import edu.ncsu.csc.itrust.model.old.beans.loaders.CommentLoader;
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
public class BulletinBoardDAO {
	private DAOFactory factory;
	private BulletinBoardLoader bulletinBoardLoader;
	private CommentLoader commentLoader;

	/**
	 * The typical constructor.
	 * 
	 * @param factory
	 *            The {@link DAOFactory} associated with this DAO, which is used
	 *            for obtaining SQL connections, etc.
	 */
	public BulletinBoardDAO(DAOFactory factory) {
		this.factory = factory;
		this.bulletinBoardLoader = new BulletinBoardLoader();
		this.commentLoader = new CommentLoader();
	}

	/**
	 * Returns the bulletin board and the comments for a given ID
	 * 
	 * @param bid
	 *            The bulletin board ID of the bulletin board record to retrieve.
	 * @return A BulletinBoardBean.
	 * @throws DBException
	 */
	public BulletinBoardBean getBulletinBoard(long bid) throws DBException {
		
	}

	/**
	 * Lists every bulletin board
	 * 
	 * @return A java.util.List of BulletinBoard Beans representing the records.
	 * @throws DBException
	 */
	public List<BulletinBoardBean> getAllBulletinBoards() throws DBException {
		
	}
	
	/**
	 * Returns the comment for a given ID
	 * 
	 * @param cid
	 *            The comment ID to retrieve.
	 * @return A CommentBean.
	 * @throws DBException
	 */
	public CommentBean getComment(long cid) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM comments WHERE ID = ?");
				) {
			ps.setLong(1, cid);
			ResultSet rs = ps.executeQuery();
			CommentBean cb = rs.next() ? commentLoader.loadSingle(rs) : null;
			rs.close();
						
			return cb;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * Lists every comment for a certain bulletin board
	 * 
	 * @return A java.util.List of CommentBean representing the comments.
	 * @throws DBException
	 */
	public List<CommentBean> getAllComment(long bid) throws DBException {
		try (
				Connection conn = factory.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM comments WHERE BulletinBoardID = ? ORDER BY CreatedOn DESC");
				) {
			ps.setLong(1, bid);
			ResultSet rs = ps.executeQuery();
			List<CommentBean> comments = commentLoader.loadList(rs);
			rs.close();
			
			return comments;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * Add a bulletin board
	 * 
	 * @param newBulletinBoard
	 *            The BulletinBoard bean representing the new information
	 * @throws DBException
	 */
	public long addBulletinBoard(BulletinBoardBean newBulletinBoard) throws DBException {

	}
	
	/**
	 * Add a comment
	 * 
	 * @param newComment
	 *            The Comment bean representing the new comment
	 * @throws DBException
	 */
	public long addComment(CommentBean newComment) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = commentLoader.loadParameters(conn.prepareStatement(
						"INSERT INTO comments (BulletinBoardID, PosterFirstName, PosterLastName, "
						+ "Text, CreatedOn) VALUES (?, ?, ?, ?, ?)"),
						newComment)) {
			stmt.executeUpdate();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * update a bulletin board
	 * 
	 * @param newBulletinBoard
	 *            The BulletinBoard bean representing the new information
	 * @throws DBException
	 */
	public void updateBulletinBoard(BulletinBoardBean newBulletinBoard) throws DBException {
		
	}
	
	/**
	 * update a comment
	 * 
	 * @param newComment
	 *            The Comment bean representing the new comment
	 * @throws DBException
	 */
	public void updateComment(CommentBean newComment) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = commentLoader.loadParametersUpdate(conn.prepareStatement(
						"UPDATE comments SET BulletinBoardID=?, PosterFirstName=?, PosterLastName=?, Text=?,"
						+ "CreatedOn=? WHERE ID=?"),
						newComment)) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * Remove a bullentin board from the database
	 * 
	 * @param bid
	 *            The The bulletin board ID of the bulletin board record to delete.
	 * @return true if removed successfully, false if not in list
	 */
	public boolean deleteBulletinBoard(long bid) throws DBException {

	}
	
	/**
	 * Remove a comment from the database
	 * 
	 * @param cid
	 *            The The comment ID of the comment to delete.
	 * @return true if removed successfully, false if not in list
	 */
	public boolean deleteComment(long cid) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"DELETE FROM comments WHERE ID=?")) {
			stmt.setLong(1, cid);
			return stmt.executeUpdate() != 0;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
}
