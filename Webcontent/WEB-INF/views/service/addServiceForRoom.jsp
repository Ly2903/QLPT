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
						<%-- <form:form action="service/addServiceForRoom123.htm" method="post"> --%>
						<div class="box__title">
							<div class="d-md-flex" style="justify-content: space-between;">
								<h2 style="font-weight: bold;">Thêm dịch vụ cho phòng</h2>
								<div>
									<button onclick="location.href='room/index.htm'"
										class="btn btn-warning me-md-2" type="button"
										style="border-color: #fffafa;">
										<i class='bx bx-arrow-back'
											style="font-size: 16px; font-weight: bold;"></i> Quay về
									</button>
								</div>
							</div>

						</div>
						<br>
						<div class="box__main" style="height: auto;">
							<h5>Lưu ý</h5>
							<ul>
								<li>Vui lòng chọn dịch vụ cho khách thuê. Nếu khách có chọn
									dịch vụ thì khi tính tiền phòng phần mềm sẽ tự tính các khoản
									phí vào hóa đơn; ngược lại nếu không chọn phần mềm sẽ bỏ qua.</li>
								<li>Đối với dịch vụ là loại điện/ nước thì sẽ tính theo chỉ
									số điện/ nước</li>
								<li>Đối với các dịch vụ khác sẽ tính theo số lượng (ví dụ
									phòng có 2 xe đạp nhập số lượng là 2)</li>
							</ul>
							<div>
								<!-- Form code begins -->
								<form action="service/addService.htm" method="post"
									style="display: flex;">
									<input name="maPhong" type="hidden" value="${maPhong }">
									<input name="THANG" style="display: none;" value="${THANG}" />
									<input name="NAM" style="display: none;" value="${NAM}" />
									<div class="form-group col-3"
										style="display: flex; justify-content: space-between;">
										<!-- Date input -->
										<label class="control-label" for="date">Tháng: </label> <input
											class="form-control" id="date" name="date"
											placeholder="MM/YYYY" type="text" style="height: 30px"
											value="${date}" />
									</div>
									<button name="btnXem" class="btn btn-primary" type="submit"
										style="border-color: #fffafa; background-color: #f5ba0b; height: 30px; line-height: 0; padding: 4px;">
										<i class='bx bx-search'
											style="font-size: 16px; font-weight: bold;">Xem</i>
									</button>
								</form>
								<!-- Form code ends -->
							</div>
							<div>${message}</div>
							<table id="customers" class="rounded_table">
								<thead>
									<tr>
										<th scope="col">Chọn</th>
										<th scope="col">Dịch vụ sử dụng</th>
										<th scope="col">Đơn giá</th>
										<th scope="col" style="width: 15%;">Số lượng</th>
										<th scope="col">Lưu</th>
									</tr>
								</thead>
								<tbody id="table_services">
									<c:forEach items="${dsQuyDinh}" var="q">
										<tr>
											<form action="service/addService/${maPhong}.htm"
												" method="post">
												<input name="maPhong" style="display: none;"
													value="${maPhong}" /> <input name="maDV"
													style="display: none;" value="${q.dichVu.MADV}" /> <input
													name="THANG" style="display: none;" value="${THANG}" /> <input
													name="NAM" style="display: none;" value="${NAM}" /> <input
													name="date" style="display: none;" value="${date}" />

												<c:choose>
													<c:when
														test="${q.dichVu.TENDV=='ĐIỆN' or q.dichVu.TENDV=='NƯỚC'}">
														<td>
															<div class="form-check">
																<input class="form-check-input" type="checkbox" value=""
																	id="flexCheckDefault" style="transform: scale(1.8);"
																	checked="checked" disabled="disabled"> <label
																	class="form-check-label" for="flexCheckDefault"></label>
															</div>
														</td>
														<td>${q.dichVu.TENDV}</td>
														<td>${q.DONGIA}</td>
														<td><input name="SoLuong"
															style="width: 100%; text-align: center;" type="number"
															disabled="disabled" value="1"></td>
														<td><button
																class="btn btn-primary me-md-2 bg-success"
																style="border-color: #fffafa; padding: 1px;"
																disabled="disabled">
																<i class='bx bx-save'></i> Lưu
															</button></td>
													</c:when>
													<c:otherwise>
														<td>
															<div class="form-check">
																<c:choose>
																	<c:when
																		test="${q.getCTDVTheoQuyDinh(maPhong, q.dichVu.MADV, THANG, NAM)!= null}">
																		<input name="check" class="form-check-input"
																			type="checkbox" value="checked" checked="checked"
																			id="flexCheckDefault" style="transform: scale(1.8);">
																		<label class="form-check-label" for="flexCheckDefault"></label>
																	</c:when>
																	<c:otherwise>
																		<input name="check" class="form-check-input"
																			type="checkbox" id="flexCheckDefault" value="checked"
																			style="transform: scale(1.8);">
																		<label class="form-check-label" for="flexCheckDefault"></label>
																	</c:otherwise>
																</c:choose>
															</div>
														</td>
														<td>${q.dichVu.TENDV}</td>
														<td>${q.DONGIA}</td>
														<td><input name="soLuong"
															value="${q.getChiSoTheoQuyDinh(maPhong, q.dichVu.MADV, THANG, NAM)}"
															style="width: 100%; text-align: right;" type="number">
														</td>
														<td><button
																class="btn btn-primary me-md-2 bg-success"
																style="border-color: #fffafa; padding: 1px;">
																<i class='bx bx-save'></i> Lưu
															</button></td>
													</c:otherwise>
												</c:choose>
											</form>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<%-- </form:form> --%>
						<br>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="/WEB-INF/views/includes/footer.jsp"%>
