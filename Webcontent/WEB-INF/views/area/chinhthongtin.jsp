<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<base href="${pageContext.servletContext.contextPath}/" />
<title>Quản lý nhà trọ</title>
<link rel="stylesheet"
	href="<c:url value ='/resources/css/styles.css' />" />
<link rel="stylesheet" href="<c:url value ='/resources/css/grid.css' />" />

<link rel="stylesheet" href="<c:url value ='/resources/css/room.css' />" />

<link href="https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css"
	rel="stylesheet" />
<link rel="preconnect" href="https://fonts.googleapis.com" />
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&amp;display=swap"
	rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js"
	type="text/javascript"></script>
<link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css"
	rel="stylesheet" type="text/css" />


<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.1.0/css/all.css"
	integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt"
	crossorigin="anonymous">
<style>
.container_cus {
	border: 1px solid #8d8d8d80;
	margin: 30px;
	width: 100%;
	padding: 30px;
}
.hide
{
	display : none;
}
.active
{
	display: block;
}
</style>
</head>


<body>

	<div class="app">
		<%@include file="/WEB-INF/views/includes/navbar.jsp"%>
		<div class="pseudo"></div>
		<main class="container_cus">
			<h2 class="room_name">Chỉnh thông tin chủ trọ</h2>
			<hr />
			<c:if test="${mess != null }">
				<div class="alert alert-primary" role="alert">${mess}</div>
			</c:if>
			<div>
				<form:form action="area/chinhthongtin/${sessionScope.mact}.htm"
					method="POST" modelAttribute="chutro" id="formChinhTT">
					<form:input class="form-control" path="MACT" style="display:none" />
					<form:input class="form-control" path="PASSWORD"
						style="display:none" />
					<form:input class="form-control" path="TAIKHOAN"
						style="display:none" />
					<div class="form-row col-12">
						<div class="form-group col-6">
							<label for="ho" class="room__label">Họ</label>
							<form:input id="ho" class="form-control" path="HO"
							 />
						</div>
						<div class="form-group col-6">
							<label for="ho" class="room__label">Tên</label>
							<form:input id="ten" class="form-control" path="TEN"
							/>
						</div>
					</div>
					<div class="form-row col-12">
						<div class="form-group col-6">
							<label for="namsinh" class="room__label">Năm sinh</label>
							<form:input id="namsinh" class="form-control" path="NAMSINH" />
						</div>
						<div class="form-group col-6">
							<label for="ho" class="room__label">Giới Tính</label>
							<form:select id="gioitinh" class="form-control" path="GIOITINH"
								items="${gender}" />
						</div>
					</div>
					<div class="form-row col-12">
						<div class="form-group col-6">
							<label for="cccd" class="room__label">Căn cước công dân</label>
							<form:input id="cccd" class="form-control" path="CCCD"/>
						</div>
						<div class="form-group col-6">
							<label for="diachi" class="room__label">Địa chỉ</label>
							<form:input id="diachi" class="form-control" path="DIACHI" />
						</div>
					</div>
					<div class="form-row col-12">
						<div class="form-group col-6">
							<label for="email" class="room__label">Email</label>
							<form:input id="email" class="form-control" path="EMAIL"
								readonly="true" />
						</div>
						<div class="form-group col-6">
							<label for="sdt" class="room__label">SĐT</label>
							<form:input id="sdt" class="form-control" path="SDT"
								readonly="true" />
						</div>
					</div>
					<div class="form-row col-12">
						<button type="button" class="btn btn-link" id="changePassword">Đổi
							mật khẩu</button>
					</div>
					<div id="formChangePass" class="hide">
					
						<div class="form-row col-12">
							<div class="form-group col-6">
							<label for="mkht" class="room__label">Mật khẩu hiện tại</label> <input
								id="mkht" class="form-control" name="passht" />
							</div>
							<div class="form-group col-6">
								<label for="password" class="room__label">Mật khẩu mới</label>
								<div class="input-group" id="show_hide_password"
									style="position: relative;">
									<input class="form-control" type="password" name="pass"
										style="width: 100%;" id="pw"/>
									<div class="input-group-addon"
										style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%); z-index: 10;">
										<a href=""><i class="fa fa-eye-slash" aria-hidden="true"></i></a>
									</div>
								</div>
							</div>
							
						</div>
					
					</div>


					<button type="submit" class="btn btn-success mt-3">
						Hoàn thành <i class='bx bxs-chevrons-right'
							style="font-size: 18px; position: relative; top: 2px;"></i>
					</button>
				</form:form>
			</div>
		</main>
	</div>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous">
		
	</script>
	<script type="text/javascript">
		let checkChangePass = 0;
		document.getElementById("formChinhTT").onsubmit=( (e)=>
		{
			if(document.getElementById("namsinh").value<1900 || document.getElementById("namsinh").value>2022){
	    		e.preventDefault();
	    		alert("Năm sinh không hợp lệ !")
	    		return;
	    	} 
	    	if(document.getElementById("cccd").value.length !=12){
	    		e.preventDefault();
	    		alert("CCCD phải đúng 12 số!")
	    		return;
	    	}
			console.log("oke");
			if((document.getElementById("pw").value.trim().length < 6 || document.getElementById("mkht").value.trim().length < 6) && checkChangePass == 1)
			{
				console.log("oke");
				e.preventDefault();
				alert("Mật khẩu ít nhất 6 ký tự !")
			}
		})
	</script>
	<script>
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
							document.getElementById("changePassword")
									.addEventListener("click",
											displayChangeForm);
							function displayChangeForm() {
								document.getElementById("formChangePass").classList.toggle("active");
								if(document.getElementById("formChangePass").classList[1] == "active")
									checkChangePass = 1;
								else
									checkChangePass = 0;
							}
						}

				);
	</script>
</body>


</html>
