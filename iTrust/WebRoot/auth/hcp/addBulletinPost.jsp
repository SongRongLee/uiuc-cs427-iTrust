<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.action.AddBulletinBoardAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.BulletinBoardBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.CommentBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>

<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.BulletinBoardDAO"%>

<%@include file="/global.jsp" %>

<%
	pageTitle = "iTrust - Add a Bulletin Post";
%>

<%@include file="/header.jsp" %>



<%
	AddBulletinBoardAction action = new AddBulletinBoardAction(prodDAO, loggedInMID.longValue(),"1");
	
		
	if (request.getParameter("postBody") != null) {
		BulletinBoardBean bb = new BulletinBoardBean();
		String title = request.getParameter("subject");		
		PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
		PersonnelBean personnel = personnelDAO.getPersonnel(loggedInMID);
		String posterFirstName = personnel.getFirstName();
		String posterLastName = personnel.getLastName();
		String content = request.getParameter("postBody");

		try {
			action.addBulletinBoard(bb, title, posterFirstName, posterLastName, content);
			loggingAction.logEvent(TransactionType.CREATE_BULLETIN_BOARD, loggedInMID.longValue(), 0, Long.toString(bb.getID()));
			response.sendRedirect("/iTrust/auth/hcp/home.jsp");
	
		} catch (FormValidationException e){
				%>
				<div align=center><span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage())%></span></div>
				<%	
				}
	}
%>


<div align="left">
	<h2>Add a Bulletin Post</h2>
	<form id="mainForm" method="post" action="addBulletinPost.jsp">
		<% long ignoreMID = loggedInMID; %>
		
		<p><b>Subject:</b> <input width="50%" class="form-control" type="text" name="subject"/></p>
		<br>
		<br>
		<p><b>Content:</b> </p>
		<textarea class="form-control" rows="3" type="text" name="postBody"></textarea><br />
		<br />
		<input class="btn btn-default" type="submit" value="Post" name="postBulletinPost"/>
		
	</form>
	<br />
	<br />
</div>

<%@include file="/footer.jsp" %>
