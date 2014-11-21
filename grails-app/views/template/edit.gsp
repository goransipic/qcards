<%@ page import="com.Qcards.Template" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'template.label', default: 'Template')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-template" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<%-- <ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create" params="[id: templateInstance.userId]"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<li><a class="logout" href="${createLink(uri: '/logout')}"><g:message code="default.logout.label" default="Logout"/></a></li>
			</ul> --%>
	<g:render template="navbar"></g:render>
		</div>
		<div id="edit-template" class="content scaffold-edit" role="main">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${templateInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${templateInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<div class="createSet">
			<ul>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create" params="[id: templateInstance.userId]"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
			</div>
			
<g:form method="post" enctype="multipart/form-data">
	<g:hiddenField name="id" value="${templateInstance?.id}" />
	<g:hiddenField name="version" value="${templateInstance?.version}" />
		<g:if test="${typeOfUser == 'partner'}">
			<g:hiddenField name="userId" value="${currentUser?.id}" />
		</g:if>
	<fieldset class="form">
	<%-- 	<g:render template="form"/> --%>
<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="template.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${templateInstance?.name}"/>
</div>
<g:if test="${typeOfUser == 'admin'}">
<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'template', 'error')}">
 	<label for="User">
		<g:message code="user.label" default="Partner" />		
	</label>
	<g:select id="user" name="user.id" from="${userElements}" optionKey="id"  value="${templateInstance?.user?.id}"  class="many-to-one"/> 
</div>
</g:if>
<h1>
	<g:message code="template.title.label" default="Title" />
</h1>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'titleFontFamily', 'error')} ">
	<label for="titleFontFamily">
		<g:message code="template.titleFontFamily.label" default="Font Family" />
	</label>
	<g:textField name="titleFontFamily" value="${templateInstance?.titleFontFamily}"/>
	<%-- <g:select name="titleFontFamily" from="${['Comic', 'Tahoma']}"  value="${templateInstance?.titleFontFamily}" class="many-to-one"/>  --%>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'titlefontFontSize', 'error')} ">
	<label for="titleFontSize">
		<g:message code="template.titleFontSize.label" default="Font Size" />
	</label>
	<g:textField name="titleFontSize" value="${templateInstance?.titleFontSize}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'titleFontStyle', 'error')} ">
	<label for="titleFontStyle">
		<g:message code="template.titleFontStyle.label" default="Font Style" />
	</label>
	<g:textField name="titleFontStyle" value="${templateInstance?.titleFontStyle}"/>
	<label id ="labelfontstyle1" for="labelfontstyle">
		<g:message code="template.labelfontstyle.label" default="(BIU)" />
	</label>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'titleColorRed', 'error')} ">
	<label for="titleColorRed">
		<g:message code="template.titleColorRed.label" default="Color Red" />
	</label>
	<g:textField name="titleColorRed" value="${templateInstance?.titleColorRed}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'titleColorGreen', 'error')} ">
	<label for="titleColorGreen">
		<g:message code="template.titleColorGreen.label" default="Color Green" />
		
	</label>
	<g:textField name="titleColorGreen" value="${templateInstance?.titleColorGreen}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'titleColorBlue', 'error')} ">
	<label for="titleColorBlue">
		<g:message code="template.titleColorBlue.label" default="Color Blue" />
		
	</label>
	<g:textField name="titleColorBlue" value="${templateInstance?.titleColorBlue}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'titleTextAlign', 'error')} ">
	<label for="titleTextAlign">
		<g:message code="template.titleTextAlign.label" default="Text Align" />
	</label>
	<g:textField name="titleTextAlign" value="${templateInstance?.titleTextAlign}"/>
	<label id ="labeltextalign1">
		<g:message code="template.labeltextalign.label" default="(LCR)" />
	</label>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'titleOffsetX', 'error')} ">
	<label for="titleOffsetX">
		<g:message code="template.titleOffsetX.label" default="Offset X" />
		
	</label>
	<g:textField name="titleOffsetX" value="${templateInstance?.titleOffsetX}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'titleOffsetY', 'error')} ">
	<label for="titleOffsetY">
		<g:message code="template.titleOffsetY.label" default="Offset Y" />
		
	</label>
	<g:textField name="titleOffsetY" value="${templateInstance?.titleOffsetY}"/>
