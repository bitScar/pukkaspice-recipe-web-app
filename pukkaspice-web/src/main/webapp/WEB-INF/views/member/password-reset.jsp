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

							<c:if test="${resetKeyValid == false}">
								<div class="alert alert-error">
									<button class="close" type="button" name="button" dismiss="error">x</button>
									Password reset time window has expired. Please enter email address for new reset instructions.
								</div>
							</c:if>

							<div class='row-fluid'>
								<div class='span12'>
									<h1 class='form-header'>Reset Password</h1>
								</div>
							</div>

							<%-- Request reset email address instructions --%>

							<c:choose>
								<c:when test="${formConfig == 'enterEmail'}">
									<form:form action="/secure/requestresetpassword" method="post" modelAttribute="passwordResetForm">
										<fieldset>

											<spring:hasBindErrors name="passwordResetForm">
												<c:if test="${errors.hasGlobalErrors()}">
													<div class="alert alert-error">
														<a class="close" data-dismiss="alert" href="#">&times;</a>
														<form:errors />
													</div>
												</c:if>
											</spring:hasBindErrors>

											<div class='row'>
												<div class='span4'>
													<spring:bind path="emailAddress">
														<div class='control-group ${status.error ? 'error' : ''}'>
															<form:label for="emailAddress" path="emailAddress">Email Address</form:label>
															<form:input type='text' class='span4' path="emailAddress" placeholder='Your email address...' maxlength="200" />
															<form:errors path="emailAddress" cssClass="help-inline" />
														</div>
													</spring:bind>
												</div>
											</div>

											<div class='row-fluid'>
												<div class='span12'>
													<div class='form-actions no-margin-bottom'>
														<button class="btn btn-primary btn-large" name="button" type="submit">Send</button>
													</div>
												</div>
											</div>
										</fieldset>
									</form:form>
								</c:when>

								<%-- Reset email address form --%>

								<c:when test="${formConfig == 'enterPassword'}">
									<form:form action="/secure/resetpassword" method="post" modelAttribute="passwordResetForm">
										<fieldset>

											<spring:hasBindErrors name="passwordResetForm">
												<c:if test="${errors.hasGlobalErrors()}">
													<div class="alert alert-error">
														<a class="close" data-dismiss="alert" href="#">&times;</a>
														<form:errors />
													</div>
												</c:if>
											</spring:hasBindErrors>

											<div class='row'>
												<div class='span4'>
													<spring:bind path="password">
														<div class='control-group ${status.error ? 'error' : ''}'>
															<form:label for="password" path="password">Password</form:label>
															<form:password class='span4' path="password" placeholder='Please Enter Your New Password...' maxlength="200"/>
															<form:errors path="password" cssClass="help-inline" />
														</div>
													</spring:bind>
												</div>
											</div>
											<div class='row'>
												<div class='span4'>
													<form:hidden path="resetKey" />
												</div>
											</div>
											<div class='row-fluid'>
												<div class='span12'>
													<div class='form-actions no-margin-bottom'>
														<button class="btn btn-primary btn-large" name="button" type="submit">Send</button>
													</div>
												</div>
											</div>
										</fieldset>
									</form:form>
								</c:when>

								<%-- Password reset email instruction message --%>

								<c:otherwise>
									<p>Please check your email for a password reset instructions.</p>
								</c:otherwise>
							</c:choose>

						</div>
					</div>
				</div>
			</div>
		</section>

	</tiles:putAttribute>
</tiles:insertDefinition>