<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/includes/taglib.jsp"%>
<link href="https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css"
	rel="stylesheet">
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


		<div class="col-push-3 col-md-6 login-form-2"
			style="position: relative;">
			<a
				style="position: absolute; top: 20px; left: 20px; display: block; width: 40px; height: 40px; color: white"
				href="login/index.htm"><i class='bx bx-arrow-back'
				style="font-size: 24px"></i> </a>
			<h3>Xác Nhận Tài Khoản</h3>
			<form action="login/forget.htm" class="login__form" method="POST">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Your Email *"
						value="" name="gmail" />
				</div>

				<div class="form-row mt-4 d-flex justify-content-center">
					<div class="form-group col-6 ">
						<input style="width: 100%" type="submit" class="btnSubmit"
							value="Xác nhận" />
					</div>

				</div>
				<c:if test="${mess != null }">
					<div class="alert alert-primary" role="alert">${mess }</div>
				</c:if>
				<c:if test="${messError != null }">
					<div class="alert alert-danger" role="alert">${messError }</div>
				</c:if>
			</form>
		</div>


	</div>
	<script src="<c:url value ='/resources/js/main.js'/>"></script>
</body>
</html>