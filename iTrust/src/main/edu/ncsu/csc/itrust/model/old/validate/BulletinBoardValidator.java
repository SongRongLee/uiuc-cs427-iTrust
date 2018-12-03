package edu.ncsu.csc.itrust.model.old.validate;

import edu.ncsu.csc.itrust.action.EditPatientAction;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.forms.BulletinBoardForm;

import org.apache.commons.validator.CreditCardValidator;
import java.util.Date;

/**
 * Validates a BulletinBoardForm
 * 
 *  
 * 
 */
public class BulletinBoardValidator extends BeanValidator<BulletinBoardForm> {
	/**
	 * The default constructor.
	 */
	public BulletinBoardValidator() {
	}

	@Override
	public void validate(BulletinBoardForm newForm) throws FormValidationException{
		ErrorList errorList = new ErrorList();
		
		errorList.addIfNotNull(checkFormat("Title", newForm.getTitle(), ValidationFormat.QUESTION, false));
		errorList.addIfNotNull(checkFormat("PosterFirstName", newForm.getPosterFirstName(), ValidationFormat.NAME, false));
		errorList.addIfNotNull(checkFormat("PosterLastName", newForm.getPosterLastName(), ValidationFormat.NAME, false));
		errorList.addIfNotNull(checkFormat("Content", newForm.getContent(), ValidationFormat.MESSAGES_BODY, false));

		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}
