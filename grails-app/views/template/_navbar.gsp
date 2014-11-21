<ul>
	<li>
		<a class="home" href="${createLink(uri: '/')}">Sets</a>
	</li>
	<g:if test="${typeOfUser== 'admin'}">
	<li>
		<g:link class="newpartner" controller="user" action="index">Partners</g:link>
	</li>
	</g:if>
	<li><g:link class="template" controller="Template" action="index"><g:message code="default.template.label" default="Templates"/></g:link></li>
	<%-- <li><g:link class="template" controller="ItemRequests" action="index">Reports</g:link></li> --%>
	<li>
	<div id="dd" class="wrapper-dropdown-5" tabindex="1"><a href="#" id="report">Reports</a>
		    <ul class="dropdown">
		        <li><g:link controller="ItemRequests" action="index"><i class="icon-user"></i>Item Log</g:link></li>
		        <li><g:link controller="ItemRequests" action="categoryCount"><i class="icon-cog"></i>Total Views</g:link></li>
		    </ul>
		</div>
	</li>
	<li><a class="logout" href="${createLink(uri: '/logout')}"><g:message code="default.logout.label" default="Logout"/></a></li>
</ul>