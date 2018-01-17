<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>


<style type="text/css">
	#main-section {
		display: flex;
/* 		flex-direction: row; */
		flex-flow: column;
 		justify-content: center;
		align-items: center; 
/* 		align-self: flex-start; */

		align-content: center;
	}
	
	#section-one {
		width: 150px;
		height: 150px;
		background-color: aqua;
		margin: 5px;
		
	}
	
	#section-two {
		width: 200px;
		height: 200px;
		background-color: blue;
		margin: 5px;
	}
	
	#section-three {
		background-color: green;
		margin: 5px;
	}
</style>

</head>
<body>

<h1>Hatton</h1>

<section id="main-section">

	<section id="section-one">
		Section One
	</section>
	
	<section id="section-two">
		Section Two
	</section>
	
	<section id="section-three">
		Section Three
	</section>
	
</section>
	

</body>
</html>