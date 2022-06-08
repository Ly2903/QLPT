<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="/WEB-INF/views/includes/header.jsp"%>
<%@include file="/resources/css/service.css"%>
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
						<form:form action="calculator/bill/${MAHOADON}.htm?linkBill"
							modelAttribute="hoadon" method="post">
							<div class="box__title">
								<div class="d-md-flex" style="justify-content: space-between;">
									<h2>HÓA ĐƠN THÁNG ${THANG}/${NAM}</h2>
									<div>
										<button onclick="location.href='calculator/index.htm'"
											class="btn btn-warning me-md-2" type="button"
											style="border-color: #fffafa;">
											<i class='bx bx-arrow-back'
												style="font-size: 16px; font-weight: bold;"></i> Quay về
										</button>

									</div>
								</div>

							</div>
							<br>
							<div class="box__main"
								style="height: auto; border: 1px solid #000; margin: 20px 100px; padding: 16px; line-height: 1.5;">
								<h5>Nhà :
									${inhoadon.hopDong.getPhong().getNhatro().getTENNT()}</h5>
								<div style="text-align: center;">
									<h2>HÓA ĐƠN TIỀN NHÀ</h2>
									<h4>Tháng: ${inhoadon.getNGAYLAP().getMonth()+1 }</h4>
									<p>(Từ ngày 1/${inhoadon.getNGAYLAP().getMonth()+1}/${inhoadon.getNGAYLAP().getYear()+1900}
										đến ngày ${inhoadon.ngayCuaThang() }/${inhoadon.getNGAYLAP().getMonth()+1}/${inhoadon.getNGAYLAP().getYear()+1900})</p>
								</div>
								<div style="padding-left: 150px; padding-right: 150px;">
									<div style="margin-bottom: 16px;">
										<h5 style="display: inline-block; margin-right: 16px;">Họ
											và tên :</h5>
										<label style="font-size: 18px;">${inhoadon.hopDong.getKhachThueDaiDien().getHO()}
											${inhoadon.hopDong.getKhachThueDaiDien().getTEN()}</label>
									</div>
									<div style="margin-bottom: 16px;">
										<h5 style="display: inline-block; margin-right: 16px;">Phòng
											:</h5>
										<label style="font-size: 18px;">${inhoadon.hopDong.getPhong().getTENPHONG()}</label>
									</div>
									<h5 style="margin-bottom: 16px;">Chi tiết hóa đơn:</h5>
									<ul
										style="list-style-type: decimal; padding-left: 100px; font-size: 18px;">
										<li style="margin-bottom: 16px;"><span
											style="padding-right: 16px;">Tiền Nhà:</span>
											${inhoadon.getTienNha()}</li>
										<li style="margin-bottom: 16px;">Tiền dịch vụ : ${inhoadon.getTONGPHUTHU()} <c:forEach
												items="${dichvu}" var="d">
												<ul style="padding-left: 100px;">
													<li><span style="padding-right: 16px;">${d.dichVu.TENDV}:</span>${d.getGia(d.dichVu.MADV)}</li>
												</ul>
											</c:forEach>
										</li>
										
									</ul>
									<h5>Tổng cộng :
										${inhoadon.getTHANHTIEN()}
									</h5>
									<ul
										style="list-style-type: none; display: flex; justify-content: flex-end; text-align: center;">
										<li
											style="display: flex; flex-direction: column; margin-right: 30px;">Người
											thanh toán <span style="display: block; margin-top: 16px;">${inhoadon.hopDong.getKhachThueDaiDien().getHO()}
												${inhoadon.hopDong.getKhachThueDaiDien().getTEN()}</span>
										</li>
										<li>Người nhận TT</li>
									</ul>
								</div>



							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/views/includes/footer.jsp"%>