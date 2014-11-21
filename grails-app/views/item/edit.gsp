<%@ page import="com.Qcards.Item" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'item.label', default: 'Item')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
		
		<g:javascript>
			function checkItems(){
				
				var slideShowJSON = '${slideShowInstanceList.encodeAsJSON()}';
				var slideShowParsedJSON = eval(slideShowJSON);

				var tempCheckBox

				for(var i=0; i< slideShowParsedJSON.length; i++){
				  	console.log(slideShowParsedJSON[i].itemId);
				  	
				  	tempCheckBox = document.getElementById("items." + slideShowParsedJSON[i].itemId);
				  	
				  	console.log(tempCheckBox);
				  	tempCheckBox.checked = true;
				}
				
			}
		</g:javascript>
	</head>
	<body>
		<a href="#edit-item" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<%-- <ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" controller="category" action="edit" params="[id: itemInstance.category.id]"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				
				<li><g:link class="create" action="create" params="${[select:itemInstance?.category?.id]}"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				
				<li><a class="logout" href="${createLink(uri: '/logout')}"><g:message code="default.logout.label" default="Logout"/></a></li>
			</ul> --%>
			<g:render template="navbar"></g:render>
		</div>
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
		<div id="edit-item" class="content scaffold-edit" role="main">
			<h1>Item</h1>
			<div class="createSet">
			<ul>
				<li>
				<g:link class="list" controller="category" action="edit" params="[id: itemInstance.category.id]"><g:message code="default.list.label" args="[entityName]" /></g:link>
				</li>
				<li>
				<g:link class="create" action="create" id="${itemInstance?.category?.id}" params="${[select:itemInstance?.category?.id]}"><g:message code="default.new.label" args="[entityName]" /></g:link>
				</li>
				
			</ul>
			</div>
			</div>
			
		
			<g:form method="post" enctype="multipart/form-data">
				<g:hiddenField name="id" value="${itemInstance?.id}" />				
				<fieldset class="form">
					
					<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'category', 'error')}">
						<label for="category">
							<g:message code="item.category.label" default="Category" />		
						</label>
						<g:select id="category" name="category.id" from="${com.Qcards.Category.list()}" optionKey="id" value="${itemInstance?.category?.id}" class="many-to-one"/>
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
						<%-- <g:select name="type" from="${Item.ItemType}" value="${itemInstance?.type}" /> --%>
						<g:if test = "${selectType}">
						<g:select  name="type2" from="${Item.ItemType}"value = "${selectType}" onchange="${remoteFunction(action: 'edit', id:"${itemInstance?.id}" , params:'"type2=" + type2.value', update: 'update-div')}" />
						</g:if>
						<g:else>
						<g:select  name="type2" from="${Item.ItemType}"value = "${itemInstance?.type}" onchange="${remoteFunction(action: 'edit', id:"${itemInstance?.id}" , params:'"type2=" + type2.value', update: 'update-div')}" />
						</g:else>
					</div>
					
					<g:if test="${slideShow==true}">
						<div id="slideShowEdit"> 
						<g:each in="${itemInstanceList}" status="i" var="itemInstance">
						<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'type', 'error')} ">
								<%-- <input type="checkbox" name="${'items.'+i}" value="${itemInstance.id}"> ${itemInstance.title} --%>
								<g:checkBox name="${'items.'+itemInstance?.id}" value="${i+'-'+itemInstance?.id}" checked="false"/> ${itemInstance?.title}   <%-- checked="${emptyList[i]==1 ? 'true' : ''}" --%>
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
							
					<div id="photo_id" class="fieldcontain ">
						<label id="photo" for="image">
							<g:message code="item.Image.label" default="Photo" />
						</label>
							<input  type='file' name="photofile"/>
					</div>
					
					<div id="picture_id" class="fieldcontain ">
						<g:if test="${itemInstance.hasPhoto}">
							<label for="photo">
							<g:message code="item.photo.label" default="" />
							</label>
					 		<img width="300px" src="<g:createLink controller="item" action="getImage" params="${[code: itemInstance.code]}"/>" /> 
						</g:if>
					</div>
					
					<g:if test="${slideShow==null}">
						<g:if test ="${videoInput==true}">
						<div id="video" class="fieldcontain ">
							<label id="video" for="Video">
								<g:message code="item.Video.label" default="Video" />
							</label>
							<input  type='file' name="videofile" />
						</div>	
						
						<g:if test="${itemInstance.hasVideo}">
							<div id="video_id" class="fieldcontain ">						
									<label for="video">
									<g:message code="item.video.label" default="" />
									</label>
									<video controls="controls" width="300" >
		  							<source src="${scheme}://${serverName}${":8888"}/qcards/internalVideo/${itemInstance.code}.mp4" type="video/mp4">
		  							</video>						
							</div>
						</g:if>
						</g:if>
						<g:else>
						<div id="audio" class="fieldcontain ">
							<label id="audio" for="audio">
								<g:message code="item.Audio.label" default="Audio" />
							</label>
							<input  type='file' name="audiofile" />
						</div>	
						
						<g:if test="${itemInstance.hasAudio}">
							<div id="audio_id" class="fieldcontain ">						
									<label for="audio">
									<g:message code="item.audio.label" default="" />
									</label>
									<audio controls="controls">
		  							<source src="${scheme}://${serverName}${":8888"}/qcards/audio/${itemInstance.code}.ogg" type="audio/ogg">
		  							</audio>						
							</div>
						</g:if>
						</g:else>
				   </g:if>
					
					<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'code', 'error')}">
						<label for="code">
						<g:message code="item.code.label" default="Secret Code" />		
						</label>						
						<g:field type="text" name="name" readonly="readonly" value="${fieldValue(bean: itemInstance, field: 'code')}"/>
					</div>
					
					<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'orderIndex', 'error')}">
						<label for="orderIndex">
							<g:message code="item.orderIndex.label" default="Order Index" />
						</label>
						<g:textField name="orderIndex" value="${itemInstance.orderIndex}" />
					</div>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update"  params="${[id: itemInstance.id, type2: itemInstance.type]}" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
			
		</div>
	</body>
	
	<g:javascript>
			checkItems();
	</g:javascript>
</html>
