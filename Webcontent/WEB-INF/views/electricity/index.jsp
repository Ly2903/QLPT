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
						<form:form action="electricity/index.htm" method="post">
							<div class="box__title">
								<div class="d-md-flex" style="justify-content: space-between;">
									<h2 style="font-weight: bold;">Chỉ số điện</h2>
									<!-- <div>
										<a href="electricity/save.htm">
											<button class="btn btn-primary me-md-2 bg-success"
												type="button" style="border-color: #fffafa;"
												onclick=clickLuuCTDV()>
												<i class='bx bx-plus'
													style="font-size: 16px; font-weight: bold;"></i> Lưu
											</button>
										</a>
									</div> -->
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
											<option value="null" selected>Tất cả</option>
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
									<div>
										Trạng thái: <select name="trangThai" class="form-select"
											style="border: 1px solid #ced4da; height: 30px;">
											<option value="null" selected>Tất cả</option>
											<c:forEach items="${dsTrangThai}" var="tt">
												<c:choose>
													<c:when test="${tt.getMATT()== trangThai}">
														<option value="${tt.getMATT()}" selected="selected">${tt.getTENTT()}</option>
													</c:when>
													<c:otherwise>
														<option value="${tt.getMATT()}">${tt.getTENTT()}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
									<button name="btnXem" class="btn btn-primary" type="submit"
										style="border-color: #fffafa; background-color: #f5ba0b; height: 30px; line-height: 0; padding: 4px;">
										<i class='bx bx-search'
											style="font-size: 16px; font-weight: bold;">Xem</i>
									</button>
								</div>
								<h5>Lưu ý</h5>
								<ul>
									<li>Bạn phải gán dịch vụ thuộc loại điện cho khách thuê
										trước thì phần chỉ số này mới được tính cho phòng đó khi tính
										tiền.</li>
									<li>Đối với lần đầu tiên sử dụng phần mềm bạn sẽ phải nhập
										chỉ số cũ và mới cho tháng sử dụng đầu tiên, các tháng tiếp
										theo phần mềm sẽ tự động lấy chỉ số mới tháng trước làm chỉ số
										cũ tháng sau.</li>
								</ul>
								<c:choose>
									<c:when test="${lbThongBaoThemDV.equals('1')}">
										<div style="color: #04AA6D">Lưu thành công!</div>
									</c:when>
									<c:otherwise>
										<div style="color: red;">${lbThongBaoThemDV}</div>
									</c:otherwise>
								</c:choose>

								<table id="customers" class="rounded_table">
									<thead>
										<tr>
											<th scope="col">Nhà</th>
											<th scope="col">Phòng</th>
											<th scope="col">Khách thuê</th>
											<th scope="col" style="width: 15%;">Chỉ số điện cũ</th>
											<th scope="col" style="width: 15%;">Chỉ số điện mới</th>
											<th scope="col">Sử dụng</th>
											<th scope="col"></th>
										</tr>
									</thead>
									<tbody id="table_services">
										<c:forEach items="${dsCTDichVu}" var="dv">
											<%-- <form:input path="hopDong" class="form-control" type="hidden"
												value="${dv.getHopDong()}" />
											<form:input path="dichVu" class="form-control" type="hidden"
												value="${dv.getDichVu()}" />
											<form:input path="thoiGian" class="form-control"
												type="hidden" value="${dv.getThoiGian()}" /> --%>
											<c:choose>
												<c:when test="${dv.getHopDong()!=null}">
													<tr>
														<form action="electricity/saveCTDV.htm" method="post">

															<input name="hopDong1" style="display: none;"
																value="${dv.getHopDong().getMAHOPDONG() }" /> <input
																name="dichVu1" style="display: none;"
																value="${dv.getDichVu().getMADV()}" /> <input
																name="thoiGian1" style="display: none;"
																value="${dv.getThoiGian().getMATG()}" />
														<td>${dv.getHopDong().getPhong().getNhatro().getTENNT()}</td>
														<td>${dv.getHopDong().getPhong().getTENPHONG()}</td>
														<td>${dv.getHopDong().getKhachThueDaiDien().getHO()}
															${dv.getHopDong().getKhachThueDaiDien().getTEN()}</td>
														<td><input name="CHISOCU1" value="${dv.getCHISOCU()}"
															style="width: 100%; text-align: right;" type="number"></input>
														</td>
														<td><input name="CHISOMOI1"
															value="${dv.getCHISOMOI()}"
															style="width: 100%; text-align: right;" type="number"></input>
														</td>
														<td>${dv.getCHISOMOI()- dv.getCHISOCU()}</td>
														<td><button
																class="btn btn-primary me-md-2 bg-success"
																style="border-color: #fffafa; padding: 1px;">
																<i class='bx bx-save'></i> Lưu
															</button></td>

														</form>
													</tr>
												</c:when>
											</c:choose>

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
<%@include file="/WEB-INF/views/includes/footer.jsp"%>
