<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/includes/header.jsp"%>
<link href="<c:url value='/resources/assets/bootstrap.min.css' />"
	rel="stylesheet" />
<link href="<c:url value='/resources/css/hopdong.css' />"
	rel="stylesheet" />
<div class="app">
	<%@include file="/WEB-INF/views/includes/navbar.jsp"%>
	<div class="pseudo"></div>
	<main class="container">
		<section class="hopdong">
			<h2>HỢP ĐỒNG CHO THUÊ PHÒNG TRỌ</h2>
			<p>Số : ${hd.MAHOPDONG } Ngày : ${hd.NGAYKY }</p>
			<div class="cacben">
				<div class="cacben_left">
					<p><b>Bên A</b>: Bên cho thuê</p>
					<p><b>Ông/ bà :</b> ${chu.HO } ${chu.TEN }</p>
					<p><b>Năm sinh :</b> ${chu.NAMSINH }</p>
					<p><b>Địa chỉ thường trú :</b> ${chu.DIACHI }</p>
					<p><b>Điện thoại :</b> ${chu.SDT }</p>
				</div>
				<div class="cacben_right">
					<p><b>Bên B:</b> Bên thuê</p>
					<p><b>Ông/ bà :</b> ${khach.HO } ${khach.TEN }</p>
					<p><b>Năm sinh :</b> ${khach.NAMSINH }</p>
					<p><b>Địa chỉ thường trú :</b> ${khach.DIACHI }</p>
					<p><b>Điện thoại :</b> ${khach.SDT }</p>
				</div>
			</div>
			<div>
				<p><b>Bên A</b> đã đồng ý cho <b>bên B</b> thuê phòng trọ số
					<b>${hd.phong.TENPHONG}</b> ở địa chỉ ${hd.phong.nhatro.PHUONG_XA} ,
					${hd.phong.nhatro.QUAN_HUYEN}, ${hd.phong.nhatro.TINH_TP} .</p>
				<p><b>Thời hạn thuê phòng :</b> ${hd.THOIHAN } kể từ ngày :
					${hd.NGAYKY }.</p>
				<p><b>Giá tiền thuê phòng là :</b> ${hd.phong.loaiPhong.DONGIA} / tháng .</p>
				<p><b>Tiền cọc đã đóng là :</b> ${hd.TIENCOC}</p>
			</div>
			<div class="kyten">
				<div class="kyten_left">
					<p>Bên A</p>
					<p>Bên B</p>
				</div>
				<div class="kyten_right">
					<p>Đã ký</p>
					<p>Đã ký</p>
				</div>

			</div>
		</section>
	</main>
</div>
<%@include file="/WEB-INF/views/includes/footer.jsp"%>
