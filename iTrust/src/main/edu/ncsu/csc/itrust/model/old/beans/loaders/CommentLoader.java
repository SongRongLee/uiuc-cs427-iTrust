package edu.ncsu.csc.itrust.model.old.beans.loaders;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.model.old.beans.CommentBean;

/**
 * A loader for DeliveryRecordBean.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class CommentLoader implements BeanLoader<CommentBean> {
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm");

	/**
	 * loadList
	 * @param rs rs
	 * @throws SQLException
	 */
	@Override
	public List<CommentBean> loadList(ResultSet rs) throws SQLException {
		List<CommentBean> list = new ArrayList<CommentBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	private void loadCommon(ResultSet rs, CommentBean c) throws SQLException{
		c.setID(rs.getInt("ID"));
		c.setBulletinBoardID(rs.getInt("BulletinBoardID"));
		c.setPosterFirstName(rs.getString("PosterFirstName"));
		c.setPosterLastName(rs.getString("PosterLastName"));
		c.setText(rs.getString("Text"));
		c.setCreatedOn(rs.getTimestamp("CreatedOn"));
	}
	
	/**
	 * loadSingle
	 * @param rs rs
	 * @return d
	 * @throws SQLException
	 */
	@Override
	public CommentBean loadSingle(ResultSet rs) throws SQLException {
		CommentBean c = new CommentBean();
		loadCommon(rs, c);
		return c;
	}

	/**
	 * loadParameters
	 * @throws SQLException
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, CommentBean c) throws SQLException {
		int i = 1;
		ps.setLong(i++, c.getBulletinBoardID());
		ps.setString(i++, c.getPosterFirstName());
		ps.setString(i++, c.getPosterLastName());
		ps.setString(i++, c.getText());
		Timestamp date = null;
		try {
			date = new java.sql.Timestamp(DATE_FORMAT.parse(c.getCreatedOnString()).getTime());
		} catch (ParseException e) {
			//TODO
		}
		ps.setTimestamp(i++, date);
		return ps;
	}
	
	/**
	 * loadParameters for update
	 * @throws SQLException
	 */
	public PreparedStatement loadParametersUpdate(PreparedStatement ps, CommentBean c) throws SQLException {
		int i = 1;
		ps.setLong(i++, c.getBulletinBoardID());
		ps.setString(i++, c.getPosterFirstName());
		ps.setString(i++, c.getPosterLastName());
		ps.setString(i++, c.getText());
		Timestamp date = null;
		try {
			date = new java.sql.Timestamp(DATE_FORMAT.parse(c.getCreatedOnString()).getTime());
		} catch (ParseException e) {
			//TODO
		}
		ps.setTimestamp(i++, date);
		ps.setLong(i++, c.getID());
		return ps;
	}
}