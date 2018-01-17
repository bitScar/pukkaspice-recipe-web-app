<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">

		<section class='section-wrapper create-account-page-w'>
			<div class='container'>
				<div class='row'>
					<div class='span5  offset3'>
						<div class='white-card extra-padding'>
							<form action="/secure/pukka_security" method="post">
								<fieldset>

									<div class='row-fluid'>
										<div class='span12'>
											<h1 class='form-header'>Members Area</h1>
										</div>
									</div>

									<c:if test="${passwordReset == true}">
										<div class='row'>
											<div class='span4'>
												<div class="alert alert-info">
													<a class="close" href="#" data-dismiss="alert">×</a>
													Your password has been reset. Please try logging in now.
												</div>
											</div>
										</div>
									</c:if>

									<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
										<div class='row'>
											<div class='span4'>
												<div class="alert alert-error">
													<a class="close" data-dismiss="alert" href="#">&times;</a>
													<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
												</div>
											</div>
										</div>
									</c:if>

									<div class='row'>
										<div class='span4'>
											<div class='control-group'>
												<label for="emailAddress">Email Address</label> <input type='text' class='span4' name="emailAddress" placeholder='Your email address...' />
											</div>
										</div>
									</div>
									<div class='row'>
										<div class='span4'>
											<%-- <div class='control-group ${status.error ? 'error' : ''}'> --%>
											<div class='control-group'>
												<label for="password">Password</label> <input type="password" class='span4' name="password" placeholder='Your Password...' />
											</div>
										</div>
									</div>
									<div class='row'>
										<div class='span4'>
											<a href="/secure/requestresetpassword" style="float: right">Have you forgot your password?</a>
										</div>
									</div>
									<div class='row-fluid'>
										<div class='span12'>
											<div class='form-actions no-margin-bottom'>
												<button class="btn btn-primary btn-large" name="button" type="submit">Log Me In</button>
											</div>
										</div>
									</div>
								</fieldset>
							</form>
						</div>
					</div>
				</div>
			</div>
		</section>

	</tiles:putAttribute>
</tiles:insertDefinition>