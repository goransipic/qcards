<%@ page import="com.Qcards.Category"%>

<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="category.title.label" default="Title" />		
	</label>
	<g:textField name="title" value="${categoryInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'descriptionOfSet', 'error')} ">
	<label for="descriptionOfSet">
		<g:message code="category.descriptionOfSet.label" default="Description" />		
	</label>
	<g:textArea name="descriptionOfSet" value="${categoryInstance?.descriptionOfSet}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'authorName', 'error')} ">
	<label for="authorName">
		<g:message code="category.authorName.label" default="Author Name" />		
	</label>
	<g:textField name="authorName" value="${categoryInstance?.authorName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'authorLink', 'error')} ">
	<label for="authorLink">
		<g:message code="category.authorLink.label" default="Author Link" />		
	</label>
	<g:textField name="authorLink" value="${categoryInstance?.authorLink}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'orderIndex', 'error')}">
	<label for="Template">
		<g:message code="category.template.label" default="Template" />		
	</label>
	<g:select id="template" name="template.id" from="${templateElements}" optionKey="id"  value="${categoryInstance?.template?.id}"  class="many-to-one"/>
</div>
<g:if test="${typeOfUser == 'admin'}">
<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'orderIndex', 'error')}">
	<label for="User">
		<g:message code="user.label" default="Partner" />		
	</label>
	<g:select id="user" name="user.id" from="${userElements}" optionKey="id"  value="${categoryInstance?.user?.id}"  class="many-to-one"/>
</div>
</g:if>
<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'hasPhoto', 'error')}">
	<label for="image">
		<g:message code="item.photoset.label" default="Photo" />
	</label>
	<input  type='file' name="setPhoto" />
</div>

