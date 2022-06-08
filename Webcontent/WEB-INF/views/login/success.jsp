<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/includes/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<base href="${pageContext.servletContext.contextPath}/" />
<link href="<c:url value='/resources/assets/bootstrap.min.css' />"
	rel="stylesheet" />
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&amp;display=swap"
	rel="stylesheet" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/css/loggin.css" />

<title>Login</title>
</head>
<body>
	<div class="container login-container">
		<div class="col-push-3 col-md-6 login-form-2">
			<div class="alert alert-success" role="alert">${message }</div>
			<div class="row d-flex justify-content-center">
				<a type="submit" href="login/index.htm"
					style="width: 50%; display: block; text-align: center"
					class="btnSubmit">OK</a>
			</div>


		</div>

	</div>
	<script src="<c:url value ='/resources/js/main.js'/>"></script>
</body>
</html>