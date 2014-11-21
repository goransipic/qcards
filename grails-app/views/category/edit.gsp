<%@ page import="com.Qcards.Category" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-category" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
		
		<g:render template="navbar"></g:render>
		<%-- 	<ul>
				
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<g:if test="${ typeOfUser == 'admin' }">
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				</g:if>
				<li><g:link class="template" controller="Template" action="index"><g:message code="default.template.label" default="Templates"/></g:link></li>
				<li><a class="logout" href="${createLink(uri: '/logout')}"><g:message code="default.logout.label" default="Logout"/></a></li>
			</ul> --%>
		</div>
		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${categoryInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${categoryInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
		<div id="edit-category" class="content scaffold-edit" role="main">
				<h1>Set</h1>
				<div class="createSet">
				<ul>
					<li><g:link class="create" action="create">New Set</g:link></li>
				</ul>
				</div>
			
			<%-- <h1>Sets</h1> --%>
			<g:form method="post" enctype="multipart/form-data">
				<g:hiddenField name="id" value="${categoryInstance?.id}" />
				<g:hiddenField name="orderIndex" value="${categoryInstance?.orderIndex}" />				
				<g:if test="${typeOfUser == 'partner'}">
				<g:hiddenField name="userId" value="${currentUser.id}" />
				</g:if>
				<fieldset class="form">
					<div id="picture_id" class="fieldcontain ">
						<g:if test="${categoryInstance.hasPhoto}">
							<label for="photo">
							<g:message code="item.photo.label" default="" />
							</label>
					 		 		<img width="300px" src="<g:createLink controller="category" action="getImage" params="${[setpicture: categoryInstance.id+".jpg"]}"/>" /> 
						</g:if>
					</div>
					<g:render template="form"/>
				</fieldset>		
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />					
				</fieldset>		
			</g:form>
		</div>
		<div id="items" ${hasErrors(bean: categoryInstance, field: 'items', 'error')} ">
			<label for="items">
				<g:message code="category.items.label" default="Items" />		
			</label>
			<g:link class="create" controller="item" action="create" id="${categoryInstance?.id}" params="['category.id': categoryInstance?.id ]">${message(code: 'default.add.label', args: [message(code: 'item.label', default: 'Item')])}</g:link>
		
			<g:if test="${categoryInstance?.items}">
				<table>
					<thead>
						<tr>
						<th class="sortable">*</th>
						<g:sortableColumn property="title"title="${message(code: 'item.title.label', default: 'Title')}" />
						<g:sortableColumn property="type" title="${message(code: 'item.type.label', default: 'ItemType')}" />
						<g:sortableColumn property="url" title="${message(code: 'item.url.label', default: 'URL')}" />
						<g:sortableColumn property="code" title="${message(code: 'item.code.label', default: 'Code')}" />
						<th class="sortable">Multimedia</th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${items}" status="i" var="itemInstance">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							<td>${i+1}</td>
							<td><g:link controller="item" action="edit" id="${itemInstance.id}" params="${[id: itemInstance.id, type2: itemInstance.type]}" > ${fieldValue(bean: itemInstance, field: "title")}</g:link></td>
							<td>${fieldValue(bean: itemInstance, field: "type")}</td>
							<td><a href="${fieldValue(bean: itemInstance, field: "url")}">${fieldValue(bean: itemInstance, field: "url")}</a></td>
							<td>${fieldValue(bean: itemInstance, field: "code")}</td>
							<td>
							<g:if test="${itemInstance.hasPhoto}">
							<g:img dir="images" file="paint-brush.jpg" width="20"/>
							</g:if>
							<g:if test="${itemInstance.hasAudio}">		
							<g:img dir="images" file="headset.jpg" width="20"/>
							</g:if>
							<g:if test="${itemInstance.hasVideo}">		
							<g:img dir="images" file="video.jpg" width="20"/>
							</g:if>
							</td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
				<g:paginate total="${itemsTotal}"  params="${[id:categoryInstance?.id]}"/>
				</div> 		
			</g:if>			
		</div>
		
	</body>
</html>
