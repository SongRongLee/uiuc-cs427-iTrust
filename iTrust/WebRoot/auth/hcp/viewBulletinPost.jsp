<%@page errorPage="/auth/exceptionHandler.jsp"%>
<%@page import="edu.ncsu.csc.itrust.action.AddBulletinBoardAction"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewBulletinBoardAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditBulletinBoardAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.CommentBean"%>
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
	loggingAction.logEvent(TransactionType.DELETE_BULLETIN_BOARD, loggedInMID.longValue(), 0, Long.toString(did));
	response.sendRedirect("/iTrust/auth/hcp/home.jsp");
	return;
}

String editRequest = request.getParameter("edit");
if(editRequest!=null){
	long eid = Long.parseLong(editRequest);
	EditBulletinBoardAction editAction = new EditBulletinBoardAction(prodDAO, loggedInMID.longValue(), "1");
	BulletinBoardBean editbb = editAction.getBulletinBoard(eid);
	response.sendRedirect("/iTrust/auth/hcp/editBulletinPost.jsp?requestID=" + Long.toString(eid));
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

String addCommentRequest = request.getParameter("comment");
if(addCommentRequest!=null){
	AddBulletinBoardAction addAction = new AddBulletinBoardAction(prodDAO, loggedInMID.longValue(), "1");
	CommentBean newComment = new CommentBean();
	addAction.addComment(newComment, rString, viewerFirstName, viewerLastName, addCommentRequest, "11/05/1996 12:25");
	loggingAction.logEvent(TransactionType.CREATE_COMMENT, loggedInMID.longValue(), 0, Long.toString(newComment.getID()));
	response.sendRedirect("/iTrust/auth/hcp/viewBulletinPost.jsp?requestID=" + rString);
	return;
}

String delCommentRequest = request.getParameter("deleteComment");
if(delCommentRequest!=null){
	Long cid = Long.parseLong(delCommentRequest);
	EditBulletinBoardAction editAction = new EditBulletinBoardAction(prodDAO, loggedInMID.longValue(), "1");
	editAction.deleteComment(cid);
	response.sendRedirect("/iTrust/auth/hcp/viewBulletinPost.jsp?requestID=" + rString);
	return;
}
	
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
						<textarea readonly rows="10" class="form-control" name="postBody"><%=bb.getContent()%></textarea>
				</td>
			</tr>
			<tr>
				<td>
					<b>Comments:</b><br>
					<%
						List<CommentBean> commentList = bb.getComments();
						for(CommentBean cb:commentList){
							%>
							<span>[<%=cb.getCreatedOnString()%>] <%=cb.getPosterFirstName() + " " + cb.getPosterLastName()%>:
							<%
							if(viewerFirstName.equals(cb.getPosterFirstName())&&viewerLastName.equals(cb.getPosterLastName())){
							%>
								<form action="viewBulletinPost.jsp" style="display: inline-block; ">
									<input type="hidden" name="requestID" value="<%=rString%>">
									<input type="hidden" name="deleteComment" value="<%=cb.getID()%>">
									<input type="submit" value="delete">
								</form>
								<br>
							<%
							}
							%>
							<%=cb.getText() %></span><br><br>
							<%
						}
					%>
				</td>
			</tr>
			<tr>
				<td>
					<b>Comment:</b>
					<form action="viewBulletinPost.jsp">
						<input type="hidden" name="requestID" value="<%=rString%>">
						<textarea name="comment" rows="2" class="form-control" ></textarea>
						<input class="btn btn-default" type="submit" value="Submit">
					</form>
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
					<td>
						<form action="viewBulletinPost.jsp">
							<input type="hidden" name="edit" value="<%=bid%>">
						    <input type="submit" value="Edit this post" />
						</form>
					</td>
				<%
			}
			%>
			</tr>
		</table>
	</div>
	


<%@include file="/footer.jsp" %>
