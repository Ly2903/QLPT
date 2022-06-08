<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/includes/header.jsp"%>
<link href="<c:url value='/resources/assets/bootstrap.min.css' />"
	rel="stylesheet" />
<style>
.container_cus {
	border: 1px solid #8d8d8d80;
	margin: 30px;
	width: 100%;
	padding: 30px;
}
</style>
<div class="app">
	<%@include file="/WEB-INF/views/includes/navbar.jsp"%>
	<div class="pseudo"></div>
	<main class="container_cus">
		<h2 class="room_name">Xác nhận thông tin khách vào phòng</h2>
		<hr />
		<c:choose>
			<c:when test="${k!=null }">
						<form:form method="POST" action="room/addCustomerOld/${maPhong}.htm"
			modelAttribute='k'>
			<form:input path="MAKT" style="display:none" />
			<div class="form-row col-8">
				<div class="form-group col-6">
					<label for="ho" class="room__label">Họ</label>
					<form:input id="ho" class="form-control" path="HO" readonly="true" />
				</div>
				<div class="form-group col-6">
					<label for="ho" class="room__label">Tên</label>
					<form:input id="ten" class="form-control" path="TEN" readonly="true"/>
				</div>
			</div>
			<div class="form-row  col-8">
				<div class="form-group col-6">
					<label for="namsinh" class="room__label">Năm sinh</label>
					<form:input id="namsinh" class="form-control" path="NAMSINH" readonly="true"/>
				</div>
				<div class="form-group col-6">
					<label for="gioitinh" class="room__label">Giới tính</label>
					<form:select id="gioitinh" class="form-control" path="GIOITINH"
						items="${gender}" disabled="true"/>
				</div>
			</div>

			<div class="form-row col-8">
				<div class="form-group col-6">
					<label for="cccd" class="room__label">CMND/CCCD</label>
					<form:input id="cccd" class="form-control" path="CCCD" readonly="true"/>
				</div>
				<div class="form-group col-6">
					<label for="email" class="room__label">Email</label>
					<form:input id="email" class="form-control" path="EMAIL" readonly="true"/>

				</div>
			</div>

			<div class="form-row col-8">
				<div class="form-group col-6">
					<label for="std" class="room__label">SĐT</label>
					<form:input id="sdt" class="form-control" path="SDT" readonly="true"/>
				</div>
				<div class="form-group col-6">
					<label for="nghenghiep" class="room__label">Nghề nghiệp</label>
					<form:input id="nghenghiep" class="form-control" path="NGHENGHIEP" readonly="true"/>
				</div>

			</div>
			<div class="col-8">
				<label for="diachi" class="room__label">Địa chỉ</label>
				<form:textarea id="diachi" class="form-control" path="DIACHI" readonly="true"/>
			</div>
		
			<div class="col-8">
				<a class="btn btn-warning mt-3 " href="room/index.htm"> <i
					class='bx bxs-chevrons-left'
					style="font-size: 18px; position: relative; top: 2px;"></i> Huỷ
				</a>

				<button type="submit" class="btn btn-success mt-3">
					Xác nhận <i class='bx bxs-chevrons-right'
						style="font-size: 18px; position: relative; top: 2px;"></i>
				</button>

			</div>
		</form:form>
							
			</c:when>
			<c:otherwise>
				<div class="alert alert-danger" role="alert">Không tồn tại khách có cccd như vậy </div>
			</c:otherwise>
		</c:choose>
	
	</main>
</div>
<%@include file="/WEB-INF/views/includes/footer.jsp"%>
