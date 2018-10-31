<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewObstetricsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PregnancyBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO"%>
<%@page import="edu.ncsu.csc.itrust.action.AddObstetricsAction"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Add an Obstetrics Record";
%>

<%@include file="/header.jsp"%>

<%	
	/* Require a Patient ID first */
	String pidString = (String) session.getAttribute("pid");
	if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
		out.println("pidstring is null");
		response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewObstetricsRecords.jsp");
		return;
	}
	ViewObstetricsAction action = new ViewObstetricsAction(prodDAO, loggedInMID.longValue(), pidString);
	long pid = action.getPid();
%>
<br /><br />
<input type="hidden" name="add" id="add" />

<%
	DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Date date = new Date();
    /* For EDD
    Calendar cal = Calendar.getInstance();
    cal.setTime(ob_action.getLMP());
    cal.add(Calendar.DATE, 280);
    Date EDD = sdf.format(c.getTime());
    */
	boolean formIsFilled = request.getParameter("formIsFilled") != null
	&& request.getParameter("formIsFilled").equals("true");
	if (formIsFilled) {
		/* adding new record to DB
		   must actually be more complex than this
		   since there are two Beans: OB and Pregancy
		   the BeanBuilder takes care of getParameterMap automatically
		   but uncertain if we can use it (we have two beans) */
		ObstetricsBean obBean = new BeanBuilder<ObstetricsBean>().build(request.getParameterMap(), new ObstetricsBean());
		try{
			AddObstetricsAction ob_action = new AddObstetricsAction(DAOFactory.getProductionInstance(), pid);
			long newObID = ob_action.add(obBean);
%>
	<div align=center>
		<span class="iTrustMessage">New Obstetrics Record successfully added!</span>
		<br />
		<br />
		<table class="fTable">
			<tr>
				<th colspan=2>New Obstetrics Information</th>
			</tr>
			<tr>
				<td class="subHeaderVertical">Obestetrics Record ID:</td>
				<td><%= StringEscapeUtils.escapeHtml("" + (newObID)) %></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Created On:</td>
				<td><%= StringEscapeUtils.escapeHtml("" + (ob_action.getCreatedOn())) %></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">LMP: </td>
				<td><%= StringEscapeUtils.escapeHtml("" + (ob_action.getLMP())) %></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Estimated Due Date: </td>
				<td><%= StringEscapeUtils.escapeHtml("" + (ob_action.getLMP())) %></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Number of Weeks Pregnant: </td>
				<td><%= StringEscapeUtils.escapeHtml("" + (ob_action.getLMP())) %></td>
			</tr>
		</table>
		<p> <% /* need an elegant way of going to viewOBRecords page or auto-clicking on the below link */ %>
			<a href = "viewObstetricsRecords.jsp">Continue to View Obstetrics Records</a>
		</p>
	</div>
<%
		} catch(FormValidationException e){
%>
	<div align=center>
		<span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage()) %></span>
	</div>
<%
		}
	}
%>


<div align=center>
<% /* the form 
      once submitted goes to Java codes above 
      S[3] still needs to be worked on
      somewhat confused by it; will ask later
      */
%>
<form action="addObstetricsRecord.jsp" method="post" id="addObRecordForm"><input type="hidden"
	name="formIsFilled" value="true"> <br />
<br />
<div style="width: 50%; text-align:center;">Please Enter New Obstetrics Record</div>
<br />
<br />
<table class="fTable">
	<tr>
		<th colspan=2>New Obstetrics Record</th>
	</tr>
	<tr>
		<td class="subHeaderVertical">Patient ID:</td>
		<td><input type="text" name="Date" value=<%=pid%>></td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Date:</td>
		<td><input type="text" name="Date" value=<%=sdf.format(date)%>></td>
	</tr>
	<tr>
		<td class="subHeaderVertical">LMP:</td>
		<td><input type="text" name="LMP">
	</tr>
	<tr>
		<td class="subHeaderVertical">Year Of Conception:</td>
		<td><input type="text" name="YearofConception"></td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Hour Labor:</td>
		<td><input type="text" name="HourLabor"></td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Weight Gain:</td>
		<td><input type="text" name="WeightGain"></td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Delivery Type:</td>
		<td><input type="text" name="DeliveryType"></td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Number of Children:</td>
		<td><input type="text" name="DeliveryType"></td>
	</tr>
</table>
<br />
<input type="submit" style="font-size: 16pt; font-weight: bold;" value="Add Record">
</form>


<%@include file="/footer.jsp"%>
