package edu.ncsu.csc.itrust.action;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.ncsu.csc.itrust.RandomPassword;
import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.logger.TransactionLogger;
import edu.ncsu.csc.itrust.model.old.beans.BulletinBoardBean;
import edu.ncsu.csc.itrust.model.old.beans.CommentBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.BulletinBoardForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.CommentForm;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.BulletinBoardDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.enums.Role;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.model.old.validate.BulletinBoardValidator;
import edu.ncsu.csc.itrust.model.old.validate.CommentValidator;


/**
 * Add a bulletin board used by addBulletinBoardVisit.jsp
 * 
 * 
 */
public class AddBulletinBoardAction extends PatientBaseAction {
	private BulletinBoardValidator bValidator = new BulletinBoardValidator();
	private CommentValidator cValidator = new CommentValidator();
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
	public AddBulletinBoardAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
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
	 * Return a bulletin board that bid represents
	 * 
	 * @param bid The id of the bulletin board we are looking for.
	 * @return a BulletinBoardBean
	 * @throws ITrustException
	 */
	public BulletinBoardBean getBulletinBoard(long bid) throws ITrustException {
		return bulletinBoardDAO.getBulletinBoard(bid);
	}
	
	
	public long addBulletinBoard(BulletinBoardBean newBulletinBoard   ) throws ITrustException, FormValidationException {
		
	}
	

	public long addComment(CommentBean newComment, String bulletinBoardID, String posterFirstName, String posterLastName, String text, String createdOn) throws ITrustException, FormValidationException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		CommentForm form = new CommentForm(bulletinBoardID, posterFirstName, posterLastName, text, createdOn);
		cValidator.validate(form);
	
		// set CommentBean manually after validation
		newComment.setBulletinBoardID(Long.parseLong(bulletinBoardID));
		newComment.setPosterFirstName(posterFirstName);
		newComment.setPosterLastName(posterLastName);
		newComment.setText(text);
		try {
			Date CreatedOn = sdf.parse(createdOn);
			newComment.setCreatedOn(CreatedOn);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return bulletinBoardDAO.addComment(newComment);
	}
	
	/**
	 * Return a comment that cid represents
	 * 
	 * @param cid The id of the comment we are looking for.
	 * @return a CommentBean
	 * @throws ITrustException
	 */
	public CommentBean getComment(long cid) throws ITrustException {
		return bulletinBoardDAO.getComment(cid);
	}
	
}
