<%@ page import="com.Qcards.ItemRequests" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'itemRequests.label', default: 'ItemRequests')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<%-- <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"> --%>
		
		<g:javascript>
		        $(document).ready(function()
		        {
		          $(".datepicker").datepicker({dateFormat: 'dd/mm/yy'});
		          console.log('has been invoked');
		        })
		</g:javascript> 
	</head>
	<body>
		<a href="#list-itemRequests" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<%-- <ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<li><a class="logout" href="${createLink(uri: '/logout')}"><g:message code="default.logout.label" default="Logout"/></a></li>
			</ul> --%>
		<g:render template="navbar"></g:render>
		</div>
		<div id="list-itemRequests" class="content scaffold-list" role="main">
			<h1>Total Views</h1>
			
			<form action="categoryCount" id="logItemForm">
				<div id= "startDate">
				<%--Start: <g:datePicker name="startDate" precision="day" years="${2010..2050}" noSelection="['':'--']" default="none"/> --%>
				Start: <input type="datetime" class="datepicker" name="startDate" value="${startDate}" placeholder="dd/MM/yyyy"/>
				<span >
				<%-- End:  <g:datePicker name="endDate" precision="day" years="${2010..2050}" noSelection="['':'--']" default="none"/> --%>
				End: <input type="datetime" class="datepicker" name="endDate" value="${endDate}" placeholder="dd/MM/yyyy"/>
				</span>
				
				<div class="filter">
				<input name="submit" type="submit" value="Filter">
				</div>
				
				</div>
			</form>
			
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					<%-- 	<g:sortableColumn property="dateCreated" title="${message(code: 'itemRequests.dateCreated.label', default: 'Date Created')}" /> --%>
					<%-- 	<g:sortableColumn property="dateCreated" title="${message(code: 'record.dateCreated.label', default: 'Time Created')}" /> --%>
					<%--  	<g:sortableColumn property="itemSecretCode" title="${message(code: 'itemRequests.itemSecretCode.label', default: 'Item Secret Code')}" />  --%>
					<%-- 	<g:sortableColumn property="categoryId" title="${message(code: 'itemRequests.categoryId.label', default: 'Category Id')}" /> --%>
					<g:sortableColumn property="title" title="${message(code: 'itemRequests.title.label', default: 'Sets')}" />
					<th>Views</th>
					<%-- <g:sortableColumn property="ip" title="${message(code: 'itemRequests.ip.label', default: 'Ip')}" /> --%>
					<%-- 	<g:sortableColumn property="userId" title="${message(code: 'itemRequests.userId.label', default: 'User Id')}" /> --%>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${categoryInstanceList}" status="i" var="categoryInstance">
					
				<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					<%--	<td><g:link action = "show" id = "${itemRequestsInstance.id}"><g:formatDate type="time" date="${itemRequestsInstance.dateCreated}" /></g:link></td>  --%>
					<%-- <td><g:link action="show" id="${itemRequestsInstance.id}">${fieldValue(bean: itemRequestsInstance, field: "itemSecretCode")}</g:link></td>  --%>
						<td><g:link action="itemCount" id="${categoryInstanceId.get(categoryInstance.key)}" params="${[startDate: startDate, endDate: endDate]}">${categoryInstance.key}</g:link> </td> 
						<td>${categoryInstance.value}</td> 
					<%-- 	<td>${fieldValue(bean: itemRequestsInstance, field: "ip")}</td> --%>
					<%-- 	<td>${fieldValue(bean: itemRequestsInstance, field: "userId")}</td> --%>
				</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<%-- <g:paginate total="${itemRequestsInstanceTotal}"  /> --%>
			</div>
		</div>
	</body>
</html>