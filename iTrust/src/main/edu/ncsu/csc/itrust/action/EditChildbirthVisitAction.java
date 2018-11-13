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
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.ChildbirthVisitForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.DeliveryRecordForm;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.validate.ChildbirthVisitValidator;
import edu.ncsu.csc.itrust.model.old.validate.DeliveryRecordValidator;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ChildbirthVisitDAO;


/**
 * Edit a patient's childbirth visit used by editChildbirthVisit.jsp
 * 
 * 
 */
public class EditChildbirthVisitAction extends PatientBaseAction {
	private ChildbirthVisitValidator cValidator = new ChildbirthVisitValidator();
	private DeliveryRecordValidator dValidator = new DeliveryRecordValidator();
	private ChildbirthVisitDAO cbDAO;
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
	public EditChildbirthVisitAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
		super(factory, pidString);
		this.patientDAO = factory.getPatientDAO();
		this.authDAO = factory.getAuthDAO();
		this.cbDAO = factory.getChildbirthVisitDAO();
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
	
	public void editVisit(ChildbirthVisitBean newVisit, String visitID, String patientID, String preferredChildbirthMethod,
			String drugs, String scheduledDate, String preScheduled) throws ITrustException, FormValidationException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		ChildbirthVisitForm form = new ChildbirthVisitForm(patientID, preferredChildbirthMethod, drugs, scheduledDate, preScheduled);
		cValidator.validate(form);
		// set ObstetricsVisitBean manually after validation
		newVisit.setVisitID(Integer.parseInt(visitID));
		newVisit.setPatientID(Integer.parseInt(patientID));
		try {
			newVisit.setScheduledDate(new Timestamp(sdf.parse(scheduledDate).getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		newVisit.setDrugs(drugs);
		newVisit.setPreferredChildbirthMethod(preferredChildbirthMethod);
		newVisit.setPreScheduled(Boolean.parseBoolean(preScheduled));
		
		cbDAO.updateChildbirthVisit(newVisit);
	}
	
	public void editDeliveryRecord(DeliveryRecordBean newRecord, String deliveryRecordID, String patientID, String childbirthVisitID, String deliveryDateTime, String deliveryMethod) throws ITrustException, FormValidationException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		DeliveryRecordForm form = new DeliveryRecordForm(patientID, childbirthVisitID, deliveryDateTime, deliveryMethod);
		dValidator.validate(form);
		// set DeliveryRecordBean manually after validation
		newRecord.setID(Integer.parseInt(deliveryRecordID));
		newRecord.setPatientID(Integer.parseInt(patientID));
		newRecord.setChildbirthVisitID(Integer.parseInt(childbirthVisitID));
		try {
			newRecord.setDeliveryDateTime(sdf.parse(deliveryDateTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		newRecord.setDeliveryMethod(deliveryMethod);
		
		cbDAO.updateDeliveryRecord(newRecord);
	}
	
	/**
	 * Return a childbirth vsiit that vid represents
	 * 
	 * @param vid The id of the obstetrics visit we are looking for.
	 * @return an ObstetricsVisitBean
	 * @throws ITrustException
	 */
	public ChildbirthVisitBean getChildbirthVisit(long vid) throws ITrustException {
		return cbDAO.getChildbirthVisit(vid);
	}
}
