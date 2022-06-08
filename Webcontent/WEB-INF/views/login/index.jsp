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
<link href='https://unpkg.com/boxicons@2.1.2/css/boxicons.min.css'
	rel='stylesheet'>


<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.1.0/css/all.css"
	integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt"
	crossorigin="anonymous">

<title>Login</title>
</head>
<body>
	<div class="container login-container">
		<div class="col-push-3 col-md-6 login-form-2">
			<h3>Đăng nhập</h3>

			<form action="login/index.htm" class="login__form" method="POST">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Your Email *"
						value="" name="TAIKHOAN" />
				</div>
				<div class="form-group">
					<div class="input-group" id="show_hide_password" style="position: relative;">
						<input class="form-control" type="password" name="PASSWORD"  placeholder="Your password *" style="width: 100%;">
						<div class="input-group-addon" style="position: absolute;right: 10px;top: 50%;transform: translateY(-50%);z-index: 10;">
							<a href=""><i class="fa fa-eye-slash" aria-hidden="true"></i></a>
						</div>
					</div>
				</div>
				<div class="form-row mt-4">
					<div class="form-group col-6">
						<input style="width: 100%" type="submit" class="btnSubmit"
							value="Đăng nhập" />
					</div>
					<div class="form-group col-6">
						<a style="width: 100%; display: block; text-align: center"
							href="login/register.htm" class="btnSubmit">Đăng Ký</a>
					</div>

				</div>

				<div class="form-group">

					<a href="login/forget.htm" class="ForgetPwd" value="Login">Quên mật khẩu</a>
				</div>
				<c:if test="${message != null }">
					<div class="alert alert-danger" role="alert">${message }</div>
				</c:if>
			</form>

		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous">
		
	</script>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$("#show_hide_password a")
									.on(
											'click',
											function(event) {
												event.preventDefault();
												if ($(
														'#show_hide_password input')
														.attr("type") == "text") {
													$(
															'#show_hide_password input')
															.attr('type',
																	'password');
													$('#show_hide_password i')
															.addClass(
																	"fa-eye-slash");
													$('#show_hide_password i')
															.removeClass(
																	"fa-eye");
												} else if ($(
														'#show_hide_password input')
														.attr("type") == "password") {
													$(
															'#show_hide_password input')
															.attr('type',
																	'text');
													$('#show_hide_password i')
															.removeClass(
																	"fa-eye-slash");
													$('#show_hide_password i')
															.addClass("fa-eye");
												}
											});
						});
	</script>
</body>
</html>