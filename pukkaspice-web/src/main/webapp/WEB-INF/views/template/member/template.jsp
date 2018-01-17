<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html ng-app="memberApp" class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html ng-app="memberApp" class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html ng-app="memberApp" class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html ng-app="memberApp" class="no-js"> <!--<![endif]-->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Pukka Spice</title>
  <meta name="description" content="A place where spice lovers vist">
  <meta name="viewport" content="width=device-width">
  
  <link href="/resources/assets/css/theme_venera.css" media="all" rel="stylesheet" type="text/css" />
  <link href="http://fonts.googleapis.com/css?family=Abel:400|Oswald:300,400,700" media="all" rel="stylesheet" type="text/css" />
  <link href="/resources/assets/css/pukka_spice.css" media="all" rel="stylesheet" type="text/css" />
  
  <!-- Third Part Modules -->
  <link rel="stylesheet" href="/resources/angluarjs/third-party-modules/angular-block-ui-master/dist/angular-block-ui.css"/>
  <link rel="stylesheet" href="/resources/angluarjs/third-party-modules/angular-ui-grid/ui-grid.min.css"/>
  
  <!-- Angular from Maven web jar -->
  <script src="/webjars/angularjs/1.4.7/angular.min.js"></script>
  <script src="/webjars/angularjs/1.4.7/angular-route.min.js"></script>
  <script src="/webjars/angularjs/1.4.7/angular-resource.min.js"></script>
  
  <!-- Third Party Modules -->
<!--   <script src="/resources/angluarjs/third-party-modules/ng-img-crop/ng-img-crop.js"></script> -->
<!--   <link href="/resources/angluarjs/third-party-modules/ng-img-crop/ng-img-crop.css" media="all" rel="stylesheet" type="text/css" /> -->

<script src="/resources/node_modules/ng-img-crop-full-extended/compile/minified/ng-img-crop.js"></script>
  <link href="/resources/node_modules/ng-img-crop-full-extended/compile/minified/ng-img-crop.css" media="all" rel="stylesheet" type="text/css" />
  
  <script src="/resources/angluarjs/third-party-modules/mask/mask.min.js"></script>
  <script src="/resources/angluarjs/third-party-modules/angular-block-ui-master/dist/angular-block-ui.js"></script>
  <script src="/resources/angluarjs/third-party-modules/angular-ui-grid/ui-grid.min.js"></script>
  
  <!-- PukkaSpice Modules -->
  <script src="/resources/angluarjs/modules.js"></script>
  <script src="/resources/angluarjs/services/data-share-service.js"></script>
  <script src="/resources/angluarjs/services/recipe-service.js"></script>
  <script src="/resources/angluarjs/services/user-service.js"></script>
  <script src="/resources/angluarjs/services/ErrorService.js"></script>
  
  <script src="/resources/angluarjs/controllers/add-recipe.js"></script>  
  <script src="/resources/angluarjs/controllers/sidebar.js"></script>
  <script src="/resources/angluarjs/controllers/profile.js"></script>
  <script src="/resources/angluarjs/controllers/my-recipes.js"></script>
  
  <script src="/resources/angluarjs/filters/TrueFalseFilter.js"></script>
  
  <link rel="shortcut icon" href="/resources/pukka-spice.ico" type="image/x-icon" />
  
  	<style type="text/css">
		.cropArea {
			background: #E4E4E4;
			overflow: hidden; 
			width: 280px;
			height: 280px;
		}
		
		.modal.modal-large {
			width: 800px;
			margin: -250px 0 0 -400px;
		}
		
		.watermark {
			position: absolute;
			top: 50%;
			transform: translateY(-50%);
			opacity: 0.25;
			font-size: 3em;
			width: 100%;
			text-align: center;
			z-index: 1000;
		}
		
		.recipeGrid {
			width: 730px;
			height: 350px;
		}
	</style>
  
</head>
<body>
<!--[if lt IE 7]>
    <p class="chromeframe">You are using an outdated browser. <a href=	"http://browsehappy.com/">Upgrade your browser today</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to better experience this site.</p>
<![endif]-->

   <tiles:insertAttribute name="header" />
   <tiles:insertAttribute name="body" />
   <tiles:insertAttribute name="footer" />

	<div id="alertModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3 id="myModalLabel">Error</h3>
		</div>
		<div class="modal-body">
			<h4>You are an error comunicating to the server!!!!</h4>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal">Close</button>
		</div>
	</div>

	<script src="/resources/assets/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="/resources/assets/js/bootstrap.js" type="text/javascript"></script>
	<script src="/resources/assets/js/prettify.js" type="text/javascript"></script>
	<script src="/resources/assets/js/lightbox.js" type="text/javascript"></script>
	<script src="/resources/assets/js/main.js" type="text/javascript"></script>

</body>
</html>