<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<header id='header'>
	<div class='navbar navbar-fixed-top'>
		<div class='navbar-inner'>
			<div class='container'>
				<a class='btn btn-navbar' data-target='.nav-collapse' data-toggle='collapse'> <span class='icon-bar'></span> <span class='icon-bar'></span> <span class='icon-bar'></span></a> <a href="/" class="brand" style="color: #b55c6b; font-size: 32px;">Pukka Spice</a>
				<div class='nav-collapse subnav-collapse collapse pull-right' id='top-navigation'>
					
					<ul class='nav'>
						<li <c:if test="${active == 'home'}">class="active"</c:if>><a href="/">Home</a></li>
						<li <c:if test="${active == 'recipes'}">class="active"</c:if>><a href="/recipes">Recipes</a></li>
						<!-- 
						<li class='dropdown'><a href="#" class="dropdown-toggle" data-toggle="dropdown">Techniques</a>
							<ul class='dropdown-menu'>
								<li><a href="#">Get Started</a></li>
							</ul>
						</li>
						 -->
						<li <c:if test="${active == 'contact'}">class="active"</c:if>><a href="/contact">Contact</a></li>
					</ul>
					
					<div class='top-account-control visible-desktop'>
						<sec:authorize ifAnyGranted="ROLE_MEMBER">
							<a href="/secure/member/dashboard" class="top-create-account">Member Area</a>
							<a href="/secure/logout" style="background-color: #f1f3ea; color: #60635b;">Log out</a>
						</sec:authorize>
						<sec:authorize ifNotGranted="ROLE_MEMBER">
							<a href="/secure/join" class="top-create-account">Join For Free</a>
							<a href="#" class="top-sign-in">Sign In</a>
							<div class='login-box'>
								<a class='close login-box-close' href='#'>&times;</a>
								<h4 class='login-box-head'>Login Form</h4>

								<form action="/secure/pukka_security" method="post">
									<div class='control-group'>
										<label>Email Address</label> <input class='span2' name="emailAddress" placeholder='Input email address...' type='text'>
									</div>
									<div class='control-group'>
										<label>Password</label> <input class='span2' name="password" placeholder='Input password...' type='password'>
									</div>
									<div class='login-actions'>
										<button class='btn btn-primary' type="submit">Log Me In</button>
									</div>
								</form>
							</div>
						</sec:authorize>
					</div>
					
					<div class='top-account-control hidden-desktop'>
						<ul class='nav'>
							<sec:authorize ifAnyGranted="ROLE_MEMBER">
								<li><a href="/secure/member/dashboard" class="top-create-account">Member Area</a></li>
								<li><a href="/secure/logout">Log out</a></li>
							</sec:authorize>
							<sec:authorize ifNotGranted="ROLE_MEMBER">
								<li><a href="/secure/join">Join For Free</a></li>
								<li><a href="/secure/login" >Sign In</a></li>
							</sec:authorize>
						</ul>
					</div>
					
				</div>
			</div>
		</div>
	</div>
</header>