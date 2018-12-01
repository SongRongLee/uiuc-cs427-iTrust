<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="edu.ncsu.csc.itrust.action.GetNextVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.action.GenObstetricsReportAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.AllergyBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PregnancyBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsVisitDAO"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View Obstetrics Obstetrics Report";
%>

<%@include file="/header.jsp"%>

<%
/* Require a Patient ID first */
String pidString = (String) session.getAttribute("pid");
if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
	out.println("pidstring is null");
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/genObstetricsReport.jsp");
	return;
}
/* Get selected patient and current personnel */
GenObstetricsReportAction action = new GenObstetricsReportAction(prodDAO,
		loggedInMID.longValue(), pidString);
PatientBean p = action.getPatient();
long pid = action.getPid();

PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
PersonnelBean personnel = personnelDAO.getPersonnel(loggedInMID);
String specialty = personnel.getSpecialty();
int index = 0;
%>

<!-- BEGIN DISPLAYING TABLES -->

<% if (action.isObstericsPatient()){ 
	loggingAction.logEvent(TransactionType.GENERATE_REPORT,
			loggedInMID.longValue(), pid, "");

%> 
	<br /><br />
<!-- PATIENT INFORMATION TABLE -->
	<table class="fTable" align="center" id="patientInfoList">
		<tr>
			<th colspan="10">Patient Information</th>
		</tr>
		<tr class="subHeader">
			<td colspan="10">Pre-existing conditions:</td>
		</tr>
		
		<!-- BEGIN PREEXISTING CONDITIONS DISPLAY -->
		<% if (action.getDiabetes().isEmpty() && action.getChronicIllness().isEmpty() 
				&& action.getCancers().isEmpty() && action.getSTDs().isEmpty()) { %>
		<tr>
				<td colspan="10">None</td>
		</tr>
		<% } else {
		  if (!action.getDiabetes().isEmpty()) { %>
		<tr>
				<td>Diabetes</td>
				<td><%=StringEscapeUtils.escapeHtml("" + action.getDiabetes() )%></td>
		</tr>
		<% }
		  if (!action.getChronicIllness().isEmpty()) { %>
		<tr>
				<td>Autoimmune disorders</td>
				<td><%=StringEscapeUtils.escapeHtml("" + action.getChronicIllness())%></td>
		</tr>
		<% }
		  if (!action.getCancers().isEmpty()) { %>
		<tr>
				<td>Cancer</td>
				<td><%=StringEscapeUtils.escapeHtml("" + action.getCancers())%></td>
		</tr>
		<% }
		  if (!action.getSTDs().isEmpty()) { %>
		<tr>
				<td>STDs</td>
				<td><%=StringEscapeUtils.escapeHtml("" + action.getSTDs())%></td>
		</tr>
		<% } 
		  }
		 %>
		<!-- END PREEXISTING CONDITIONS DISPLAY -->
		
		<tr class="subHeader">
				<td colspan="10">Common Drug Allergies:</td>
		</tr>
		<%
		List<AllergyBean> allergies = action.getAllergies();
		if (allergies.isEmpty()) { %>
		<tr>
				<td colspan="10">None</td>
		</tr>
		<%}
		else {
			index = 0;
			for (AllergyBean allergy : allergies) {
		%>
			<tr>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (allergy.getDescription()))%></td>
			</tr>
		<%
			index++;
			}
		}
		%>
		
	</table>
	
<!-- PATIENT INFORMATION TABLE -->
	
	<br />
	
<!-- PAST PREGNANCIES TABLE -->
	
	<table class="fTable" align="center" id="pastPregnancyList">
		<tr>
			<th colspan="10">Past Pregnancies</th>
		</tr>
		<tr class="subHeader">
	    		<td>Pregnancy term</td>
	   			<td>Delivery type</td>
	  			<td>Conception year</td>
	  			<td>Estimated delivery date</td>
	  			<td>Blood type</td>
	  			
		</tr>
		<% 
		List<ObstetricsBean> records = action.getAllObstetrics();
		index = 0;
		for (ObstetricsBean obsbean : records) {
			List<PregnancyBean> priorPregnancies = obsbean.getPregnancies();
			Date obstetricsDate = obsbean.getCreated_onAsDate();
		
			for (PregnancyBean pregBean : priorPregnancies) {
				Date pregDate = pregBean.getDateAsDate();
				if(pregDate.before(obstetricsDate) || pregDate.equals(obstetricsDate)) {
			%>
				<tr>
					<td><%=StringEscapeUtils.escapeHtml("" + (pregBean.getNum_weeks_pregnant())+ " weeks")%></td>
					<td><%=StringEscapeUtils.escapeHtml("" + (pregBean.getDelivery_type()))%></td>
					<td><%=StringEscapeUtils.escapeHtml("" + (pregBean.getYOC()))%></td>
					<td><%=StringEscapeUtils.escapeHtml("" + (obsbean.getEDD()))%></td>
					<td><%=StringEscapeUtils.escapeHtml("" + p.getBloodType())%></td>
				</tr>
				<%
					index++;
				}
			}
		}
		%>
		
	</table>
	
