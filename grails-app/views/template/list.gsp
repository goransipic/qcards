
<%@ page import="com.Qcards.Template" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'template.label', default: 'Template')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-template" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
		<%-- 	<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create" ><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<li><a class="logout" href="${createLink(uri: '/logout')}"><g:message code="default.logout.label" default="Logout"/></a></li>
			</ul> --%>
			<g:render template="navbar"></g:render>
		</div>
		<div id="list-template" class="content scaffold-list" role="main">
		<div>
			<div class="container">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			</div>
			<div class="createSet">
			<ul>
				<li>
				<g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
				</li>
			</ul>
			</div>
		</div>
			<%-- <h1><g:message code="default.list.label" args="[entityName]" /></h1> --%>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					<g:sortableColumn property="name" title="${message(code: 'template.name.label', default: 'Name')}" />
					<th class="sortable">Front/Back Photo</th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${templateInstanceList}" status="i" var="templateInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					<td><g:link action="edit" id="${templateInstance.id}">${fieldValue(bean: templateInstance, field: "name")}</g:link></td>
					<td>
						<g:if test="${templateInstance.hasFrontPhoto}">
						<span class="templatepictures"><g:img dir="images" file="ok.jpg" width="20"/></span>
						</g:if>
						<g:else>
						<span class="templatepictures"><g:img dir="images" file="no.png" width="20"/></span>
						</g:else>
						<g:if test="${templateInstance.hasBackPhoto}">		
						<span class="templatepictures"><g:img dir="images" file="ok.jpg" width="20"/></span>
						</g:if>
						<g:else>
						<span class="templatepictures"><g:img dir="images" file="no.png" width="20"/></span>
						</g:else>
						</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${templateInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
