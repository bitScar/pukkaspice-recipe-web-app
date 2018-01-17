<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">

		<section class='section-wrapper create-account-page-w'>
			<div class='container'>
				<div class='row'>
					<div class='span9'>
						<div class='white-card extra-padding'>
							<form:form action="/secure/join" method="post" modelAttribute="joinForm">
								<fieldset>
									<div class='row-fluid'>
										<div class='span12'>
											<h1 class='form-header'>Join the Spicers</h1>
										</div>
									</div>

									<spring:hasBindErrors name="joinForm">
										<c:if test="${errors.hasGlobalErrors()}">
											<div class="alert alert-error">
												<a class="close" data-dismiss="alert" href="#">&times;</a>
												<form:errors />
											</div>
										</c:if>
									</spring:hasBindErrors>

									<div class='row'>
										<div class='span4'>
											<spring:bind path="firstName">
												<div class='control-group ${status.error ? 'error' : ''}'>
													<form:label for="firstName" path="firstName">First Name</form:label>
													<form:input type='text' class='span4' path="firstName" placeholder='Your first name...' />
													<form:errors path="firstName" cssClass="help-inline" />
												</div>
											</spring:bind>
										</div>
										<div class='span4'>
											<spring:bind path="surname">
												<div class='control-group ${status.error ? 'error' : ''}'>
													<form:label for="surname" path="surname">Last Name</form:label>
													<form:input type='text' class='span4' path="surname" placeholder='Your last name...' />
													<form:errors path="surname" cssClass="help-inline" />
												</div>
											</spring:bind>
										</div>
									</div>
									<div class='row'>
										<div class='span4'>
											<spring:bind path="emailAddress">
												<div class='control-group ${status.error ? 'error' : ''}'>
													<form:label for="emailAddress" path="emailAddress">Email Address</form:label>
													<form:input type='text' class='span4' path="emailAddress" placeholder='Your email address...' />
													<form:errors path="emailAddress" cssClass="help-inline" />
												</div>
											</spring:bind>
										</div>
										<div class='span4'>
											<spring:bind path="password">
												<div class='control-group ${status.error ? 'error' : ''}'>
													<form:label for="password" path="password">Password</form:label>
													<form:password class='span4' path="password" placeholder='Your Password...' />
													<form:errors path="password" cssClass="help-inline" />
												</div>
											</spring:bind>
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
							</form:form>
						</div>
					</div>
					<div class='span3'>
						<div class='blog-side-text-block widget-filled widget-yellow'>
							<h3>Our Membership:</h3>
							<ul class='big-iconed-tips'>
								<li><i class='icon-credit-card'></i> It is completely free</li>
<!-- 								<li><i class='icon-lock'></i> 100% Secured</li> -->
								<li><i class='icon-thumbs-up'></i> Lots of fun</li>
							</ul>
							<p>PukkaSpice is all about spices and cooking. That's our passion and all we want to share it with you. We do not spam, and we do not share any membership details - EVER!</p>
						</div>
					</div>
				</div>
			</div>
		</section>

	</tiles:putAttribute>
</tiles:insertDefinition>