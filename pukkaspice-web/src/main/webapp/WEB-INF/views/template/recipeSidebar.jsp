<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class='span4'>
	<div class='blog-side-bar'>
		<div class='blog-side-search widget-tp'>
			<h3>
				<i class='icon-search'></i> Search in Recipes
			</h3>
			<form:form method="post" modelAttribute="recipeSearchForm" action="/searchRecipes" class='no-margin-bottom form-search' >
				<fieldset>
					<div class='controls controls-row'>
						<form:input class='search-input span4' path="name" type="text" placeholder='Input search query' />
					</div>
				</fieldset>
			</form:form>
		</div>
		<div class='blog-categories widget-tp'>
			<h3>
				<i class='icon-th-list'></i> Recipe Categories
			</h3>
			<ul>
				<li>
					<a href="/recipes/starters/1">Starters</a>
				</li>
				<li>
					<a href="/recipes/main-meals/1">Main Meals</a></li>
				<li>
					<a href="/recipes/deserts/1">Deserts</a>
				</li>
				<li>
					<a href="/recipes/breads/1">Breads</a>
				</li>
				<li>
					<a href="/recipes/sauces/1">Sauces</a>
				</li>
				<li>
					<a href="/recipes/other/1">Other</a>
				</li>
			</ul>
		</div>
		<div class='blog-side-text-block widget-filled widget-yellow'>
			<h3>Do you have a recipe?</h3>
			<p>Why not join us in compiling some of the best recipes on the web. It's easy, just join or login and start putting your ideas for recipes right here.</p>
		</div>
	</div>
</div>