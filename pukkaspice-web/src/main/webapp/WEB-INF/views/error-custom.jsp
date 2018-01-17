<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<section class='section-wrapper not-found-w'>
			<div class='container'>
				<div class='row'>
					<div class='span4'>
						<img alt="Not-found-error" src="/resources/assets/images/global-error.png" />
					</div>
					<div class='span8'>
						<h1>Oops, ${errorCode} - ${errorMessage} </h1>
						<p>Let's try one of the following remedies to get you back on track...</p>
						<div class='not-found-solutions btn-group'>
							<a href="/contact" class="btn"><i class='icon-comment'></i> Report error to admin </a> 
							<a href="/" class="btn"><i class='icon-home'></i> Go to the homepage </a> 
						</div>
					</div>
				</div>
			</div>
		</section>
	</tiles:putAttribute>
</tiles:insertDefinition>