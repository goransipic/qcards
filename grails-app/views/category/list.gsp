
<%@ page import="com.Qcards.Category" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'category.label', default: 'Set')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-category" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<g:render template="navbar"/>
		</div>
		<div id="list-category" class="content scaffold-list" role="main">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
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

			
			<table>
				<thead>
					<tr>
						<g:sortableColumn property="title" title="${message(code: 'category.title.label', default: 'Title')}" />
						<th class="sortable">Options</th>
						<th class="sortable">Download</th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${categoryInstanceList}" status="i" var="categoryInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">					
						<td><g:link action="edit" params="[sort: 'dateCreated', order: 'desc', setPhoto: 'br']"id="${categoryInstance.id}">${fieldValue(bean: categoryInstance, field: "title")}</g:link></td>
						 <td> 
						 <g:if test="${categoryInstance?.items}">
	       			     <a id="pdf-link" class="cupid-blue" href="" onClick="javascript:window.open('<g:createLink controller="Category" action="renderPDF" id="${categoryInstance?.id}"/>','_blank','width=800, height=600')">Render PDF</a> 
	                     <%--  <a id="pdf-link" class="cupid-blue" href="" >Render PDF</a> --%>
	                     </g:if>
	                     </td>
	                     <td> 
						 <g:if test="${categoryInstance?.hasPdfFile}">
	                         <a id="pdf-link" class="cupid-blue" href="http://manas.hopto.org:8888/PHP/PDFGenerator/${categoryInstance.id}.pdf" >Download PDF </a>
	                     </g:if>
	                     </td>
	                 </tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${categoryInstanceTotal}"/>
			</div>  
		</div>
	</body>
</html>
