<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.action.EditBulletinBoardAction"%>
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
	pageTitle = "iTrust - Edit a Bulletin Post";
%>

<%@include file="/header.jsp" %>



<%
	EditBulletinBoardAction action = new EditBulletinBoardAction(prodDAO, loggedInMID.longValue(),"1");
	String eid = request.getParameter("requestID");
	if (eid == null){
		eid = request.getParameter("ID");
	}
	
	BulletinBoardBean bb = new BulletinBoardBean();
	bb = action.getBulletinBoard(Long.parseLong(eid));
		
	if (request.getParameter("postBody") != null) {
		String createdOn = bb.getCreatedOnString();
		String title = request.getParameter("subject");		
		PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
		PersonnelBean personnel = personnelDAO.getPersonnel(loggedInMID);
		String posterFirstName = personnel.getFirstName();
		String posterLastName = personnel.getLastName();
		String content = request.getParameter("postBody");

		try {
			action.editBulletinBoard(bb, eid, title, posterFirstName, posterLastName, createdOn, content);			
			response.sendRedirect("/iTrust/auth/hcp/home.jsp");
	
		} catch (FormValidationException e){
				%>
				<div align=center><span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage())%></span></div>
				<%	
				}
	}
%>


<div align="left">
	<h2>Edit a Bulletin Post</h2>
	<form id="mainForm" method="post" action="editBulletinPost.jsp">
	<input type="hidden" name="ID" value="<%=eid%>">
		<% long ignoreMID = loggedInMID; %>
		
		<p><b>Subject:</b> <input width="50%" class="form-control"   type="text" name="subject" value="<%=bb.getTitle()%>"/></p>
		<br>
		<br>
		<p><b>Content:</b> </p>
		<textarea class="form-control" rows="3" type="text" name="postBody"><%=bb.getContent()%></textarea><br />
		<br />
		<input class="btn btn-default" type="submit" value="Edit" name="postBulletinPost"/>
		
	</form>
	<br />
	<br />
</div>

<%@include file="/footer.jsp" %>
