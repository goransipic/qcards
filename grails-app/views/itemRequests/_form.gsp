<%@ page import="com.Qcards.ItemRequests" %>



<div class="fieldcontain ${hasErrors(bean: itemRequestsInstance, field: 'itemSecretCode', 'error')} ">
	<label for="itemSecretCode">
		<g:message code="itemRequests.itemSecretCode.label" default="Item Secret Code" />
		
	</label>
	<g:textField name="itemSecretCode" maxlength="12" value="${itemRequestsInstance?.itemSecretCode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: itemRequestsInstance, field: 'categoryId', 'error')} required">
	<label for="categoryId">
		<g:message code="itemRequests.categoryId.label" default="Category Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="categoryId" type="number" value="${itemRequestsInstance.categoryId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: itemRequestsInstance, field: 'ip', 'error')} ">
	<label for="ip">
		<g:message code="itemRequests.ip.label" default="Ip" />
		
	</label>
	<g:textField name="ip" value="${itemRequestsInstance?.ip}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: itemRequestsInstance, field: 'userId', 'error')} required">
	<label for="userId">
		<g:message code="itemRequests.userId.label" default="User Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="userId" type="number" value="${itemRequestsInstance.userId}" required=""/>
</div>

