<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/includes/header.jsp"%>
<div class="app">
	<%@include file="/WEB-INF/views/includes/navbar.jsp"%>
	<div class="main">
		<div class="pseudo"></div>
		<div class="content">
			<div class="content__header">
				<i class='bx bx-menu'></i>
			</div>
			<hr />
			<%-- <c:choose>
				<c:when test="${ sessionScope.mact != null}">
					<p>Đã đăng nhập ${mact }</p>
				</c:when>
				<c:otherwise>
					<p>Chưa đăng nhập</p>
				</c:otherwise>
			</c:choose> --%>
			<div class="row">
				<div class="c-6">
					<div class="box">
						<h2 class="box__title">Trạng thái phòng</h2>
						<div class="box__main">
							<div id="piechart"></div>
						</div>
					</div>
				</div>
				<div class="c-6">
					<div class="box">
						<h2 class="box__title">Danh sách phòng trống</h2>
						<div class="box__main">
							<div class="rounded_table">
								<table id="customers">
									<tr>
										<th scope="col">Nhà</th>
										<th scope="col">Phòng</th>
									</tr>

									<c:forEach items="${dsPhongTrong}" var="p">
										<tr>
											<td>${p.nhatro.TENNT }</td>
											<td>${p.TENPHONG }</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="c-6">
					<div class="box"">
						<h2 class="box__title">Danh sách khách nợ tiền nhà</h2>
						<div class="box__main">
							<div class="rounded_table">
								<table id="customers">
									<tr>
										<th>Nhà</th>
										<th>Phòng</th>
										<th>Khách</th>
										<th>Tháng</th>
										<th>Năm</th>
										<th>Số tiền</th>
									</tr>
									<c:forEach items="${dsHoaDon}" var="h">
										<tr>
											<td>${h.hopDong.phong.nhatro.TENNT}</td>
											<td>${h.hopDong.phong.TENPHONG }</td>
											<td>${h.hopDong.khachThueDaiDien.HO }
												${h.hopDong.khachThueDaiDien.TEN }</td>
											<td>${h.getThang() }</td>
											<td>${h.getNam() }</td>
											<td>${h.THANHTIEN }</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>

				<div class="c-6">
					<div class="box"">
						<h2 class="box__title">Khách sắp hết hạn hợp đồng</h2>
						<div class="box__main">
							<div class="rounded_table">
								<table id="customers">
									<tr>
										<th>Nhà</th>
										<th>Phòng</th>
										<th>Khách</th>
										<th>Ngày hết hạn</th>
										<th>Gia hạn hợp đồng</th>
									</tr>
									<c:forEach items="${dsHopDongSapHetHan}" var="h">
										<tr>
											<td>${h.phong.nhatro.TENNT}</td>
											<td>${h.phong.TENPHONG }</td>
											<td>${h.khachThueDaiDien.HO }
												${h.khachThueDaiDien.TEN }</td>
											<td>${h.THOIHAN }</td>
											<td><a class="btn-danger btnXoaDichVu"
												href="room/giahanhopdong/${h.phong.MAPHONG}.htm">
													<i class='bx bxs-edit' style="padding: 2px 4px;"></i>
											</a></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="/WEB-INF/views/includes/footer.jsp"%>