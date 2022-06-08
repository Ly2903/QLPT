
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="/WEB-INF/views/includes/header.jsp"%>
<%@include file="/resources/css/service.css"%>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
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
					<div class="box"
						style="width: 95%; padding: 16px; padding-top: 0; height: auto;">

						<div class="box__title">
							<div class="d-md-flex" style="justify-content: space-between;">
								<h2 style="font-weight: bold;">Danh sách dịch vụ theo nhà trọ</h2>
								<div>

									<button onclick="location.href='service/create.htm'"
										class="btn btn-primary me-md-2 bg-success" type="button"
										style="border-color: #fffafa;">
										<i class='bx bx-plus'
											style="font-size: 16px; font-weight: bold;"></i> Thêm dịch vụ
									</button>
								</div>
							</div>
						</div>
						<br>
						<div class="box__main">
							<form:form modelAttribute="nhatro" action="service/index.htm"
								method="post">
								<div class="d-md-flex"
									style="justify-content: space-between; align-items: flex-start;">
									<div class="form-group"
										style="display: flex; justify-content: space-between; align-items: flex-start;">
										<div style="width: 100px;">Chọn nhà trọ</div>
										<div style="margin-left: 8px; margin-right: 8px;">
											<form:select id="Khu" class="form-control" path="MANT"
												items="${cbNhaTro}" itemLabel="TENNT" itemValue="MANT">
												<form:option value="null">Tất cả</form:option>
											</form:select>
										</div>
										<button class="btn btn-primary" type="submit"
											style="border-color: #fffafa; background-color: #f5ba0b;">
											<i class='bx bx-search'
												style="font-size: 16px; font-weight: bold;"></i> Xem
										</button>
									</div>
									<div style="display: flex;">
										<input name="searchInput" id="searchInput"
											class="form-control" style="margin-right: 16px;"
											aria-lable="Search" placeholder="Nhập tên dịch vụ cần tìm"
											title="abc">
										<button name="btnSearch" id="searchProduct"
											class="btn btn-outline-success" type="submit">Search</button>
									</div>

								</div>
							</form:form>
						</div>
						<br>
						<c:if test="${message != null }">
							<div class="alert alert-primary" role="alert">${message}</div>
						</c:if>
						<table id="customers" class="rounded_table">
							<thead>
								<tr>
									<th scope="col">Tên dịch vụ</th>
									<th scope="col">Đơn vị tính</th>
									<th scope="col">Đơn giá (VNĐ)</th>
									<th scope="col">Mô tả</th>
									<th scope="col">Chỉnh sửa</th>
									<th scope="col">Xóa</th>
								</tr>
							</thead>
							<tbody id="table_services">
								<c:forEach items="${services}" var="dv">
									<c:choose>
										<c:when test="${empty dv.getDsQuyDinh()}"></c:when>
										<c:when test="${dv.getQuyDinh(MANT)==-1}"></c:when>
										<c:otherwise>
											<tr>
												<td>${dv.TENDV }</td>
												<td>${dv.DONVITINH}</td>
												<c:choose>
													<c:when test="${MANT == null}">
														<td></td>
													</c:when>
													<c:when test="${empty dv.getDsQuyDinh()}">
														<td></td>
													</c:when>
													<c:when test="${dv.getQuyDinh(MANT)==-1}">
														<td></td>
													</c:when>
													<c:otherwise>
														<td>${dv.getDsQuyDinh().get(dv.getQuyDinh(MANT)).getDONGIA()}</td>
													</c:otherwise>
												</c:choose>
												<td>${dv.getDsQuyDinh().get(dv.getQuyDinh(MANT)).getMOTA()}</td>
												<td><a class="btn-primary"
													href="service/create/${dv.MADV}.htm?linkEdit&MANT=${MANT}&dongia=${dv.getDsQuyDinh().get(dv.getQuyDinh(MANT)).getDONGIA()}&mota=${dv.getDsQuyDinh().get(dv.getQuyDinh(MANT)).getMOTA()}"><i
														class='bx bxs-edit' style="padding: 2px 4px;"></i> </a></td>
												<%-- <td>
													<button type="button" class="btn btn-danger"
														data-bs-toggle="modal" data-bs-target="#exampleModal"
														style="padding: 1px;">Xóa</button> <!-- Modal -->
													<div class="modal fade" id="exampleModal" tabindex="-1"
														aria-labelledby="exampleModalLabel" aria-hidden="true">
														<div class="modal-dialog" style="max-width: 400px;">
															<div class="modal-content">
																<div class="modal-header"
																	style="background-color: #04AA6D;">
																	<h5 class="modal-title" id="exampleModalLabel"
																		style="color: #fff;">Xóa dịch vụ trong quy định</h5>
																	<button type="button" class="btn-close"
																		data-bs-dismiss="modal" aria-label="Close"></button>
																</div>
																<div class="modal-body">Bạn có chắc chắn xóa
																	${dv.TENDV } không?</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-secondary"
																		data-bs-dismiss="modal">Bỏ qua</button>
																	<a class="btn-danger"
																		href="service/index/${dv.MADV}.htm?linkDelete&MANT=${MANT}">
																		<button type="button" class="btn btn-danger">
																			Xóa</button>
																	</a>

																</div>
															</div>
														</div>
													</div>
												</td> --%>
												<td><a class="btn-danger btnXoaDichVu"
													href="service/index/${dv.MADV}.htm?linkDelete&MANT=${MANT}">
														<i class='bx bx-x'></i>
												</a></td>
											</tr>

										</c:otherwise>
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
<script type="text/javascript">
	let btnXoa = document.getElementsByClassName("btnXoaDichVu");
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
