package edu.ncsu.csc.itrust.model.old.validate;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.forms.UltrasoundForm;

/**
 * Validates an Ultrasound form
 */

public class UltrasoundValidator extends BeanValidator<UltrasoundForm> {
	/**
	 * The default constructor.
	 */
	public UltrasoundValidator() {
	}

	
	@Override
	public void validate(UltrasoundForm newForm) throws FormValidationException{
		ErrorList errorList = new ErrorList();
		
		errorList.addIfNotNull(checkFormat("PatientID", newForm.getPatientID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkFormat("created_on", newForm.getCreated_on(), ValidationFormat.DATETIMESTAMP, false));
		// Image is yet to validate
		
		
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}