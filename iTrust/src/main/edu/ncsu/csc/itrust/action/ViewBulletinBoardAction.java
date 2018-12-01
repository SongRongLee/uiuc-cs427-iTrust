package edu.ncsu.csc.itrust.action;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.BulletinBoardBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.BulletinBoardDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;

/**
 * View a patient's childbirth visit used by viewChildbirthVisit.jsp
 * 
 * 
 */
public class ViewBulletinBoardAction extends PatientBaseAction {
	private PatientDAO patientDAO;
	private BulletinBoardDAO bulletinBoardDAO;
	private AuthDAO authDAO;
	private long loggedInMID;

	/**
	 * The super class validates the patient id
	 * 
	 * @param factory The DAOFactory used to create the DAOs for this action.
	 * @param loggedInMID The MID of the user who is authorizing this action.
	 * @param pidString The MID of the patient being edited.
	 * @throws ITrustException
	 */
	public ViewBulletinBoardAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
		super(factory, pidString);
		this.patientDAO = factory.getPatientDAO();
		this.authDAO = factory.getAuthDAO();
		this.bulletinBoardDAO = factory.getBulletinBoardDAO();
		this.loggedInMID = loggedInMID;
	}
	
	/**
	 * Returns a PatientBean for the patient
	 * 
	 * @return the PatientBean
	 * @throws DBException
	 */
	public PatientBean getPatient() throws DBException {
		return patientDAO.getPatient(this.getPid());
	}
	
	/**
	 * Return a list of bulletin board
	 * 
	 * @return a list of BulletinBoardBean
	 * @throws ITrustException
	 */
	public List<BulletinBoardBean> getAllBulletinBoards() throws ITrustException {
		return bulletinBoardDAO.getAllBulletinBoards();
	}

	
	/**
	 * Return a bulletin board that bid represents
	 * 
	 * @param bid The id of the bulletin board we are looking for.
	 * @return a BulletinBoardBean
	 * @throws ITrustException
	 */
	public BulletinBoardBean getBulletinBoard(long bid) throws ITrustException {
		return bulletinBoardDAO.getBulletinBoard(bid);
	}
	
	
}
