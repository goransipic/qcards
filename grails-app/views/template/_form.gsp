<%@ page import="com.Qcards.Template" %>
<%-- 
<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'categories', 'error')} ">
	<label for="categories">
		<g:message code="template.categories.label" default="CategoryID" />
	</label>
	<g:select id="category" name="category.id" from="${com.Qcards.Category.list()}" optionKey="id"  value=""  class="many-to-one"/>
</div>
--%>
<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="template.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${templateInstance?.name}"/>
</div>

<g:if test="${typeOfUser == 'admin'}">
<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'userId', 'error')}">
	<label for="userId">
		<g:message code="user.label" default="Partner" />		
	</label>
	<%--  <g:textField name="userId" value="${currentUser?.id}"/> --%>
	 <%-- <g:select id="user" name="user.id" from="${com.Qcards.User.list()}" optionKey="id"  value="${templateInstance?.user?.id}"  class="many-to-one"/> --%>
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
	<%-- <g:textField name="titleFontFamily" value="Comic"/> --%>
	<g:select name="titleFontFamily" from="${['Comic', 'Tahoma']}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'titlefontFontSize', 'error')} ">
	<label for="titleFontSize">
		<g:message code="template.titleFontSize.label" default="Font Size" />
	</label>
	<g:textField name="titleFontSize" value="20"/>
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
	<g:textField name="titleColorRed" value="185"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'titleColorGreen', 'error')} ">
	<label for="titleColorGreen">
		<g:message code="template.titleColorGreen.label" default="Color Green" />
		
	</label>
	<g:textField name="titleColorGreen" value="197"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'titleColorBlue', 'error')} ">
	<label for="titleColorBlue">
		<g:message code="template.titleColorBlue.label" default="Color Blue" />
		
	</label>
	<g:textField name="titleColorBlue" value="152"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'titleTextAlign', 'error')} ">
	<label for="titleTextAlign">
		<g:message code="template.titleTextAlign.label" default="Text Align" />
	</label>
	<g:textField name="titleTextAlign" value="C"/>
	<label id ="labeltextalign1">
		<g:message code="template.labeltextalign.label" default="(LCR)" />
	</label>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'titleOffsetX', 'error')} ">
	<label for="titleOffsetX">
		<g:message code="template.titleOffsetX.label" default="Offset X" />
		
	</label>
	<g:textField name="titleOffsetX" value="2"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'titleOffsetY', 'error')} ">
	<label for="titleOffsetY">
		<g:message code="template.titleOffsetY.label" default="Offset Y" />
		
	</label>
	<g:textField name="titleOffsetY" value="74"/>
</div>
<h1>
		<g:message code="template.footer.label" default="Footer" />
</h1>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'footerFontFamily', 'error')} ">
	<label for="footerFontFamily">
		<g:message code="template.footerFontFamily.label" default="Font Family" />
		
	</label>
	<%--<g:textField name="footerFontFamily" value="Tahoma"/>  --%>
	<g:select name="footerFontFamily" from="${['Comic', 'Tahoma']}" value="Tahoma" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'footerFontSize', 'error')} ">
	<label for="footerFontSize">
		<g:message code="template.footerFontSize.label" default="Font Size" />
		
	</label>
	<g:textField name="footerFontSize" value="14"/>
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
	<g:textField name="footerColorRed" value="156"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'footerColorGreen', 'error')} ">
	<label for="footerColorGreen">
		<g:message code="template.footerColorGreen.label" default="Color Green" />
		
	</label>
	<g:textField name="footerColorGreen" value="159"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'footerColorBlue', 'error')} ">
	<label for="footerColorBlue">
		<g:message code="template.footerColorBlue.label" default="Color Blue" />
		
	</label>
	<g:textField name="footerColorBlue" value="187"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'footerTextAlign', 'error')} ">
	<label for="footerTextAlign">
		<g:message code="template.footerTextAlign.label" default="Text Align" />
	</label>
	<g:textField name="footerTextAlign" value="C"/>
	<label id ="labeltextalign2">
		<g:message code="template.labeltextalign.label" default="(LCR)" />
	</label>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'footerOffsetX', 'error')} ">
	<label for="footerOffsetX">
		<g:message code="template.footerOffsetX.label" default="Offset X" />
		
	</label>
	<g:textField name="footerOffsetX" value="2"/>
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'footerOffsetY', 'error')} ">
	<label for="footerOffsetY">
		<g:message code="template.footerOffsetY.label" default="Offset Y" />
		
	</label>
	<g:textField name="footerOffsetY" value="84"/>
</div>

<h1>
		<g:message code="template.footer.label" default="Template pictures" />
</h1>
<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'Templatefront', 'error')}">
	<label for="templatefront">
		<g:message code="template.templatefront.label" default="Template Front" />
	</label>
	<input  type='file' name="templatefront" />
</div>

<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'Templateback', 'error')}">
	<label for="templateback">
		<g:message code="item.templateback.label" default="Template Back" />
	</label>
	<input  type='file' name="templateback" />
</div>

<div class="fieldcontain ${hasErrors(bean: templateInstance, field: 'photoOffsetY', 'error')} ">
	<label for="photoOffsetY">
		<g:message code="template.photoOffsetY.label" default="Photo Offset Y" />
		
	</label>
	<g:textField name="photoOffsetY" value="2"/>
</div>


