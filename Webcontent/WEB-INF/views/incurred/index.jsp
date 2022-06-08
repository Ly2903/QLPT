<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/includes/header.jsp"%>
<%@include file="/resources/css/service.css"%>
<!-- included thư viện calendar -->
<!--  jQuery -->
<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.11.3.min.js"></script>

<!-- Isolated Version of Bootstrap, not needed if your site already uses Bootstrap -->
<link rel="stylesheet"
	href="https://formden.com/static/cdn/bootstrap-iso.css" />

<!-- Bootstrap Date-Picker Plugin -->
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css" />
<!-- end -->

<div class="app">
	<%@include file="/WEB-INF/views/includes/navbar.jsp"%>
	<div class="main">
		<div class="pseudo"></div>
		<div class="content">
			<div class="content__header">
				<i class='bx bx-menu'></i>
			</div>
			<hr />
			<div class="row">
				<div class="c-12">
					<div class="box" style="width: 94%; height: auto;">
						<div class="box__title">
							<div class="d-md-flex" style="justify-content: space-between;">
								<h2 style="font-weight: bold;">Bảo Trì</h2>
								<div>

									<button onclick="location.href='incurred/create.htm'"
										class="btn btn-primary me-md-2 bg-success" type="button"
										style="border-color: #fffafa;">
										<i class='bx bx-plus'
											style="font-size: 16px; font-weight: bold;"></i> Thêm
									</button>
								</div>
							</div>
						</div>
						<br>
						<div class="box__main" style="height: auto; padding: 16px;">
							<div class="box__main">
								<form:form modelAttribute="nhatro" action="incurred/index.htm"
									method="post">
									<div class="d-md-flex"
										style="justify-content: space-between; align-items: flex-start;">
										<div>
											<!-- Form code begins -->
											<form method="post">
												<div class="form-group"
													style="display: flex; justify-content: space-between;">
													<!-- Date input -->
													<label class="control-label" for="date">Tháng: </label> <input
														class="form-control" id="date" name="date"
														placeholder="MM/YYYY" style="height: 30px" value="${date}" />
												</div>
											</form>
											<!-- Form code ends -->
										</div>
										<div class="form-group"
											style="display: flex; justify-content: space-between; align-items: flex-start;">
											<div style="width: 100px;">Chọn nhà trọ</div>
											<div style="margin-left: 8px; margin-right: 8px;">
												<select class="form-select" name="MANT">
													<option selected></option>
													<c:forEach items="${dsNhaTro}" var="k">
														<option value="${k.getMANT()}" path="MANT">${k.getTENNT()}</option>
													</c:forEach>
												</select>
											</div>
											<button class="btn btn-primary" type="submit"
												style="border-color: #fffafa; background-color: #f5ba0b;">
												<i class='bx bx-search'
													style="font-size: 16px; font-weight: bold;"></i> Xem
											</button>
										</div>


									</div>
								</form:form>
							</div>
							<br>
							<%-- <%
							String mt = request.getParameter("MANT");
							%> --%>
							<c:if test="${message!=NULL}">
								<div class="alert alert-primary" role="alert">${message}</div>
							</c:if>

							<table id="customers" class="rounded_table">
								<thead>
									<tr>
										<th scope="col"></th>
										<th scope="col">Nhà</th>
										<th scope="col">Phòng</th>
										<th scope="col">Mô tả</th>
										<th scope="col">Số tiền(VNĐ)</th>

									</tr>
								</thead>
								<tbody>
									<c:forEach items="${dsPhatSinh}" var="k">
										<c:choose>
											<c:when test="${k.phong.nhatro.getMANT()==nhatroselect}">
												<tr>
													<td style="width: 5%"><a class="btnXoa"
														href="incurred/index/${k.MABAOTRI}.htm?linkDelete"><i
															class='bx bx-folder-minus' style="font-size: 18px;" title="XÓA"></i></a>
														<a href="incurred/create/${k.MABAOTRI}.htm?linkEdit"><i
															class='bx bx-edit' style="font-size: 18px;"title="CHỈNH SỬA"></i></a></td>
													<td style="width: 15%">${k.phong.nhatro.TENNT }</td>
													<td style="width: 10%">${k.phong.TENPHONG}</td>
													<td style="width: 23%">${k.MOTA }</td>
													<td style="width: 15%">${k.CHIPHI }</td>

												</tr>
											</c:when>
										</c:choose>

									</c:forEach>
								</tbody>
							</table>

							<br>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	let btnXoa = document.getElementsByClassName("btnXoa");
	console.log(btnXoa);
	const confirmIt = function(e) {
		if (!confirm('Bạn có chắc chắn muốn xoá không ?'))
			e.preventDefault();
	};
	for (let i = 0, l = btnXoa.length; i < l; i++) {
		btnXoa[i].addEventListener('click', confirmIt, false);
	}
</script>
<%@include file="/WEB-INF/views/includes/footer.jsp"%>
