package edu.ncsu.csc.itrust.model.old.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.model.old.beans.BulletinBoardBean;
/**
 * A loader for PregnancyBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class BulletinBoardLoader implements BeanLoader<BulletinBoardBean> {
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm");

	/**
	 * loadList
	 * @param rs rs
	 * @throws SQLException
	 */
	@Override
	public List<BulletinBoardBean> loadList(ResultSet rs) throws SQLException {
		List<BulletinBoardBean> list = new ArrayList<BulletinBoardBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	private void loadCommon(ResultSet rs, BulletinBoardBean p) throws SQLException{
		p.setID(rs.getInt("ID"));
		p.setTitle(rs.getString("Title"));
		p.setPosterFirstName(rs.getString("PosterFirstName"));
		p.setPosterLastName(rs.getString("PosterLastName"));
		p.setCreatedOn(rs.getTimestamp("CreatedOn"));
		p.setContent(rs.getString("Content"));
	}
	
	/**
	 * loadSingle
	 * @param rs rs
	 * @return p
	 * @throws SQLException
	 */
	@Override
	public BulletinBoardBean loadSingle(ResultSet rs) throws SQLException {
		BulletinBoardBean p = new BulletinBoardBean();
		loadCommon(rs, p);
		return p;
	}

	/**
	 * loadParameters
	 * @throws SQLException
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, BulletinBoardBean p) throws SQLException {
		int i = 1;
		//ps.setLong(i++, p.getID());
		ps.setString(i++, p.getTitle());
		ps.setString(i++, p.getPosterFirstName());
		ps.setString(i++, p.getPosterLastName());
		ps.setString(i++, p.getContent());
		Timestamp date = null;
		try {
			date = new java.sql.Timestamp(DATE_FORMAT.parse(p.getCreatedOnString()).getTime());
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
	public PreparedStatement loadParametersUpdate(PreparedStatement ps, BulletinBoardBean b) throws SQLException {
		int i = 1;
		//ps.setLong(i++, b.getID());
		ps.setString(i++, b.getTitle());
		ps.setString(i++, b.getPosterFirstName());
		ps.setString(i++, b.getPosterLastName());
		ps.setString(i++, b.getContent());
		Timestamp date = null;
		try {
			date = new java.sql.Timestamp(DATE_FORMAT.parse(b.getCreatedOnString()).getTime());
		} catch (ParseException e) {
			//TODO
		}
		ps.setTimestamp(i++, date);
		ps.setLong(i++, b.getID());
		return ps;
	}
}