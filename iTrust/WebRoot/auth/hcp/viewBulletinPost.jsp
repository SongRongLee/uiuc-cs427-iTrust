<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.action.ViewBulletinBoardAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditBulletinBoardAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.BulletinBoardBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="java.util.List"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Bulletin Post";
%>

<%@include file="/header.jsp" %>

<%

String deleteRequest = request.getParameter("delete");
if(deleteRequest!=null){
	long did = Long.parseLong(deleteRequest);
	EditBulletinBoardAction editAction = new EditBulletinBoardAction(prodDAO, loggedInMID.longValue(), "1");
	editAction.deleteBulletinBoard(did);
	response.sendRedirect("/iTrust/auth/hcp/home.jsp");
	return;
}

PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
PersonnelBean personnel = personnelDAO.getPersonnel(loggedInMID);
String viewerFirstName = personnel.getFirstName();
String viewerLastName = personnel.getLastName();

String rString = request.getParameter("requestID");
long bid = Long.parseLong(rString);
ViewBulletinBoardAction action = new ViewBulletinBoardAction(prodDAO, loggedInMID.longValue(), "1"); 
BulletinBoardBean bb = action.getBulletinBoard(bid);

	
%>
	<div>
		<table width="99%">
			<tr>
				<td><b>Author: <%=bb.getPosterFirstName() + " " + bb.getPosterLastName()%></b> </td>
			</tr>
			<tr>
				<td><b>Title: <%=bb.getTitle()%></b> </td>
			</tr>
			<tr>
				<td><b>Date: <%=bb.getCreatedOnString()%></b> </td>
			</tr>
			<tr>
				<td>
					<b>Content:</b>
					<textarea readonly rows="10" class="form-control" ><%=bb.getContent()%></textarea>
				</td>
			</tr>
			<tr>
				<td>
					<b>Comments:</b><br>
					<span>[2018-11-05] Robert:<br>aiwejfoiwjgjwoegjiwengoiniw</span><br><br>
					<span>[2018-11-05] Robert:<br>aiwejfoiwjgjwoegjiwengoiniw</span><br><br>
					<span>[2018-11-05] Robert:<br>aiwejfoiwjgjwoegjiwengoiniw</span><br><br>
				</td>
			</tr>
			<tr>
				<td>
					<b>Comment:</b>
					<textarea rows="2" class="form-control" ></textarea>
				</td>
			</tr>
			<tr>
			<%
			if(viewerFirstName.equals(bb.getPosterFirstName()) && viewerLastName.equals(bb.getPosterLastName())){
				%>
					
					<td>
						<form action="viewBulletinPost.jsp">
							<input type="hidden" name="delete" value="<%=bid%>">
						    <input type="submit" value="Delete this post" />
						</form>
					</td>
				<%
			}
			%>
			</tr>
		</table>
	</div>
	


<%@include file="/footer.jsp" %>
