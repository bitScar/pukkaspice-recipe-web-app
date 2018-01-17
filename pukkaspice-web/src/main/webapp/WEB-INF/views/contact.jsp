<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
 
            <section class='section-wrapper contact-page-w'>
              <div class='container'>
                <h3 class='section-header'>Contact Page</h3>
                <div class='row'>
                  <div class='span4'>
                    <div class='blog-side-text-block widget-filled widget-yellow'>
                      <h3>Contact Information</h3>
                      <ul class="unstyled big-iconed-tips">
                        <li>
                          <i class='icon-map-marker'></i>
                          Address: Glasgow, Scotland
                        </li>
                        <li>
                          <i class='icon-globe'></i>
                          Website: www.pukkaspice.com
                        </li>
                      </ul>
                      <p>If you have any questions then please feel free to send us a message.</p>
                    </div>
                  </div>
                  <div class='span8'>
                    <div class="white-card extra-padding">
                    
                    <c:if test="${formSubmitted == true}">
	                    <div class="alert alert-success">
							<button class="close" type="button" name="button" dismiss="alert">x</button>
							Thank you, your message has been successfully sent to us.
						</div>
					</c:if>

                    <form:form method="post" modelAttribute="contactForm" action="contact" class='form-transparent no-margin-bottom' id='form-add-comment' >
						<spring:hasBindErrors name="contactForm">
							<c:forEach var="error" items="${errors.allErrors}">
								<div class="alert alert-error">
									<a class="close" data-dismiss="alert" href="#">&times;</a>
									<spring:message message="${error}" />
								</div>
							</c:forEach>
						</spring:hasBindErrors>

						<fieldset>
                        <div class='row-fluid'>
                          <div class='span12'>
                            <legend>Complete the form to contact us</legend>
                            <div class='controls controls-row'>
                              <form:input class='search-input span6' path="name" type="text" placeholder='Your name' maxlength="50" style="padding-bottom: 5px; padding-top: 5px;"/>
                              <form:input class='search-input span6' path="email" type="text" placeholder='Your email' maxlength="50" style="padding-bottom: 5px; padding-top: 5px;"/>
                            </div>
                            <div class='controls controls-row'>
                               <form:textarea path="message" class="span12" placeholder='Your message' rows='6' maxlength="500" />
                            </div>
                            <div class='form-actions'>
                              <button class='btn btn-small'>Submit Message</button>
                            </div>
                          </div>
                        </div>
                      </fieldset>
                    </form:form>
                    
                    </div>
                  </div>
                </div>
                <div class='row'>
                  <div class='span12'>
                    <iframe frameborder="0" scrolling="no" marginheight="0" marginwidth="0" width="100%" height="350" src="https://maps.google.com/maps?hl=en&q=G5 0be&ie=UTF8&t=roadmap&z=8&iwloc=B&output=embed"></iframe>
                  </div>
                </div>
              </div>
            </section>
 
    </tiles:putAttribute>
</tiles:insertDefinition>