</div>
<h1>
		<g:message code="template.footer.label" default="Footer" />
</h1>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'footerFontFamily', 'error')} ">
	<label for="footerFontFamily">
		<g:message code="template.footerFontFamily.label" default="Font Family" />
	</label>
	<g:textField name="footerFontFamily" value="${templateInstance?.footerFontFamily}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'footerfontFontSize', 'error')} ">
	<label for="footerFontSize">
		<g:message code="template.footerFontSize.label" default="Font Size" />
		
	</label>
	<g:textField name="footerFontSize" value="${templateInstance?.footerFontSize}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'footerFontStyle', 'error')} ">
	<label for="footerFontStyle">
		<g:message code="template.footerFontStyle.label" default="Font Style" />
	</label>
	<g:textField name="footerFontStyle" value="${templateInstance?.footerFontStyle}"/>
	<label id ="labelfontstyle2" for="labelfontstyle">
		<g:message code="template.labelfontstyle.label" default="(BIU)" />
	</label>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'footerColorRed', 'error')} ">
	<label for="footerColorRed">
		<g:message code="template.footerColorRed.label" default="Color Red" />
		
	</label>
	<g:textField name="footerColorRed" value="${templateInstance?.footerColorRed}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'footerColorGreen', 'error')} ">
	<label for="footerColorGreen">
		<g:message code="template.footerColorGreen.label" default="Color Green" />
		
	</label>
	<g:textField name="footerColorGreen" value="${templateInstance?.footerColorGreen}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'footerColorBlue', 'error')} ">
	<label for="footerColorBlue">
		<g:message code="template.footerColorBlue.label" default="Color Blue" />
		
	</label>
	<g:textField name="footerColorBlue" value="${templateInstance?.footerColorBlue}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'footerTextAlign', 'error')} ">
	<label for="footerTextAlign">
		<g:message code="template.footerTextAlign.label" default="Text Align" />
	</label>
	<g:textField name="footerTextAlign" value="${templateInstance?.footerTextAlign}"/>
	<label id ="labeltextalign2">
		<g:message code="template.labeltextalign.label" default="(LCR)" />
	</label>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'footerOffsetX', 'error')} ">
	<label for="footerOffsetX">
		<g:message code="template.footerOffsetX.label" default="Offset X" />
		
	</label>
	<g:textField name="footerOffsetX" value="${templateInstance?.footerOffsetX}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'footerOffsetY', 'error')} ">
	<label for="footerOffsetY">
		<g:message code="template.footerOffsetY.label" default="Offset Y" />
		
	</label>
	<g:textField name="footerOffsetY" value="${templateInstance?.footerOffsetY}"/>
</div>

<h1>
		<g:message code="template.footer.label" default="Template pictures" />
</h1>
<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'Templatefront', 'error')}">
	<label for="templatefront">
		<g:message code="template.templatefront.label" default="Template Front" />
	</label>
	<input  type='file' name="templatefront" />
</div>

<div id="picture_id" class="fieldcontain ">
	<g:if test="${templateInstance.hasFrontPhoto}">
		<label for="photo">
		<g:message code="template.photo.label" default="" />
		</label>
 		<img width="300px" src="<g:createLink controller="template" action="getImage" params="${[templatepicture: templateInstance.id+"f.png"]}"/>" /> 
	</g:if>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'Templateback', 'error')}">
	<label for="templateback">
		<g:message code="template.templateback.label" default="Template Back" />
	</label>
	<input  type='file' name="templateback" />
</div>

<div id="picture_id" class="fieldcontain ">
	<g:if test="${templateInstance.hasBackPhoto}">
		<label for="photo">
		<g:message code="template.photo.label" default="" />
		</label>
 		<img width="300px" src="<g:createLink controller="template" action="getImage" params="${[templatepicture: templateInstance.id+"b.png"]}"/>" /> 
	</g:if>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'photoOffsetY', 'error')} ">
	<label for="photoOffsetY">
		<g:message code="template.photoOffsetY.label" default="Photo Offset Y" />
		
	</label>
	<g:textField name="photoOffsetY" value="${templateInstance?.photoOffsetY}"/>
</div>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
