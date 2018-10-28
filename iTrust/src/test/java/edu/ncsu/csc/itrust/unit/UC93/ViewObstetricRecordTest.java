package edu.ncsu.csc.itrust.unit.UC93;


import org.junit.Test;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

public class ViewObstetricRecordTest extends HelperTest{

	@Test
	public void test() throws Exception{
		WebConversation wc = login("9000000003","pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Patient Information").click();
		assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		wr.getForms()[1].setParameter("FIRST_NAME", "Random");
		wr.getForms()[1].setParameter("LAST_NAME", "Person");
		wr.getForms()[1].getButtons()[0].click();
		wr = wc.getCurrentPage();
		
		WebTable wt = wr.getTableStartingWith("MID");
		assertEquals("MID", wt.getCellAsText(0, 0));
		assertEquals("", wt.getCellAsText(1, 0));
		assertEquals("Random", wt.getCellAsText(1, 1));
		assertEquals("Person", wt.getCellAsText(1, 2));
		wr.getForms()[2].getButtons()[0].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Edit Patient", wr.getTitle());

	}

}
