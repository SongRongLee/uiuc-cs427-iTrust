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
		//action.addBulletinBoard(bb, title, posterFirstName, posterLastName, content);

		try {
			action.addBulletinBoard(bb, title, posterFirstName, posterLastName, content);			
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
		<%@include file="/auth/hcp/composeBulletinPost.jsp" 
		%>
		
	</form>
	<br />
	<br />
</div>

<%@include file="/footer.jsp" %>
