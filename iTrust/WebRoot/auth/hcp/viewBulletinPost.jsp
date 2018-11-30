<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.action.ViewMyMessagesAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.MessageBean"%>
<%@page import="java.util.List"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Bulletin Post";
%>

<%@include file="/header.jsp" %>

<%
	
	
%>
	<div>
		<table width="99%">
			<tr>
				<td><b>Author:</b> </td>
			</tr>
			<tr>
				<td><b>Title:</b> </td>
			</tr>
			<tr>
				<td><b>Date:</b> </td>
			</tr>
			<tr>
				<td>
					<b>Content:</b>
					<textarea readonly rows="10" class="form-control" >bablkajelkjlegjaeglkklejgajegjlaejgl</textarea>
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
				<td>
					<button>delete this post</button>
				</td>
			</tr>
		</table>
	</div>
	


<%@include file="/footer.jsp" %>
