
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
			<h1>Item Log</h1>
			
			<form action="list" id="logItemForm">
				<span id= "startDate">
				<%--Start: <g:datePicker name="startDate" precision="day" years="${2010..2050}" noSelection="['':'--']" default="none"/> --%>
				Start: <input type="datetime" class="datepicker" name="startDate" value="${startDate}" placeholder="dd/MM/yyyy"/>
				</span>
				<span id= "endDate">
				<%-- End:  <g:datePicker name="endDate" precision="day" years="${2010..2050}" noSelection="['':'--']" default="none"/> --%>
				End: <input type="datetime" class="datepicker" name="endDate" value="${endDate}" placeholder="dd/MM/yyyy"/>
				</span>
				<span id= "specificCategory">
				Category: <g:select optionKey="id" name="specificCategory" from="${categoryList}"  noSelection="['':'--']" value = "${specificCategory?.id}"
								onchange="${remoteFunction(action: 'list', params: '"specificCategory=" + specificCategory.value + "&specificPartner=" + specificPartner.value + "&startDate=" + startDate.value + "&endDate=" + endDate.value', update: 'update-div')}"/>
				</span>
				<g:if test="${typeOfUser=="admin"}">
				<span id= "specificPartner">
				Partner: <g:select optionKey="id" name="specificPartner" from="${partnerList}" noSelection="['':'--']" value = "${specificPartner?.id}"
				onchange="${remoteFunction(action: 'list', params: '"specificCategory=" + specificCategory.value + "&specificPartner=" + specificPartner.value + "&startDate=" + startDate.value + "&endDate=" + endDate.value', update: 'update-div')}"/>
				</span>
				</g:if>
				<input name="submit" type="submit" value="Filter">
			</form>
			
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						
						<g:sortableColumn property="dateCreated" title="${message(code: 'itemRequests.dateCreated.label', default: 'Date Created')}" />
						
						<g:sortableColumn property="dateCreated" title="${message(code: 'record.dateCreated.label', default: 'Time Created')}" />
					<%--  	<g:sortableColumn property="itemSecretCode" title="${message(code: 'itemRequests.itemSecretCode.label', default: 'Item Secret Code')}" />  --%>
					
					<%-- 	<g:sortableColumn property="categoryId" title="${message(code: 'itemRequests.categoryId.label', default: 'Category Id')}" /> --%>
						<g:sortableColumn property="title" title="${message(code: 'itemRequests.title.label', default: 'Item')}" />
						
						<g:sortableColumn property="ip" title="${message(code: 'itemRequests.ip.label', default: 'Ip')}" />
					
					<%-- 	<g:sortableColumn property="userId" title="${message(code: 'itemRequests.userId.label', default: 'User Id')}" /> --%>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${itemRequestsInstanceList}" status="i" var="itemRequestsInstance">
					
					<g:if test="${ i == 0 || itemRequestsInstanceList[i].dateCreated.date != itemRequestsInstanceList[i - 1].dateCreated.date}">
						<tr class="date">
						<td><g:formatDate date="${itemRequestsInstance.dateCreated}" type="date" style="FULL"/></td>
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
						
						<td><g:link action = "itemHistory" params="${[item: itemRequestsInstance.itemSecretCode]}">${fieldValue(bean: itemRequestsInstance, field: "title")}</g:link></td>
						
						<td>${fieldValue(bean: itemRequestsInstance, field: "ip")}</td>
					
					<%-- 	<td>${fieldValue(bean: itemRequestsInstance, field: "userId")}</td> --%>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${itemRequestsInstanceTotal}" params="${[specificCategory: specificCategory?.id, specificPartner: specificPartner?.id, startDate:startDate, endDate:endDate]}" />
			</div>
		</div>
	</body>
</html>
