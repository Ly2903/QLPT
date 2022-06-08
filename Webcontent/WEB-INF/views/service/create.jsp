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
						<form:form action="service/create.htm" modelAttribute="service"
							method="post">
							<div class="box__title">
								<div class="d-md-flex" style="justify-content: space-between;">
									<h2>${title}</h2>
									<div>
										<button onclick="location.href='service/index.htm'"
											class="btn btn-warning me-md-2" type="button"
											style="border-color: #fffafa;">
											<i class='bx bx-arrow-back'
												style="font-size: 16px; font-weight: bold;"></i> Quay về
										</button>
										<button name="${btnStatus}" class="btn btn-primary"
											style="border-color: #fffafa;" type="submit">
											<i class='bx bx-save'
												style="font-size: 16px; font-weight: bold;"></i> Lưu
										</button>
									</div>
								</div>
							</div>
							<br>
							<div class="box__main" style="height: auto;">
								<c:choose>
									<c:when test="${type != 1}">
										<div class="row" style="margin-bottom: 10px;">
											<div class="col-4">
												<label class="form-label">Chọn nhà trọ*</label> <select
													class="form-control nt1" name="MANT" onclick="fn_NhaTro()">
													<c:forEach items="${cbNhaTro}" var="c">
														<option value="${c.getMANT()}">${c.getTENNT() }</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-4">
												<label class="form-label">Chọn dịch vụ*</label> <select
													class="form-control dv1" name="MADV1" onclick="clickService()">
													<option value="isSelected">KHÁC</option>
													<c:forEach items="${cbDichVu}" var="c">
														<option value="${c.getMADV()}">${c.getTENDV() }</option>
													</c:forEach>
												</select>
											</div>
										</div>

										<div class="n_hide">
											<div class="row n_hide" style="margin-bottom: 0px;">
												<form:input path="MADV" class="form-control" type="hidden" />
												<div class="col mb-3">
													<label class="form-label">Tên dịch vụ* </label>
													<label style="color: red;">${lbTBTenDVTrung}</label>
													<form:input id="TENDV" path="TENDV" type="text"
														cssClass="form-control" placeholder="Nhập tên dịch vụ"></form:input>
												</div>
												<div class="col mb-3">
													<label class="form-label">Đơn vị tính </label>
													<form:input path="DONVITINH" type="text"
														cssClass="form-control" placeholder="Nhập đơn vị tính"></form:input>
												</div>
												<div class="col mb-3">
													<label class="form-label">Đơn giá* </label>
													<label style="color: red;">${lbTBDonGiaNull}</label>
													<div class="input-group">
														<c:choose>
															<c:when test="${type != 1}">
																<input name="dongia" class="form-control"
																	aria-label="Dollar amount (with dot and two decimal places)"
																	style="text-align: right;" type="number"/>
																<span class="input-group-text">VNĐ</span>
															</c:when>
															<c:otherwise>
																<input name="dongia" value="${dongia}"
																	class="form-control"
																	aria-label="Dollar amount (with dot and two decimal places)"
																	style="text-align: right;" type="number"/>
																<span class="input-group-text">VNĐ</span>
															</c:otherwise>
														</c:choose>
													</div>
												</div>
											</div>
											<div class="row" style="margin-bottom: 10px;">
												<div class="col mb-8">
													<label class="form-label">Ghi chú </label>
													<textarea name="mota" class="form-control" rows="3"
														style="resize: unset;" id="mota" value="${mota}"></textarea>
												</div>
											</div>
											<div class="row" style="margin-bottom: 0px;">
												<div class="col mb-8">
													<p style="color: red;">(*)Thông tin bắt buộc</p>
												</div>
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<div class="row" style="margin-bottom: 0px;">
											<input name="MANT" value="${MANT}" class="form-control" type="hidden" />
											<form:input path="MADV" class="form-control" type="hidden" />
											<div class="col mb-3">
												<label class="form-label">Tên dịch vụ* </label>
												<label style="color: red;">${lbTBTenDVTrung}</label>
												<form:input id="TENDV" path="TENDV" type="text"
													cssClass="form-control" placeholder="Nhập tên dịch vụ" disabled="true"></form:input>
											</div>
											<div class="col mb-3">
												<label class="form-label">Đơn vị tính </label>
												<form:input path="DONVITINH" type="text"
													cssClass="form-control" placeholder="Nhập đơn vị tính" disabled="true"></form:input>
											</div>
											<div class="col mb-3">
												<label class="form-label">Đơn giá* </label>
												<label style="color: red;">${lbTBDonGiaNull}</label>
												<div class="input-group">
													<c:choose>
														<c:when test="${type != 1}">
															<input name="dongia" class="form-control"
																aria-label="Dollar amount (with dot and two decimal places)"
																style="text-align: right;" type="number"/>
															<span class="input-group-text" >VNĐ</span>
														</c:when>
														<c:otherwise>
															<input name="dongia" value="${dongia}"
																class="form-control"
																aria-label="Dollar amount (with dot and two decimal places)"
																style="text-align: right;" type="number"/>
															<span class="input-group-text">VNĐ</span>
														</c:otherwise>
													</c:choose>
												</div>
											</div>
										</div>
										<div class="row" style="margin-bottom: 10px;">
											<div class="col mb-8">
												<label class="form-label">Ghi chú </label>
												<textarea name="mota" class="form-control" rows="3"
													style="resize: unset;" id="mota" value="${mota}"></textarea>
											</div>
										</div>
										<div class="row" style="margin-bottom: 0px;">
											<div class="col mb-8">
												<p style="color: red;">(*)Thông tin bắt buộc</p>
											</div>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	/* bat su kien click  */
	function clickService() {
		//lấy select chọn dịch vụ
		var a = document.querySelector(".dv1");
	    var n_hide = document.querySelectorAll(".n_hide input, textarea");
	    //bắt sự kiên thay đổi
	    a.addEventListener("change",()=>{
	    	//lấy lựa chọn
	    	var i = a.selectedIndex;
	    	//nạp mảng [madv, tendv, donvi]
	    	var array = new Array();
	    	<c:forEach items="${cbDichVu}" var="item">
	    	   array.push(["${item.getMADV()}", "${item.getTENDV()}", "${item.getDONVITINH()}"]);
	    	</c:forEach>
	    	//if cho 2 trường hợp. 1: chọn khác "KHAC" 2: chọn "KHAC"
	    	if(i != 0){
	    		//vòng for để disable != dongia vs mota
	    	for(let b of n_hide){
	    		if(b.name == "dongia" || b.name=="mota"){
	    		}else{
	    			b.setAttribute("disabled", "true")
	    		}
	    		
	    	}
	    		//set tên cho ten dich vu
	    	document.getElementById('TENDV').setAttribute('value', a.options[i].innerHTML);
	    		//hàm trả về đơn vị theo lựa chọn
	    	function getDV(){
	    		for(let tmp of array){
	    			if(a.options[i].value == tmp[0]){
	    				return tmp[2]
	    			}
	    		}
	    	}
	    		//set gia trị cho đơn vị
	    	document.getElementById('DONVITINH').setAttribute('value', getDV())
	    	/* document.getElementById('MOTA').innerHTML = getDV()[0] */
	    	}
	    	else {
	    		//xoá tất cả díable
	    		for(let b of n_hide){b.removeAttribute("disabled")}
	    		// document.getElementById('TENDV').removeAttribute('value'); 
	    		document.getElementById('TENDV').setAttribute('value', "");
	    		document.getElementById('DONVITINH').setAttribute('value', "");
	    		/* document.getElementById('MOTA').innerHTML="" */
	    	}
	    	})
	 }
	//trả về những dịch vụ không nằm trong nhà trọ này đúng khum
	function getAllByMaNT(dv, mant) {
		//lấy mảng quy đinh
		var arrQD = [];
		
		<c:forEach items="${abc}" var="item2">
			arrQD.push(["${item2.getNhaTro().getMANT()}", "${item2.getDichVu().getMADV()}"]);
		</c:forEach>
		//lấy mảng nhà trọ
		var arrayNT = [];
		
		<c:forEach items="${cbNhaTro}" var="item">
			arrayNT.push(["${item.getMANT()}"]);
		</c:forEach>
		var arrTmp = []
		let arr1 = []
		//lấy mã dịch vụ theo mã nhà trọ
		for(let tmp2 of arrQD){
			if(mant == tmp2[0]){
				arr1.push(tmp2[1])
			}
		}
		//lấy ra dịch vụ theo arr1
		for(let tmp3 of dv){
			if(!arr1.includes(tmp3[0])){
				arrTmp.push(tmp3)
				console.log(arr1);
			}
		}				
		
		return arrTmp
	}
	function fn_NhaTro() {
		var a = document.querySelector(".nt1");
	    a.addEventListener("change",()=>{
	    	var i = a.selectedIndex;
	    	var array = new Array();
	    	var maNt1=a.options[a.selectedIndex].value;
	    	<c:forEach items="${cbDichVu}" var="item">
	    		array.push(["${item.getMADV()}", "${item.getTENDV()}", "${item.getDONVITINH()}"]);
	    	</c:forEach>
	    	var arrDV = getAllByMaNT(array, maNt1)
	    	var dv1 = document.querySelector(".dv1");
	    	while (dv1.options.length > 1) {
	    	     dv1.remove(dv1.options.length-1);
	    	}
	    	var dvsl = []
	    	for(let tmp of arrDV){
	    		dvsl.push(tmp[0])
	    	}
	    	console.log(dvsl)
	    	function getDVTheoNT(){
	    		for(let tmp of array){
	    			if(dvsl.includes(tmp[0])){
	    				dv1.add(new Option(tmp[1],tmp[0]))
	    			}	
	    		}
	    	}
	    	getDVTheoNT()
	    })
	}
</script>
<%@include file="/WEB-INF/views/includes/footer.jsp"%>
