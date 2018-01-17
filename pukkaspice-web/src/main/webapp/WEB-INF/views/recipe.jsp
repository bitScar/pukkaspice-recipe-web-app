<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false"%>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">

		<section class='section-wrapper post-w'>
		
			<div class='container'>
				<div class='row'>
					<div class='span8'>
						<div class='single-post-w'>
						
							<h1 class='post-title'><c:out value="${recipe.name}" /></h1>
							<p class='post-info'>
								<span class='info-item'> by <c:out value="${recipe.userSummary.forename} ${recipe.userSummary.surname}" /></span> 
								<span class='info-item'><fmt:formatDate type="date" dateStyle="long" timeStyle="long" value="${recipe.datePublished}" /></span> 
								<span class='info-item'>Prep Time: <c:out value="${recipe.prepTime}" /> mins</span> 
								<span class='info-item'>Cook Time: <c:out value="${recipe.cookTime}" /> mins</span> 
								<span class='last-info-item'>Yield: <c:out value="${recipe.recipeYield}" /></span> 
							</p>
							
							<div class="post-content row">
								<div class="span-12">
									<p><c:out value="${recipe.description}" /></p>
								</div>
							</div>
							
							<div class='post-content row'>
								<div class="span-6">
									<div class='single-post-image bottom decor-image'>
										<img width="350" alt="<c:out value="${recipe.name}" /> Photo" src="${recipe.getImageUrl()}" />
									</div>
								</div>
								<div class="span-6">
									<h2 style="margin-top: 0px;">Ingredients</h2>
									<dl class="dl-horizontal">
										<c:forEach items="${recipe.ingredients}" var="i">
											<dt><c:out value="${i.quantity} ${i.measurement.shortName}" /></dt>
											<dd><c:out value="${i.description}" /></dd>
										</c:forEach>
									</dl>
								</div>
								<div style="clear: both;"></div>
							</div>
							
							<div class='post-content'>
								<h2>Instructions</h2>
								<dl class="dl-horizontal">
									<c:forEach items="${recipe.recipeInstructions}" var="i" varStatus="myIndex">
										<dt class="dt-instruction">${myIndex.index + 1} </dt>
										<dd><c:out value="${i.description}" /></dd>
									</c:forEach>
								</dl>
													
								<c:if test="${not empty recipe.alternativeIdeas}">
									<h3>Alternative Ideas</h3>
									<p><c:out value="${recipe.alternativeIdeas}" /></p>
								</c:if>
							</div>
							
							${recipe.getJsonLd()}
		
						</div> <!-- single-post-w -->
						
						<div class='post-author-w'>
							<h3 class='section-header'>About Author</h3>
							<div class='row'>
								<div class='span1'>
									<div class='post-author-photo hidden-phone'>
										<img alt="Avatar-1" src="${recipe.userSummary.getImageUrl()}" />
									</div>
								</div>
								<div class='span7'>
									<div class='post-author-bio-w'>
										<div class='post-author-name'>
											<c:out value="${recipe.userSummary.forename} ${recipe.userSummary.surname}" />
										</div>
										<div class='post-author-bio'>
											<c:out value="${recipe.userSummary.profileDescription}" />
										</div>
									</div>
								</div>
							</div>
						</div>
						
					</div> <!-- span8 -->
					
					<%@ include file="/WEB-INF/views/template/recipeSidebar.jsp" %>
					
				</div> <!-- row -->
			</div> <!-- container -->
		</section>


	</tiles:putAttribute>
</tiles:insertDefinition>


