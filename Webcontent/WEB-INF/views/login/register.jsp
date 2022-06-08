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
<link href="https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&amp;display=swap"
	rel="stylesheet" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/css/loggin.css" />

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
		<div class="col-push-3 col-md-6 login-form-2"
			style="position: relative;">
			<a
				style="position: absolute; top: 20px; left: 20px; display: block; width: 40px; height: 40px; color: white"
				href="login/index.htm"><i class='bx bx-arrow-back'
				style="font-size: 24px"></i> </a>
			<h3>Đăng Ký</h3>

			<form:form action="login/register.htm" class="login__form"
				method="POST" modelAttribute="user" id="formLogin">
				<form:input path="MACT" style="display:none" />
				<div class="form-group">
					<form:input type="text" class="form-control" placeholder="Nhập họ "
						value="" path="HO" maxlength="40"/>
				</div>
				<div class="form-group">
					<form:input type="text" class="form-control"
						placeholder="Nhập tên " value="" path="TEN" maxlength="10"/>
				</div>
				<div class="form-group">
					<form:input type="text" class="form-control"
						placeholder="Số điện thoại " value="" path="SDT" maxlength="10" id="phone"/>
				</div>
				<div class="form-group">
					<form:input type="text" class="form-control	" placeholder="Gmail"
						value="" path="EMAIL" id="email" maxlength="50"/>
				</div>

				<div class="form-group" style="display: none">
					<form:input type="text" class="form-control" placeholder="Username"
						value="" path="TAIKHOAN" />
				</div>
				<div class="form-group">
					<div class="input-group" id="show_hide_password"
						style="position: relative;">
						<form:input class="form-control" type="password" path="PASSWORD"
							placeholder="Your password *" style="width: 100%;" maxlength="20" id="pw"/>
						<div class="input-group-addon"
							style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%); z-index: 10;">
							<a href=""><i class="fa fa-eye-slash" aria-hidden="true"></i></a>
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="input-group" id="show_hide_password"
						style="position: relative;">
						<input class="form-control" type="password" name="pwcf"
							placeholder="Your confirm password *" style="width: 100%;" maxlength="20"/>
						<div class="input-group-addon"
							style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%); z-index: 10;">
							<a href=""></a>
						</div>
					</div>
				</div>
				<c:if test="${ messError != null }">
					<div class="alert alert-warning" role="alert">${messError}</div>
				</c:if>

				<div class="form-group" style="margin-top: 30px">
					<button type="submit"
						style="width: 100%; display: block; text-align: center"
						class="btnSubmit">Đăng Ký</button>
				</div>


			</form:form>
		</div>

	</div>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous">
		
	</script>
	<script type="text/javascript">
		function validateEmail(value) {
			let regex = /([a-zA-Z0-9]+)([\_\.\-{1}])?([a-zA-Z0-9]+)\@([a-zA-Z0-9]+)([\.])([a-zA-Z\.]+)/g
			return regex.test(value) ? true : false;
		}
		function validatePhone(value){
            let regex=/^(0)[0-9]{9}$/g
            return regex.test(value)?true:false;
        }
		let formLogin = document.getElementById("formLogin");
	
		
		formLogin.onsubmit=( (e)=>
		{
			
			if(validateEmail(document.getElementById("email").value.trim()) == false)
				{
					e.preventDefault();
					alert("Email không đúng định dạng !")
					return;
				}
			if(validatePhone(document.getElementById("phone").value.trim()) == false)
			{
				e.preventDefault();
				alert("Số điện thoại không đúng !")
				return;
			}
			if(document.getElementById("pw").value.trim().length < 6)
			{
				e.preventDefault();
				alert("Mật khẩu ít nhất 6 ký tự !")
				return;
			}
				
		})
		
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