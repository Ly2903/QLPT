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
			<h3>Xác thực gmail</h3>
			<form action="login/authenticator.htm" method="post">
				<div class="form-group">
					<input type="text" class="form-control"
						placeholder="Mã xác thực" value="" name="code" />
				</div>
				<div class="form-group">
						<input style="width:100%" type="submit" class="btnSubmit" value="Xác nhận" />
					</div>
			</form>
		</div>
	</div>
	<script src="<c:url value ='/resources/js/main.js'/>"></script>
</body>
</html>