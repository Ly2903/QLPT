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

		<div class="room">
			<c:choose>
				<c:when test="${ sessionScope.mact != null}">
					<p>Đã đăng nhập ${mact }</p>
				</c:when>
				<c:otherwise>
					<p>Chưa đăng nhập</p>
				</c:otherwise>
			</c:choose>
			<h2 class="room_name">Xem những người đang ở tại phòng</h2>
			<hr />

			<c:if test="${message != null }">
				<div class="alert alert-primary" role="alert">${message}</div>
			</c:if>


			<c:if test="${mess != null }">
				<div class="alert alert-primary" role="alert">${mess}</div>
			</c:if>
			<c:choose>
				<c:when test="${khach != null }">

					<div class="mb-4 d-flex flex-row-reverse">
						<c:if test="${r.trangThai.MATT != 3 }">
							<div class="ml-2">
								<a href="room/addCustomer/${maPhong}.htm">
									<button type="button" class="btn btn-warning">Thêm
										người</button>
								</a>
							</div>
						</c:if>


						<div class="ml-2">
							<a href="room/traphong/${maPhong}.htm" class="btnTraPhong">
								<button type="button" class="btn btn-danger">Trả phòng</button>
							</a>
						</div>
						<div class="ml-2">
							<a href="room/giahanhopdong/${maPhong}.htm">
								<button type="button" class="btn btn-success">Gia hạn
									hợp đồng</button>
							</a>
						</div>

						<div class="ml-2">
							<a href="room/xemhopdong/${maPhong}.htm">
								<button type="button" class="btn btn-primary">Xem hợp
									đồng</button>
							</a>
						</div>





					</div>
					<table class="table table-striped  table-bordered">
						<thead>
							<tr style="text-align: center">
								<th scope="col">Họ</th>
								<th scope="col">Tên</th>
								<th scope="col">Năm sinh</th>
								<th scope="col">Giới tính</th>
								<th scope="col">CCCD</th>
								<th scope="col">Địa chỉ</th>
								<th scope="col">Email</th>
								<th scope="col">Phone</th>
								<th scope="col">Nghề nghiệp</th>
								<th scope="col">Thôi ở</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="r" items="${khach}">
								<tr>
									<td style="width: 8%">${r.HO}</td>
									<td style="width: 8%">${r.TEN }</td>
									<td style="width: 8%">${r.NAMSINH}</td>
									<td style="width: 8%">${r.GIOITINH }</td>
									<td style="width: 14%">${r.CCCD }</td>
									<td style="width: 20%">${r.DIACHI }</td>
									<td style="width: 10%">${r.EMAIL }</td>
									<td style="width: 12%">${r.SDT }</td>
									<td style="width: 14%">${r.NGHENGHIEP }</td>
									<td style="width: 8%" style="text-align:center"><a
										class="btnThoiO"
										href="room/huyhopdong/${r.MAKT}/${maPhong}.htm"><i
											class='bx bx-exit' style="font-size: 24px"></i></a></td>
								</tr>

							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<h3>Chưa có ai ở phòng này</h3>
				</c:otherwise>
			</c:choose>




		</div>
	</main>
</div>
<script>
	let btnXoa = document.getElementsByClassName("btnTraPhong");
	const confirmIt = function(e, s) {
		if (!confirm('Bạn có chắc chắn muốn trả phòng không ?'))
			e.preventDefault();
	};
	for (let i = 0, l = btnXoa.length; i < l; i++) {
		btnXoa[i].addEventListener('click', confirmIt, false);
	}

	let btnThoiO = document.getElementsByClassName("btnThoiO");
	for (let i = 0, l = btnThoiO.length; i < l; i++) {
		btnThoiO[i].addEventListener('click', confirmIt, false);
	}
</script>

</body>
</html>