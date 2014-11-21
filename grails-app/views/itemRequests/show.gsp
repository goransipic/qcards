
<%@ page import="com.Qcards.ItemRequests" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'itemRequests.label', default: 'ItemRequests')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-itemRequests" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<%-- <ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			 	<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li> 
			<li><a class="logout" href="${createLink(uri: '/logout')}"><g:message code="default.logout.label" default="Logout"/></a></li>
			</ul>  --%>
		<g:render template="navbar"></g:render>	
		</div>
		<div id="show-itemRequests" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<div class="createSet">
			<ul>
				<li>
				<li><g:link class="list" action="list">Report List</g:link></li>
				</li>
			</ul>
			</div>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list itemRequests">
			
				<g:if test="${itemRequestsInstance?.itemSecretCode}">
				<li class="fieldcontain">
					<span id="itemSecretCode-label" class="property-label"><g:message code="itemRequests.itemSecretCode.label" default="Item Secret Code" /></span>
					
						<span class="property-value" aria-labelledby="itemSecretCode-label"><g:fieldValue bean="${itemRequestsInstance}" field="itemSecretCode"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${itemRequestsInstance?.categoryId}">
				<li class="fieldcontain">
					<span id="categoryId-label" class="property-label"><g:message code="itemRequests.categoryId.label" default="Category Id" /></span>
					
						<span class="property-value" aria-labelledby="categoryId-label"><g:fieldValue bean="${itemRequestsInstance}" field="categoryId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${itemRequestsInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="itemRequests.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${itemRequestsInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${itemRequestsInstance?.ip}">
				<li class="fieldcontain">
					<span id="ip-label" class="property-label"><g:message code="itemRequests.ip.label" default="Ip" /></span>
					
						<span class="property-value" aria-labelledby="ip-label"><g:fieldValue bean="${itemRequestsInstance}" field="ip"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${itemRequestsInstance?.userId}">
				<li class="fieldcontain">
					<span id="userId-label" class="property-label"><g:message code="itemRequests.userId.label" default="User Id" /></span>
					
						<span class="property-value" aria-labelledby="userId-label"><g:fieldValue bean="${itemRequestsInstance}" field="userId"/></span>
					
				</li>
				</g:if>
			
			</ol>
	<%-- 	<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${itemRequestsInstance?.id}" />
					<g:link class="edit" action="edit" id="${itemRequestsInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form> --%>	
		</div>
	</body>
</html>
