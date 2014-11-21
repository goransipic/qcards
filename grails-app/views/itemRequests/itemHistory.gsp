<%@ page import="com.Qcards.ItemRequests" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'itemRequests.label', default: 'ItemRequests')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-itemRequests" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<%-- <ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			<!--  	<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li> -->
				<li><a class="logout" href="${createLink(uri: '/logout')}"><g:message code="default.logout.label" default="Logout"/></a></li>
			</ul> --%>
		<g:render template="navbar"></g:render>
		</div>
		<div id="list-itemRequests" class="content scaffold-list" role="main">
			<h1>${itemRequestsInstanceList[0].title} Log</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						
						<g:sortableColumn property="dateCreated" params="${[item:itemSecretCode]}" title="${message(code: 'itemRequests.dateCreated.label', default: 'Date Created')}" />
						
						<g:sortableColumn property="dateCreated" params="${[item:itemSecretCode]}" title="${message(code: 'record.dateCreated.label', default: 'Time Created')}" />
					<%--  	<g:sortableColumn property="itemSecretCode" title="${message(code: 'itemRequests.itemSecretCode.label', default: 'Item Secret Code')}" />  --%>
					
					<%-- 	<g:sortableColumn property="categoryId" title="${message(code: 'itemRequests.categoryId.label', default: 'Category Id')}" /> --%>
						<g:sortableColumn property="title" params="${[item:itemSecretCode]}" title="${message(code: 'itemRequests.title.label', default: 'Item')}" />
						
						<g:sortableColumn property="ip" params="${[item:itemSecretCode]}" title="${message(code: 'itemRequests.ip.label', default: 'Ip')}" />
					
					<%-- 	<g:sortableColumn property="userId" title="${message(code: 'itemRequests.userId.label', default: 'User Id')}" /> --%>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${itemRequestsInstanceList}" status="i" var="itemRequestsInstance">
					
					<g:if test="${ i == 0 || itemRequestsInstanceList[i].dateCreated.date != itemRequestsInstanceList[i - 1].dateCreated.date}">
						<tr class="date">
						<td><g:link action="show" id="${itemRequestsInstance.id}"><g:formatDate date="${itemRequestsInstance.dateCreated}" type="date" style="FULL"/></g:link></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						</tr>
					</g:if>
					
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td></td>
						<td><g:link action = "show" id = "${itemRequestsInstance.id}"><g:formatDate type="time" date="${itemRequestsInstance.dateCreated}" /></g:link></td>
						
					<%-- <td><g:link action="show" id="${itemRequestsInstance.id}">${fieldValue(bean: itemRequestsInstance, field: "itemSecretCode")}</g:link></td>  --%>
					
					<%-- 	<td>${fieldValue(bean: itemRequestsInstance, field: "categoryId")}</td> --%>
						
						<td>${fieldValue(bean: itemRequestsInstance, field: "title")}</td>
						
						<td>${fieldValue(bean: itemRequestsInstance, field: "ip")}</td>
					
					<%-- 	<td>${fieldValue(bean: itemRequestsInstance, field: "userId")}</td> --%>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${itemRequestsInstanceTotal}" params="${[specificCategory: specificCategory?.id, specificPartner: specificPartner?.id]}" />
			</div>
		</div>
	</body>
</html>