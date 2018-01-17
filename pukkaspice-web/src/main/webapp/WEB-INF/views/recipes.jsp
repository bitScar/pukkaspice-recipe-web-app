<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">

		<section class='section-wrapper posts-w'>
			<div class='container'>
				<div class='row'>
				
					<div class='span8'>
					
						<!-- recipes head section -->
						<div class='row'>
							<div class='span8'>
								<c:choose>
								   <c:when test="${recipeSummaries.size() > 0}">
								   		<h3 class='section-header'><c:out value="${category.getPageName()}" /></h3>
								   </c:when>
								   <c:otherwise>
										<blockquote style="margin-top: 60px">
							            <p>We don't appear to have anything that matches your search.</p>
							            <small>Why not add a recipe when you get a chance</small>
							            </blockquote>
								   </c:otherwise>
								</c:choose>
							</div>
						</div>
						
						<!-- render each of the recipes -->
						<c:forEach items="${recipeSummaries}" var="r" varStatus="myIndex">
						
							<%-- create a row for 2 recipes --%>
							<c:if test="${myIndex.count % 2 == 1}">
								<c:out value="<div class='row'>" escapeXml="false"></c:out>
							</c:if>

							<div class='span4'>
								<div class='white-card recent-post clearfix'>
									<h5 class='recent-post-header'>
										<a href="${r.getSeoFriendlyRecipeUrl()}"><c:out value="${r.name}"/></a>
									</h5>
									<div class='post-info clearfix'>
										<div class='pull-left'>
											<span class='post-date'>
												<fmt:formatDate type="date" dateStyle="long" timeStyle="long" value="${r.datePublished}" />
											</span>
										</div>
									</div>
									<img alt="Photo-card-big-1" class="post-image" src="${r.getImageUrl()}" />
									<p class='post-content'><c:out value="${r.getShortDescription()}"/></p>
									<a href="${r.getSeoFriendlyRecipeUrl()}" class="btn btn-primary btn-extra pull-right">Recipe &gt;</a>
								</div>
							</div>

							<%-- close the row if we get 2 recipes in a row or we have displayed all the recipes --%>
							<c:if test="${myIndex.count == recipeSummaries.size() || myIndex.count % 2 == 0}">
								<c:out value="</div>" escapeXml="false"></c:out>
							</c:if>
						</c:forEach>

						<div class='row'>
							<div class='span8'>
							
								<c:if test="${recipeSummaries.size() > 0 && pageCount > 0}">
									<h3 class='section-header'>Pages</h3>
								</c:if>
								
								<div class='pagination'>
									<ul>
										<c:set var="startPage" value="1" />
										<c:set var="endPage" value="${pageCount}" />
										<c:forEach begin="${startPage}" end="${endPage}" step="1" varStatus="loopStatus">
											<c:if test="${startPage == loopStatus.count && currentPage > 1}">
												<li><a href="/recipes/${category.getLinkName()}/${currentPage - 1}">Prev</a></li>
											</c:if>
												<li <c:if test="${loopStatus.count == currentPage}">class='active'</c:if>>
													<a href="/recipes/${category.getLinkName()}/${loopStatus.count}" <c:if test="${loopStatus.count == currentPage}">onclick='return false'</c:if>>${loopStatus.count}</a>
												</li>
											<c:if test="${currentPage != endPage && endPage == loopStatus.count}">
												<li><a href="/recipes/${category.getLinkName()}/${currentPage + 1}">Next</a></li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</div>

					<%@ include file="/WEB-INF/views/template/recipeSidebar.jsp"%>

				</div>
			</div>
		</section>

	</tiles:putAttribute>
</tiles:insertDefinition>