<!-- PAST PREGNANCIES TABLE -->
	
	<br />
	
<!-- OB OFFICE VISITS TABLE -->
	
	<table class="fTable" align="center" id="OBlist">
		<tr>
			<th colspan="11">Obstretrics Office Visits</th>
		</tr>
		<tr class="subHeader">
	    		<td>Weeks Pregnant</td>
	    		<td>Weight(pounds)</td>
	    		<td>Blood Pressure</td>
	    		<td>Fetal heart rate</td>
	    		<td>Num Children</td>
	    		<td>Low Lying Placenta</td>
	  			<td>Pregnancy complication warning flags</td>
		</tr>
		<% 
		List<ObstetricsVisitBean> visits = action.getSortedObstetricsVisits();
		GetNextVisitAction nvAction = new GetNextVisitAction();
		index = 0;
		for (ObstetricsVisitBean obvisit : visits) {	
		%>
			<tr>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (obvisit.getNumWeeks()))%></td>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (obvisit.getWeight()))%></td>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (obvisit.getBloodPressure()))%></td>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (obvisit.getFHR()))%></td>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (obvisit.getNumChildren()))%></td>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (obvisit.getLLP()))%></td>
<!-- COMPLICATIONS BEGIN -->
	  			<td>
			  	<% 
			  	long vid = obvisit.getID();
			  	 if (!action.getRHFlag()) { %>
						<%=StringEscapeUtils.escapeHtml("RH Negative")%><br/>
				<% }
				 if (action.isHighBloodPressure(vid)) { %>
						<%=StringEscapeUtils.escapeHtml("High Blood Pressure")%><br/>
				<% }
				 if (action.isAdvancedMaternalAge(vid)) { %>
					<%=StringEscapeUtils.escapeHtml("Advanced Maternal Age")%><br/>
				<% }
				 if (obvisit.getLLP()) { %>
					<%=StringEscapeUtils.escapeHtml("Low-lying placenta")%><br/>
				<% }
				 if (action.isHighPotentialMiscarriage()) { %>
					<%=StringEscapeUtils.escapeHtml("High genetic potential for miscarriage")%><br/>
				<% }
				 if (action.isAbormalHeartRate(vid)) { %>
					<%=StringEscapeUtils.escapeHtml("Abnormal fetal heart rate")%><br/>
				<% }
				 if (obvisit.getNumChildren()>1) { %>
					<%=StringEscapeUtils.escapeHtml("Multiples in current pregnancy")%><br/>
				<% }
				 if (action.isAtypicalWeightChange(vid)) { %>
					<%=StringEscapeUtils.escapeHtml("Atypical weight change")%><br/>
				<% }
				 if (action.isHyperemesisGravidarum()) { %>
					<%=StringEscapeUtils.escapeHtml("Hyperemesis gravidarum")%><br/>
				<% }
				 if (action.isHypothyroidism()) { %>
					<%=StringEscapeUtils.escapeHtml("Hypothyroidism")%><br/>
				<% }
				  
				%>
	  			
	  			</td>
	  			
<!-- COMPLICATIONS END -->
			</tr>
		<%
			index++;
		}
		%>
	<table/>
	
<!-- OB OFFICE VISITS TABLE -->
	
<!-- END DISPLAYING TABLES -->
	<%
}
else{
	
	session.setAttribute("pid", "");
	%>
	<br />
	<div align=center>
		<span class="iTrustMessage" id="ViewUltrasoundPatientError">
		Selected patient does not have an obstetrics record. Please <a href="genObstetricsReport.jsp">try again</a>.</span>
	</div>
	<br />
	<% 
}
%>

<%@include file="/footer.jsp"%>
