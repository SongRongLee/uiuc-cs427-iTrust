<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.action.SendMessageAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.MessageBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>

<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.MessageDAO"%>

<%@include file="/global.jsp" %>

<%
	pageTitle = "iTrust - Add a Bulletin Post";
%>

<%@include file="/header.jsp" %>

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
