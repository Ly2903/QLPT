<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="/WEB-INF/views/includes/header.jsp"%>
<%@include file="/resources/css/service.css"%>
<link rel="stylesheet"
	href="https://formden.com/static/cdn/bootstrap-iso.css" />
<!--  jQuery -->
<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<!-- Bootstrap Date-Picker Plugin -->

<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css" />
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
					<div class="box" style="width: 95%; padding: 16px; padding-top: 0; height: auto;">
						<form:form action="incurred/create.htm" modelAttribute="incurred"
							method="post" id="formElement">
							<div class="box__title">
								<div class="d-md-flex" style="justify-content: space-between;">
									<h2 style="font-weight: bold;">Thêm Bảo Trì</h2>
									<div>
										<button onclick="location.href='incurred/index.htm'"
											class="btn btn-warning me-md-2" type="button"
											style="border-color: #fffafa; color: #fffafa">
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
							<c:if test="${message!=NULL}">
								<div class="alert alert-primary" role="alert">${message}</div>
							</c:if>
							<div class="box__main" style="height: auto;">
								<div class="row" style="margin-bottom: 0px;">
									<form:input path="MABAOTRI" class="form-control" type="hidden" />
									<div class="col mb-3">
										<label class="control-label">Nhà trọ </label>
										<div style="margin-left: 8px; margin-right: 8px;">
											<c:choose>
												<c:when test="${!empty TENNHATRO}">
													<div style="margin-left: 8px; margin-right: 8px;">
														<input class="form-control" value="${TENNHATRO }"
															disabled="disabled">
													</div>
												</c:when>
												<c:otherwise>
													<form:select id="Khu" onchange="getPhong()"
														class="form-control nt1" path="phong.nhatro.MANT"
														items="${dsNhaTro}" itemLabel="TENNT" itemValue="MANT"
														onclick="fn_NhaTro()">
													</form:select>
												</c:otherwise>
											</c:choose>
										</div>

									</div>

									<div class="col mb-3">
										<label class="control-label">Phòng </label>
										<div style="margin-left: 8px; margin-right: 8px;">
											<c:choose>
												<c:when test="${!empty TENPHONG}">
													<div style="margin-left: 8px; margin-right: 8px;">
														<input class="form-control" value="${TENPHONG }"
															disabled="disabled">
													</div>
												</c:when>
												<c:otherwise>
													<form:select id="Phong" class="form-control phong"
														path="phong.MAPHONG" items="${dsPhong}"
														itemLabel="TENPHONG" itemValue="MAPHONG">
													</form:select>
												</c:otherwise>
											</c:choose>

										</div>

									</div>
									<div class="col mb-3">
										<form method="post">
											<label class="form-label">Thời gian </label>
											<div class="form-group"
												style="display: flex; justify-content: space-between;">
												<!-- Date input -->
												<label class="control-label" for="NGAY"></label>
												<form:input class="form-control" id="NGAY" path="NGAY"
													type="text" name="NGAY" style="height: 35px" />
											</div>
										</form>
									</div>
									<div class="col mb-3">
										<label class="form-label">Chi phí </label>
										<form:input path="CHIPHI" type="text" name="CHIPHI"
											cssClass="form-control" placeholder="Nhập chi phí" id="chiphi"></form:input>
									</div>

								</div>
								<div class="row" style="margin-bottom: 10px;">
									<div class="col mb-8">
										<label class="form-label">Mô tả </label>
										<form:textarea path="MOTA" name="MOTA" class="form-control"
											rows="3" style="resize: unset;"></form:textarea>
									</div>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>
<script>
/* 	checkChiPhi */
	let formElement = document.getElementById("formElement");
	console.log(formElement);
	formElement.onsubmit=((e)=>{
	if(document.getElementById("chiphi").value.length == 0)
	{
		e.preventDefault();
		alert("Chưa nhập chi phí !");
		return;
	}})
	
	
	
</script>
<script type="text/javascript">
/* Calendar in electricity */
$(document).ready(function(){
  var date_input=$('input[name="NGAY"]'); //our date input has the name "date"
  var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
  var options={
    format: 'dd/mm/yyyy',
    container: container,
    todayHighlight: true,
    autoclose: true,
  };
  date_input.datepicker(options);
})
//trả về những dịch vụ không nằm trong nhà trọ này đúng khum
	function getAllByMaNT(phong, mant) {
		//lấy mảng nhà trọ
		var arrayNT = [];
		
		<c:forEach items="${dsNhaTro}" var="item">
			arrayNT.push(["${item.getMANT()}"]);
		</c:forEach>
		
		
		var arrTmp = []
		//lấy mã dịch vụ theo mã nhà trọ
		for(let tmp2 of phong){
			if(mant == tmp2[2]){
				arrTmp.push(tmp2[0])
			}
		}
		/* var arrTmp = []
		//lấy ra dịch vụ theo arr1
		for(let tmp of phong){
			if(arrayNT.includes(tmp[0])){
				arrTmp.push(tmp)
			}
		}				 */
		console.log(arrTmp);
		return arrTmp
	}
	function fn_NhaTro() {
		var a = document.querySelector(".nt1");
	    a.addEventListener("change",()=>{
	    	var i = a.selectedIndex;
	    	var array = new Array();
	    	var maNt1=a.options[a.selectedIndex].value;
	    	<c:forEach items="${dsPhong}" var="item">
	    		array.push(["${item.getMAPHONG()}", "${item.getTENPHONG()}", "${item.getNhatro().getMANT()}"]);
	    	</c:forEach>
	    	var arrDV = getAllByMaNT(array, maNt1)
	    	var dv1 = document.querySelector(".phong");
	    	while (dv1.options.length > 0) {
	    	     dv1.remove(dv1.options.length-1);
	    	}
	    	var dvsl = []
	    	for(let tmp of arrDV){
	    		dvsl.push(tmp)
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