<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
	<c:choose>
		<c:when test="${title != null}">
			<title>${title}</title>
		</c:when>
		<c:otherwise>
			<title>Pukka Spice</title>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${metaDescription != null}">
			<meta name="description" content="${metaDescription}">
		</c:when>
		<c:otherwise>
			<meta name="description" content="Recipe, blogs, and tools to help you create your next meal. We are pasonate about food, flavour, and ingredients used in cusines from all around the world. We try to provide you with the best resouces for your kitchen experiements.">
		</c:otherwise>
	</c:choose>

	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	
	<meta name="viewport" content="width=device-width">
	
	<link href="/resources/assets/css/theme_venera.css" media="all" rel="stylesheet" type="text/css" />
	<link href="http://fonts.googleapis.com/css?family=Abel:400|Oswald:300,400,700" media="all" rel="stylesheet" type="text/css" />
	<link href="/resources/assets/css/pukka_spice.css" media="all" rel="stylesheet" type="text/css" />
	
	<link rel="shortcut icon" href="/resources/pukka-spice.ico" type="image/x-icon" />
	
	<script>
		(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		})(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
		
		ga('create', 'UA-79580484-1', 'auto');
		ga('send', 'pageview');
	</script>
	  
	<meta name="google-site-verification" content="q33wlz58x1tpVLCPK4SjTnGeAnYwsQ0fFT6eo7PT57U" />
</head>
<body>
	<!--[if lt IE 7]>
    	<p class="chromeframe">You are using an outdated browser. <a href=	"http://browsehappy.com/">Upgrade your browser today</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to better experience this site.</p>
	<![endif]-->

	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="body" />
	<tiles:insertAttribute name="footer" />

	<script src="/resources/assets/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="/resources/assets/js/bootstrap.js" type="text/javascript"></script>
	<script src="/resources/assets/js/prettify.js" type="text/javascript"></script>
	<script src="/resources/assets/js/lightbox.js" type="text/javascript"></script>
	<script src="/resources/assets/js/main.js" type="text/javascript"></script>

	<script type="text/javascript">
        $(document).ready(function() {
            $(".alert").alert();
        });
    </script>
</html>
