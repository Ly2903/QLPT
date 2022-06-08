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
		<c:choose>
			<c:when test="${formHide == null }">
				<h2 class="room_name">Danh sách loại phòng</h2>
				<hr />
				<c:if test="${message != null }">
					<div class="alert alert-primary" role="alert">${message}</div>
				</c:if>
				<div class="mb-4 d-flex flex-row-reverse">
					<a href="room/typeroom/index.htm?linkAdd"
						style="margin-right: 10px">
						<button type="button" class="btn btn-primary">
							<i class='bx bx-plus-medical'></i> Thêm Loại Phòng
						</button>
					</a>
				</div>
				<table class="table table-striped  table-bordered">
					<thead>
						<tr style="text-align: center">
							<th scope="col">Tên loại</th>
							<th scope="col">Số người tối đa</th>
							<th scope="col">Diện tích</th>
							<th scope="col">Giá phòng(/1 tháng)</th>
							<th scope="col">Mô tả</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="loaiPhong" items="${loaiPhong}">
							<tr>
								<td style="width: 15%">${loaiPhong.TENLOAI }</td>
								<td style="width: 15%">${loaiPhong.SLNGUOITD}</td>
								<td style="width: 15%">${loaiPhong.DIENTICH }</td>

								<td style="width: 15%"><fmt:formatNumber type="currency"
										currencySymbol="đ" value="${loaiPhong.DONGIA}" /></td>
								<td style="width: 30%">${loaiPhong.MOTA }</td>
								<td style="text-align: center;"><a
									href="room/typeroom/index/${loaiPhong.MALOAI}.htm?linkDelete"><i
										class='bx bx-folder-minus' style="font-size: 18px;"></i></a></td>
								<td style="text-align: center;"><a
									href="room/typeroom/index/${loaiPhong.MALOAI}.htm?linkEdit"><i
										class='bx bx-edit' style="font-size: 18px;"></i></a></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
				
			</c:when>
			<c:otherwise>
				<h2 class="mt-4">Thêm loại phòng</h2>
				<hr />
				<form:form modelAttribute="loaiPhong" method="POST"
					action="room/typeroom/index.htm">
					<form:input path="MALOAI" style="display:none" />
					<div class="form-group col-8">
						<label for="tenloai" class="room__label">Tên Loại</label>
						<form:input class="form-control" id="tenloai" path="TENLOAI"></form:input>
						<form:errors path="TENLOAI" />
					</div>

					<div class="form-row col-8">

						<div class="form-group col-6">
							<label for="slntd" class="room__label">Số lượng người tối
								đa</label>
							<form:input type="number" class="form-control" id="slntd" path="SLNGUOITD"></form:input>
							<form:errors path="SLNGUOITD" />
						</div>
						<div class="form-group col-6">
							<label for="dientich" class="room__label">Diện Tích</label>
							<form:input type="number" class="form-control" id="dientich" path="DIENTICH"></form:input>
							<form:errors path="DIENTICH" />
						</div>
					</div>


					<div class="form-group col-8">
						<label for="dongia" class="room__label">Giá tiền / 1 tháng
						</label>
						<form:input type="number" class="form-control" id="dongia" path="DONGIA"></form:input>
						<form:errors path="DONGIA" />
					</div>


					<div class="form-group col-8">
						<label for="MoTa" class="room__label">Mô Tả</label>
						<form:textarea class="form-control" id="MoTa" rows="4" path="MOTA"></form:textarea>
						<form:errors path="MOTA" />

					</div>
					<button type="submit" name="${btnStatus}"
						class="btn btn-success mt-3">
						<i class='bx bx-save'
							style="font-size: 18px; position: relative; top: 2px;"></i> Lưu
					</button>
				</form:form>
			</c:otherwise>
		</c:choose>

	</main>
</div>
<%@include file="/WEB-INF/views/includes/footer.jsp"%>
