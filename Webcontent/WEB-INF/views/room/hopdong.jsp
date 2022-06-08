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
<style>
.container_cus {
	border: 1px solid #8d8d8d80;
	margin: 30px;
	width: 100%;
	padding: 30px;
}
</style>
</head>


<body>

	<div class="app">
		<%@include file="/WEB-INF/views/includes/navbar.jsp"%>
		<div class="pseudo"></div>
		<main class="container_cus">
			<h2 class="room_name">Tạo hợp đồng</h2>
			<hr />
			<div class="form_hopdong">
				<form:form method="POST" action="room/themHopDong.htm"
					modelAttribute='hopdong'>
					<c:if test="${message != null }">
						<div class="alert alert-warning" role="alert">${message}</div>
					</c:if>
					<form:input class="form-control" path="phong.MAPHONG"
						style="display:none" />
					<form:input class="form-control" path="DAHUY" style="display:none" />

					<form:input path="MAHOPDONG" style="display:none" />
					<div class="form-row col-8">
						<div class="form-group col-6">
							<label for="datepicker" class="room__label">Ngày ký</label>
							<form:input id="datepicker" class="form-control" path="NGAYKY" />
						</div>
						<div class="form-group col-6">
							<label for="datepicker1" class="room__label">Thời hạn</label>
							<form:input id="datepicker1" class="form-control" path="THOIHAN" />
						</div>

					</div>


					<div class="form-group col-8">
						<label for="tiencoc" class="room__label">Tiền cọc</label>
						<form:input id="tiencoc" class="form-control" path="TIENCOC" />
					</div>

					<div class="form-group col-8">
						<button type="submit" class="btn btn-success mt-3">
							<i class='bx bxs-check-circle'
								style="font-size: 18px; position: relative; top: 2px;"></i> Xong
						</button>
					</div>
				</form:form>

			</div>
		</main>
	</div>
	<script>
		console.log("dsadsa");
		$('#datepicker').datepicker({
			uiLibrary : 'bootstrap4'
		});
		$('#datepicker1').datepicker({
			uiLibrary : 'bootstrap4'
		});
	</script>
</body>


</html>
