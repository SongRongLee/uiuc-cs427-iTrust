package edu.ncsu.csc.itrust.action;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.BulletinBoardBean;
//import edu.ncsu.csc.itrust.model.old.beans.BulletinBoardBean;
import edu.ncsu.csc.itrust.model.old.beans.CommentBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.BulletinBoardForm;
//import edu.ncsu.csc.itrust.model.old.beans.forms.BulletinBoardForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.CommentForm;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.validate.BulletinBoardValidator;
//import edu.ncsu.csc.itrust.model.old.validate.BulletinBoardValidator;
import edu.ncsu.csc.itrust.model.old.validate.CommentValidator;
import edu.ncsu.csc.itrust.model.old.dao.mysql.BulletinBoardDAO;


/**
 * Edit a patient's childbirth visit used by editChildbirthVisit.jsp
 * 
 * 
 */
public class EditBulletinBoardAction extends PatientBaseAction {
	private BulletinBoardValidator bValidator = new BulletinBoardValidator();
	private CommentValidator cValidator = new CommentValidator();
	private BulletinBoardDAO bulletinBoardDAO;
	private PatientDAO patientDAO;
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
	public EditBulletinBoardAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
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
	
	public void editBulletinBoard(BulletinBoardBean newBulletinBoard, String ID, String title, String posterFirstName, String posterLastName, String createdOn, String content) throws ITrustException, FormValidationException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		BulletinBoardForm form = new BulletinBoardForm(title, posterFirstName, posterLastName, content);
		bValidator.validate(form);
		
		// set DeliveryRecordBean manually after validation
		newBulletinBoard.setID(Integer.parseInt(ID));
		newBulletinBoard.setTitle(title);
		newBulletinBoard.setPosterFirstName(posterFirstName);
		newBulletinBoard.setPosterLastName(posterLastName);
		Date CreatedOn = new Date();
		newBulletinBoard.setCreatedOn(CreatedOn);
		newBulletinBoard.setContent(content);
		
		bulletinBoardDAO.updateBulletinBoard(newBulletinBoard);
	}
	
	public void editComment(CommentBean newComment, String ID, String bulletinBoardID, String posterFirstName, String posterLastName, String text, String createdOn) throws ITrustException, FormValidationException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		CommentForm form = new CommentForm(bulletinBoardID, posterFirstName, posterLastName, text, createdOn);
		cValidator.validate(form);
		
		// set DeliveryRecordBean manually after validation
		newComment.setID(Integer.parseInt(ID));
		newComment.setBulletinBoardID(Integer.parseInt(bulletinBoardID));
		newComment.setPosterFirstName(posterFirstName);
		newComment.setPosterLastName(posterLastName);
		newComment.setText(text);
		try {
			Date CreatedOn = sdf.parse(createdOn);
			newComment.setCreatedOn(CreatedOn);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		bulletinBoardDAO.updateComment(newComment);
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
	
	/**
	 * Method to delete a bulletin board
	 * @param bid
	 * @return a string indicates successfully deleted or not
	 */
	public String deleteBulletinBoard(long bid) throws ITrustException, FormValidationException{
		if (bulletinBoardDAO.deleteBulletinBoard(bid)){
			return "BulletinBoard deleted successfully";
		} else {
			return "BulletinBoard could not be deleted";
		}
	}
	
	/**
	 * Method to delete a comment
	 * @param cid
	 * @return a string indicates successfully deleted or not
	 */
	public String deleteComment(long cid) throws ITrustException, FormValidationException{
		if (bulletinBoardDAO.deleteComment(cid)){
			return "Comment deleted successfully";
		} else {
			return "Comment could not be deleted";
		}
	}
}
