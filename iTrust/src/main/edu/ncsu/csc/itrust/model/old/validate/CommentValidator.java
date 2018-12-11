package edu.ncsu.csc.itrust.model.old.validate;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.CommentBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.CommentForm;
import java.util.Date;

/**
 * Validates a  DeliveryRecordForm
 * 
 *  
 * 
 */
public class CommentValidator extends BeanValidator<CommentForm> {
	/**
	 * The default constructor.
	 */
	public CommentValidator() {
	}

	@Override
	public void validate(CommentForm newForm) throws FormValidationException{
		ErrorList errorList = new ErrorList();
		
		errorList.addIfNotNull(checkFormat("bulletinBoardID", newForm.getBulletinBoardID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkFormat("posterFirstName", newForm.getPosterFirstName(), ValidationFormat.NAME, false));
		errorList.addIfNotNull(checkFormat("posterLastName", newForm.getPosterLastName(), ValidationFormat.NAME, false));
		errorList.addIfNotNull(checkFormat("text", newForm.getText(), ValidationFormat.MESSAGES_BODY, false));
		errorList.addIfNotNull(checkFormat("createdOn", newForm.getCreatedOn(), ValidationFormat.DATETIMESTAMP, false));
		
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}