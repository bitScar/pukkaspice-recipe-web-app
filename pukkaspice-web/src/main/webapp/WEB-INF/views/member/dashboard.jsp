<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>

<tiles:insertDefinition name="memberTemplate">
	<tiles:putAttribute name="body">
		<%-- Provides a stripped back ground for the whole body --%>
		<div class="just-stripped">

			<%-- Provides fixed width (1170px) container for main and sidebar --%>
			<div class='container'>
				<%-- Section wrapper just provides some padding top and bottom--%>
				<section class='section-wrapper'>
					<%-- Row spans 12 segments--%>
					<div class='row'>

						<div class="span8">
							<%-- Area used by Angular as card layout for pages--%>
							<ng-view />
						</div>

						<div class='span4' ng-controller="sideBarCtrl">
							<div class='blog-side-bar'>
								<div class='blog-categories widget-tp'>
									<h3>
										<i class='icon-th-list'></i> Your Pages
									</h3>
									<ul>
										<li ng-repeat="item in menuItem">
											<a href="{{item.url}}">{{item.name}}</a>
										<li>
									</ul>
								</div>
							</div>
						</div>
						
					</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>


