package edu.ncsu.csc.itrust.action;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.diagnosis.Diagnosis;
import edu.ncsu.csc.itrust.model.diagnosis.DiagnosisMySQL;
import edu.ncsu.csc.itrust.model.icdcode.ICDCode;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisit;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisitData;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisitMySQL;
import edu.ncsu.csc.itrust.model.old.beans.AllergyBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AllergyDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsVisitDAO;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;


/**
 * View a patient's obstetrics report used by genObstetricsReport.jsp
 */
public class  GenObstetricsReportAction extends PatientBaseAction {
	private PatientDAO patientDAO;
	private ObstetricsDAO obstetricsDAO;
	private ObstetricsVisitDAO obstetricsVisitDAO;
	private AllergyDAO allergyDAO;
	private OfficeVisitData officeVisitData;
	private DiagnosisMySQL diagnosisData;
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
	public GenObstetricsReportAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
		super(factory, pidString);
		this.patientDAO = factory.getPatientDAO();
		this.authDAO = factory.getAuthDAO();
		this.obstetricsDAO = factory.getObstetricsDAO();
		this.obstetricsVisitDAO = factory.getObstetricsVisitDAO();
		this.allergyDAO = factory.getAllergyDAO();
		this.officeVisitData = new OfficeVisitMySQL();
		this.diagnosisData = new DiagnosisMySQL();
		this.loggedInMID = loggedInMID;
	}
	
	/**
	 * The super class validates the patient id
	 * 
	 * @param factory The DAOFactory used to create the DAOs for this action.
	 * @param loggedInMID The MID of the user who is authorizing this action.
	 * @param pidString The MID of the patient being edited.
	 * @throws ITrustException
	 */
	public GenObstetricsReportAction(DAOFactory factory, long loggedInMID, String pidString, DataSource ds) throws ITrustException {
		super(factory, pidString);
		this.patientDAO = factory.getPatientDAO();
		this.authDAO = factory.getAuthDAO();
		this.obstetricsDAO = factory.getObstetricsDAO();
		this.obstetricsVisitDAO = factory.getObstetricsVisitDAO();
		this.allergyDAO = factory.getAllergyDAO();
		this.officeVisitData = new OfficeVisitMySQL(ds);
		this.diagnosisData = new DiagnosisMySQL(ds);
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
	 * Return a list of obstetrics visits that pid represents sorted by scheduled dates
	 * 
	 * @param pid The id of the patient we are looking for.
	 * @return a list of ObstetricsVisitBean
	 * @throws ITrustException
	 */
	public List<ObstetricsVisitBean> getSortedObstetricsVisits() throws ITrustException {
		return obstetricsVisitDAO.getSortedObstetricsVisits(pid);
	}
	
	/**
	 * Return an obstetrics vsiit that vid represents
	 * 
	 * @param vid The id of the obstetrics visit we are looking for.
	 * @return an ObstetricsVisitBean
	 * @throws ITrustException
	 */
	public ObstetricsVisitBean getObstetricsVisit(long vid) throws ITrustException {
		return obstetricsVisitDAO.getObstetricsVisit(vid);
	}
	
	/**
	 * Return a list of obstetrics record that pid represents
	 * 
	 * @param pid The id of the patient we are looking for.
	 * @return a list of ObstetricsBean
	 * @throws ITrustException
	 */
	public List<ObstetricsBean> getAllObstetrics() throws ITrustException {
		return obstetricsDAO.getAllObstetrics(pid);
	}
	
	/**
	 * Return true if the patient is an obstetrics patient
	 * 
	 * @param pid The id of the patient we are checking.
	 * @return a Boolean
	 * @throws ITrustException
	 */
	public Boolean isObstericsPatient() throws ITrustException {
		List<ObstetricsBean> oblist = getAllObstetrics();
		if (oblist.size() != 0){
			
			/* Get current date */
			Date now = new Date();
			
			long diffInMillies = Math.abs(now.getTime() - oblist.get(0).getLMPAsDate().getTime());
		    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			if (((int)diff/7) < 49){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return true if the patient has RH+
	 * 
	 * @param pid - the id of the patient we are checking.
	 * @return a Boolean
	 * @throws ITrustException
	 */
	public Boolean getRHFlag() throws ITrustException {
		PatientBean pb = patientDAO.getPatient(pid);
		String bloodType = pb.getBloodType().getName();

		if (bloodType.charAt(bloodType.length()-1) == '-')
			return false;
		
		return true;
	}
	
	/**
	 * Return true if the patient has High Blood Pressure
	 * during the given obstetrics visit
	 * 
	 * @param vid The id of the obstetrics visit we are checking.
	 * @return a Boolean
	 * @throws ITrustException
	 */
	public Boolean isHighBloodPressure(long vid) throws ITrustException {
		ObstetricsVisitBean obstetricsVisitBean = obstetricsVisitDAO.getObstetricsVisit(vid);
		int systolic = Integer.parseInt((obstetricsVisitBean.getBloodPressure().split("/")[0]));
		int diastolic = Integer.parseInt((obstetricsVisitBean.getBloodPressure().split("/")[1]));
		return (systolic >= 140 || diastolic >= 90);
	}
	
	/**
	 * Return the corresponding obstetrics bean given a
	 * obstetrics visit id
	 * 
	 * @param vid The id of the obstetrics visit we are checking.
	 * @return an ObstetricsBean
	 * @throws ITrustException
	 */
	public ObstetricsBean getObstetricsByVisit(long vid) throws ITrustException {
		ObstetricsVisitBean obstetricsVisitBean = obstetricsVisitDAO.getObstetricsVisit(vid);
		Timestamp scheduledDate = obstetricsVisitBean.getScheduledDate();
		int weekNum = Integer.parseInt(obstetricsVisitBean.getNumWeeks().split("-")[0]);
		int dayNum = Integer.parseInt(obstetricsVisitBean.getNumWeeks().split("-")[1]);
		int dayPreg = 7*weekNum + dayNum;
		
		// calculate LMP
		Calendar cal = Calendar.getInstance();
		cal.setTime(scheduledDate);
		cal.add(Calendar.DAY_OF_WEEK, -dayPreg);
		Timestamp LMP = new Timestamp(cal.getTime().getTime());
		
		List<ObstetricsBean> oblist = getAllObstetrics();
		ObstetricsBean result = null;
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		for (ObstetricsBean ob : oblist) {
			if (ob.getLMP().equals(dateFormat.format(LMP))){
				result = ob;
			}
		}
		return result;
	}
	
	/**
	 * Return true if the patient is of advanced maternal age
	 * during the given obstetrics visit
	 * 
	 * @param vid The id of the obstetrics visit we are checking.
	 * @return a Boolean
	 * @throws ITrustException
	 */
	public Boolean isAdvancedMaternalAge(long vid) throws ITrustException {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		PatientBean patient = patientDAO.getPatient(pid);
		ObstetricsBean ob = getObstetricsByVisit(vid);
		
		Date EDD = null;
		try {
			EDD = dateFormat.parse(ob.getEDD());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// calculate age
		long ageInMs = EDD.getTime() - patient.getDateOfBirth().getTime();
		long age = ageInMs / (1000L * 60L * 60L * 24L * 365L);
		return (int) age >= 35;
	}
	
	/**
	 * Return true if the patient is of high potential for miscarriage
	 * 
	 * @return a Boolean
	 * @throws ITrustException
	 */
	public Boolean isHighPotentialMiscarriage() throws ITrustException {
		String[] miscarriageList = {"D6851"};
		List<OfficeVisit> ovList = officeVisitData.getVisitsForPatient(pid);
		for (OfficeVisit ov : ovList) {
			List<Diagnosis> diagList = diagnosisData.getAllDiagnosisByOfficeVisit(ov.getVisitID());
			for (Diagnosis diag : diagList) {
				if (Arrays.asList(miscarriageList).contains(diag.getIcdCode().getCode())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Return true if the fetus has abnormal heart rate
	 * during the given obstetrics visit
	 * 
	 * @param vid The id of the obstetrics visit we are checking.
	 * @return a Boolean
	 * @throws ITrustException
	 */
	public Boolean isAbormalHeartRate(long vid) throws ITrustException {
		ObstetricsVisitBean obstetricsVisitBean = obstetricsVisitDAO.getObstetricsVisit(vid);
		int FHR = obstetricsVisitBean.getFHR();
		return (FHR < 120 || FHR > 160);
	}
	
	/**
	 * Return true if the patient has atypical weight change
	 * during the given obstetrics visit
	 * 
	 * @param vid The id of the obstetrics visit we are checking.
	 * @return a Boolean
	 * @throws ITrustException
	 */
	public Boolean isAtypicalWeightChange(long vid) throws ITrustException {
		ObstetricsBean ob = getObstetricsByVisit(vid);
		float weightGain = ob.getPregnancies().get(0).getWeight_gain();
		return (weightGain < 13 || weightGain > 35);
	}
	
	/**
	 * Return true if the patient is of hyperemesis gravidarum
	 * 
	 * @return a Boolean
	 * @throws ITrustException
	 */
	public Boolean isHyperemesisGravidarum() throws ITrustException {
		String[] hgList = {"O211"};
		List<OfficeVisit> ovList = officeVisitData.getVisitsForPatient(pid);
		for (OfficeVisit ov : ovList) {
			List<Diagnosis> diagList = diagnosisData.getAllDiagnosisByOfficeVisit(ov.getVisitID());
			for (Diagnosis diag : diagList) {
				if (Arrays.asList(hgList).contains(diag.getIcdCode().getCode())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Return true if the patient is of Hypothyroidism
	 * 
	 * @return a Boolean
	 * @throws ITrustException
	 */
	public Boolean isHypothyroidism() throws ITrustException {
		String[] hypothyroidismList = {"E039"};
		List<OfficeVisit> ovList = officeVisitData.getVisitsForPatient(pid);
		for (OfficeVisit ov : ovList) {
			List<Diagnosis> diagList = diagnosisData.getAllDiagnosisByOfficeVisit(ov.getVisitID());
			for (Diagnosis diag : diagList) {
				if (Arrays.asList(hypothyroidismList).contains(diag.getIcdCode().getCode())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns a list of ICDCode of diabetes for the patient
	 * 
	 * @return a list of ICDCode
	 * @throws ITrustException
	 */
	public List<ICDCode> getDiabetes() throws ITrustException {
		String[] diabetesList = {"E10", "E11", "E08"};
		List<ICDCode> results = new ArrayList<ICDCode>();
		List<OfficeVisit> ovList = officeVisitData.getVisitsForPatient(pid);
		for (OfficeVisit ov : ovList) {
			List<Diagnosis> diagList = diagnosisData.getAllDiagnosisByOfficeVisit(ov.getVisitID());
			for (Diagnosis diag : diagList) {
				if (Arrays.asList(diabetesList).contains(diag.getIcdCode().getCode())){
					results.add(diag.getIcdCode());
				}
			}
		}
		return results;
	}
	
	/**
	 * Returns a list of ICDCode of chronic illness for the patient
	 * 
	 * @return a list of ICDCode
	 * @throws ITrustException
	 */
	public List<ICDCode> getChronicIllness() throws ITrustException {
		List<ICDCode> results = new ArrayList<ICDCode>();
		List<OfficeVisit> ovList = officeVisitData.getVisitsForPatient(pid);
		for (OfficeVisit ov : ovList) {
			List<Diagnosis> diagList = diagnosisData.getAllDiagnosisByOfficeVisit(ov.getVisitID());
			for (Diagnosis diag : diagList) {
				if (diag.getIcdCode().isChronic()){
					results.add(diag.getIcdCode());
				}
			}
		}
		return results;
	}
	
	/**
	 * Returns a list of ICDCode of cancers for the patient
	 * 
	 * @return a list of ICDCode
	 * @throws ITrustException
	 */
	public List<ICDCode> getCancers() throws ITrustException {
		String[] cancerList = {"C50", "C7A", "C15"};
		List<ICDCode> results = new ArrayList<ICDCode>();
		List<OfficeVisit> ovList = officeVisitData.getVisitsForPatient(pid);
		for (OfficeVisit ov : ovList) {
			List<Diagnosis> diagList = diagnosisData.getAllDiagnosisByOfficeVisit(ov.getVisitID());
			for (Diagnosis diag : diagList) {
				if (Arrays.asList(cancerList).contains(diag.getIcdCode().getCode())){
					results.add(diag.getIcdCode());
				}
			}
		}
		return results;
	}
	
	/**
	 * Returns a list of ICDCode of STDs for the patient
	 * 
	 * @return a list of ICDCode
	 * @throws ITrustException
	 */
	public List<ICDCode> getSTDs() throws ITrustException {
		String[] stdList = {"B20", "B0082", "A568", "A64"};
		List<ICDCode> results = new ArrayList<ICDCode>();
		List<OfficeVisit> ovList = officeVisitData.getVisitsForPatient(pid);
		for (OfficeVisit ov : ovList) {
			List<Diagnosis> diagList = diagnosisData.getAllDiagnosisByOfficeVisit(ov.getVisitID());
			for (Diagnosis diag : diagList) {
				if (Arrays.asList(stdList).contains(diag.getIcdCode().getCode())){
					results.add(diag.getIcdCode());
				}
			}
		}
		return results;
	}
	
	/**
	 * Returns a list of AllergyBeans for the patient
	 * 
	 * @return a list of AllergyBeans
	 * @throws ITrustException
	 */
	public List<AllergyBean> getAllergies() throws ITrustException {
		return allergyDAO.getAllergies(pid);
	}
}
