<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="qlpt.entity.CTDichVuEntity"%>
<%@page import="java.util.*"%>
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
					<div class="box"
						style="width: 95%; padding: 16px; padding-top: 0; height: auto;">
						<form:form action="calculator/index.htm" method="post">
							<div class="box__title">
								<div class="d-md-flex" style="justify-content: space-between;">
									<h2 style="font-weight: bold;">Tính Tiền</h2>
								</div>
							</div>
							<br>
							<div class="box__main" style="height: auto;">
								<div class="d-md-flex"
									style="justify-content: space-between; align-content: flex-start;">
									<div>
										<!-- Form code begins -->
										<form method="post">
											<div class="form-group"
												style="display: flex; justify-content: space-between;">
												<!-- Date input -->
												<label class="control-label" for="date">Tháng: </label> <input
													class="form-control" id="date" name="date"
													placeholder="MM/YYYY" type="text" style="height: 30px"
													value="${date}" />
											</div>
										</form>
										<!-- Form code ends -->
									</div>
									<div>
										Nhà: <select name="nhaTro" class="form-select"
											style="border: 1px solid #ced4da; height: 30px;">
											<option value="null" selected></option>
											<c:forEach items="${dsNhaTro}" var="k">
												<c:choose>
													<c:when test="${k.getMANT()==nhaTro}">
														<option value="${k.getMANT()}" selected="selected">${k.getTENNT()}</option>
													</c:when>
													<c:otherwise>
														<option value="${k.getMANT()}">${k.getTENNT()}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
									<div></div>
									<button name="btnXem" class="btn btn-primary" type="submit"
										style="border-color: #fffafa; background-color: #f5ba0b; height: 30px; line-height: 0; padding: 4px;">
										<i class='bx bx-search'
											style="font-size: 16px; font-weight: bold;">Xem</i>
									</button>
								</div>



								<c:if test="${title!=NULL}">
									<div class="alert alert-primary" role="alert">${title}</div>
								</c:if>

								<table id="customers" class="rounded_table">
									<thead>
										<tr>
											<th scope="col">Nhà</th>
											<th scope="col">Phòng</th>
											<th scope="col">Khách thuê</th>
											<th scope="col">Thành Tiền</th>
											<th scope="col">Trạng Thái</th>
											<th scope="col"></th>
										</tr>
									</thead>

									<tbody id="table_services">
										<c:forEach items="${dsHoaDon}" var="hd">
											<tr>
												<c:choose>
													<c:when
														test="${hd.hopDong.getPhong().getNhatro().getMANT()==nhatroselect}">
														<td>${hd.hopDong.getPhong().getNhatro().getTENNT()}</td>
														<td>${hd.hopDong.getPhong().getTENPHONG()}</td>
														<td>${hd.hopDong.getKhachThueDaiDien().getHO()}
															${hd.hopDong.getKhachThueDaiDien().getTEN()}</td>
														<td>${hd.getTHANHTIEN()}</td>
														<td><c:choose>
																<c:when test="${hd.TRANGTHAI}">Đã thu tiền</c:when>
																<c:when test="${!hd.TRANGTHAI}">Chưa thu tiền</c:when>
															</c:choose></td>
														<td><c:choose>
																<c:when test="${hd.TRANGTHAI}">
																	<a href="calculator/bill/${hd.MAHOADON}.htm?linkBill"><i
																		class='bx bx-notepad' style="font-size: 20px;"></i>In
																		hóa đơn</a>
																	
																
																</c:when>
																<c:when test="${!hd.TRANGTHAI}">
																	<a href="calculator/bill/${hd.MAHOADON}.htm?linkBill"><i
																		class='bx bx-notepad' style="font-size: 20px;"></i>In
																		hóa đơn</a>
																	<br>
																	<a class="btnPayment"
																		href="calculator/index/${hd.MAHOADON}.htm?linkPayment"><i
																		class='bx bx-money' style="font-size: 20px;"></i>Đóng
																		tiền</a>
																</c:when>
															</c:choose></td>
														
													</c:when>


												</c:choose>


											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</form:form>
						<br>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	let btnXoa = document.getElementsByClassName("btnPayment");
	console.log(btnXoa);
	const confirmIt = function(e) {
		if (!confirm('Đóng tiền cho phòng này ?'))
			e.preventDefault();
	};
	for (let i = 0, l = btnXoa.length; i < l; i++) {
		btnXoa[i].addEventListener('click', confirmIt, false);
	}
</script>
<%@include file="/WEB-INF/views/includes/footer.jsp"%>
