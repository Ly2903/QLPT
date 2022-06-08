<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/includes/taglib.jsp"%>
<div class="cus_navbar" style="padding-top:30px">
	<div class="cus_header">
		<h1>QUẢN LÝ NHÀ TRỌ</h1>
		<p>SIMPLE HOUSE</p>
	</div>
	<p class="navbar__hello">Xin chào, <a href="area/chinhthongtin/${sessionScope.mact}.htm"> ${sessionScope.hoten }</a></p>
	<div class="navbar__func">
		<a class="navbar__func-item" href="home/index.htm"> <i
			class="bx bx-home-alt"></i>
			<p>Trang chủ</p>
			<div class="active"></div>
		</a> 
		</a> <a class="navbar__func-item" href="area/index.htm"> <i
			class="bx bx-building-house"></i>
			<p>Nhà trọ</p>
			<div class="active"></div>
		</a> 
		<a class="navbar__func-item" href="room/index.htm">
		<i class='bx bx-store-alt'></i>
			<p>Phòng</p>
			<div class="active"></div>
		<a href="service/index.htm" class="navbar__func-item"> <i
			class="bx bxl-sketch"></i>
			<p>Dịch vụ</p>
			<div class="active"></div>
		</a> <a href="electricity/index.htm" class="navbar__func-item"> <i
			class="bx bx-cloud-lightning"></i>
			<p>Chỉ số Điện</p>
			<div class="active"></div>
		</a> <a class="navbar__func-item" href="water/index.htm"> <i class="bx bx-water"></i>
			<p>Chỉ số nước</p>
			<div class="active"></div>
		</a> <a class="navbar__func-item" href="incurred/index.htm"> <i class='bx bxs-magic-wand'></i>
			<p>Bảo trì</p>
			<div class="active"></div>
		</a> <a class="navbar__func-item" href="calculator/index.htm"> <i class="bx bx-calculator"></i>
			<p>Tính Tiền</p>
			<div class="active"></div>
		</a> <a class="navbar__func-item" href="login/logout.htm"><i class='bx bx-log-out-circle'></i>
			<p>Đăng Xuất</p>
			<div class="active"></div>
		</a>
	</div>
	
</div>
