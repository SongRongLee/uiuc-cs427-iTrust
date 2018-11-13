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
import edu.ncsu.csc.itrust.model.old.beans.ChildbirthVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.DeliveryRecordBean;
import edu.ncsu.csc.itrust.model.old.beans.FetusBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.ChildbirthVisitForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.DeliveryRecordForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.FetusForm;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ChildbirthVisitDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.validate.ChildbirthVisitValidator;
import edu.ncsu.csc.itrust.model.old.validate.DeliveryRecordValidator;


/**
 * Add a patient's childbirth visit used by addChildbirthVisit.jsp
 * 
 * 
 */
public class AddChildbirthVisitAction extends PatientBaseAction {
	private ChildbirthVisitValidator validator = new ChildbirthVisitValidator();
	private DeliveryRecordValidator dValidator = new DeliveryRecordValidator();
	private PatientDAO patientDAO;
	private ChildbirthVisitDAO childbirthDAO;
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
	public AddChildbirthVisitAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
		super(factory, pidString);
		this.patientDAO = factory.getPatientDAO();
		this.authDAO = factory.getAuthDAO();
		this.childbirthDAO = factory.getChildbirthVisitDAO();
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
	 * Return a childbirth visit that vid represents
	 * 
	 * @param vid The id of the childbirth visit we are looking for.
	 * @return a ChildbirthVisitBean
	 * @throws ITrustException
	 */
	public ChildbirthVisitBean getChildbirthVisit(long vid) throws ITrustException {
		return childbirthDAO.getChildbirthVisit(vid);
	}
	
	
	public long addVisit(ChildbirthVisitBean newVisit, String patientID, String preferredChildbirthMethod,
			String drugs, String scheduledDate, String preScheduled) throws ITrustException, FormValidationException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		ChildbirthVisitForm form = new ChildbirthVisitForm(patientID, preferredChildbirthMethod, drugs, scheduledDate,
				preScheduled);
		validator.validate(form);
		
		// set ChildbirthVisit manually after validation
		newVisit.setPatientID(Integer.parseInt(patientID));
		try {
			newVisit.setScheduledDate(new Timestamp(sdf.parse(scheduledDate).getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		newVisit.setPreferredChildbirthMethod(preferredChildbirthMethod);
		newVisit.setDrugs(drugs);
		newVisit.setPreScheduled(Boolean.parseBoolean(preScheduled));
		
		return childbirthDAO.addChildbirthVisit(newVisit);
	}
	
	/**
	 * Update DeliveryRecordBean object and save its information
	 * 
	 * @param newRecord, patientID, ultrasoundID, created_on, CRL, BPD, HC, FL, OFD, AC, HL, EFW
	 * @throws ITrustException
	 * @throws FormValidationException
	 */
	public long addDelivery(DeliveryRecordBean newRecord, String patientID, String childbirthVisitID, String deliveryDateTime, String deliveryMethod) throws ITrustException, FormValidationException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		DeliveryRecordForm form = new DeliveryRecordForm(patientID, childbirthVisitID, deliveryDateTime, deliveryMethod);
		dValidator.validate(form);
		
		// set FetusBean manually after validation
		newRecord.setPatientID(Long.parseLong(patientID));
		newRecord.setChildbirthVisitID(Long.parseLong(childbirthVisitID));
		try {
			newRecord.setDeliveryDateTime(sdf.parse(deliveryDateTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newRecord.setDeliveryMethod(deliveryMethod);
				
		return childbirthDAO.addDeliveryRecord(newRecord);
	}
	
	/**
	 * Return a childbirth visit that vid represents
	 * 
	 * @param vid The id of the childbirth visit we are looking for.
	 * @return a ChildbirthVisitBean
	 * @throws ITrustException
	 */
	public DeliveryRecordBean getDeliveryRecord(long vid) throws ITrustException {
		return childbirthDAO.getDeliveryRecord(vid);
	}
}
