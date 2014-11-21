<%@ page import="com.Qcards.Item" %>

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'item.label', default: 'Item')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-item" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<%--<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>				
				<li><a class="logout" href="${createLink(uri: '/logout')}"><g:message code="default.logout.label" default="Logout"/></a></li>
			</ul>  --%>
		<g:render template="navbar"></g:render>
		</div>
		<div id="create-item" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${itemInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${itemInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" enctype="multipart/form-data">
					<g:if test="${slideShow==true}">
						<g:hiddenField name="url" value="" />
					</g:if>	
				<fieldset class="form">					
					<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'category', 'error')}">
						<label for="category">
							<g:message code="item.category.label" default="Category" />		
						</label>
							<g:if test="${select}">
							<g:select id="category" name="category.id" from="${com.Qcards.Category.list()}" optionKey="id"  value="${select}"  class="many-to-one"/>
							</g:if>
							<g:else>
							<g:select id="category" name="category.id" from="${com.Qcards.Category.list()}" optionKey="id"  value="${itemInstance?.category?.id}"  class="many-to-one"/>
							</g:else>
						</div>
					
					<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'title', 'error')} ">
						<label for="title">
							<g:message code="item.title.label" default="Title" />		
						</label>
						<g:textField name="title" value="${itemInstance?.title}"/>
					</div>
					
					<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'type', 'error')} ">
						<label for="type">
							<g:message code="item.type.label" default="Type" />		
						</label>	
						
						<g:select  name="type2" from="${Item.ItemType}"value = "${selectType}" onchange="${remoteFunction(action: 'create', params:'"type2=" + type2.value', update: 'update-div')}" />
					</div>
					
					<g:if test="${slideShow}">
						<div id="slideShowCreate"> 
						<g:each in="${itemInstanceList}" status="i" var="itemInstance">
						<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'type', 'error')} ">
								<%-- <input type="checkbox" name="${'items.'+i}" value="${itemInstance.id}"> ${itemInstance.title} --%>
								<g:checkBox name="${'items.'+i}" value="${i+'-'+itemInstance?.id}" checked="${inCreateAction==true ? '' : 'true'}"/> ${itemInstance?.title}
						</div>																																
						</g:each>
						</div>
					</g:if>
					
					<g:if test="${!slideShow}">
					<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'url', 'error')} ">
						<label for="url">
							<g:message code="item.url.label" default="Url" />							
						</label>
						<g:textField name="url" value="${itemInstance?.url}"/>
					</div>
					</g:if>
					
					<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'attribution', 'error')} ">
						<label for="attribution">
							<g:message code="item.attribution.label" default="Attribution" />							
						</label>
						<g:textArea name="attribution" value="${itemInstance?.attribution}"/>
					</div>
					
					<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'footer', 'error')} ">
						<label for="footer">
							<g:message code="item.footer.label" default="Footer" />
							
						</label>
						<g:textArea name="footer" value="${itemInstance?.footer}"/>
					</div>
					
					<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'orderIndex', 'error')}">
						<label for="orderIndex">
							<g:message code="item.orderIndex.label" default="Order Index" />
						</label>
						<g:textField name="orderIndex" value="${itemInstance?.orderIndex}" />
					</div>
					
					<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'orderIndex', 'error')}">
						<label for="image">
							<g:message code="item.Image.label" default="Photo" />
						</label>
						<input  type='file' name="photofile" />
					</div>
					
					<g:if test="${slideShow==null}">
						<g:if test ="${videoInput}">
						<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'videoInput', 'error')}">
							<label for="Video">
								<g:message code="item.Video.label" default="Video" />
							</label>
							<input  type='file' name="videofile" />
						</div>
						</g:if>
						<g:else>
							<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'orderIndex', 'error')}">
								<label for="Audio">
									<g:message code="item.Audio.label" default="Audio" />
								</label>
								<input  type='file' name="audiofile" />
							</div>
						</g:else>
					</g:if>
				</fieldset>
				
				<fieldset class="buttons">
					<g:submitButton name="create" class="save"   value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
