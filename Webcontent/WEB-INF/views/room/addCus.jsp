<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/includes/header.jsp"%>
<link href="<c:url value='/resources/assets/bootstrap.min.css' />"
	rel="stylesheet" />
<style>
.container_cus {
	border: 1px solid #8d8d8d80;
	margin: 30px;
	width: 100%;
	padding: 30px;
}
</style>
<div class="app">
	<%@include file="/WEB-INF/views/includes/navbar.jsp"%>
	<div class="pseudo"></div>
	<main class="container_cus">
		<div class="form_cus">
			<h2 class="room_name">Thêm khách vào phòng</h2>
			<hr />
			<div class="form-row col-4" id="s1_loaikhach">
				<label for="loaikhach" class="room__label">Chọn loại khách</label> <select
					class="form-control" id="loaikhach">
					<option value="1">Khách đã từng dùng</option>
					<option value="2">Khách mới ở lần đầu</option>
				</select>
				<button type="submit" class="btn btn-success mt-3" id="step1">
					Tiếp <i class='bx bxs-chevrons-right'
						style="font-size: 18px; position: relative; top: 2px;"></i>
				</button>
			</div>

			<div class="form-row col-4" id="s21_loaikhach" style="display: none">
				<form action="room/addCustomer/khachcu/${id}.htm" method='POST' id="formCCCD">
					<label for="ho" class="room__label">Nhập căn cước/chứng
						minh nhân dân :</label> <input id="tenKC" class="form-control"
						name="cccdCK" />
					<button class="btn btn-warning mt-3 quaylai">
						<i class='bx bxs-chevrons-left'
							style="font-size: 18px; position: relative; top: 2px;"></i> Quay
						lại
					</button>
					<button type="submit" class="btn btn-success mt-3">
						Tiếp <i class='bx bxs-chevrons-right'
							style="font-size: 18px; position: relative; top: 2px;"></i>
					</button>

				</form>
			</div>



			<form:form method="POST" action="room/addCustomer/${id}.htm"
				modelAttribute='customer' style="display:none" id="s2_loaikhach"
				class="addKhachMoi">
				<form:input path="MAKT" style="display:none" />
				<div class="form-row col-8">
					<div class="form-group col-6">
						<label for="ho" class="room__label">Họ</label>
						<form:input type="text" id="ho" class="form-control" path="HO" />
					</div>
					<div class="form-group col-6">
						<label for="ho" class="room__label">Tên</label>
						<form:input id="ten" class="form-control" path="TEN" />
					</div>
				</div>
				<div class="form-row  col-8">
					<div class="form-group col-6">
						<label for="namsinh" class="room__label">Năm sinh</label>
						<form:input id="namsinh" class="form-control" path="NAMSINH" />
					</div>
					<div class="form-group col-6">
						<label for="gioitinh" class="room__label">Giới tính</label>
						<form:select id="gioitinh" class="form-control" path="GIOITINH"
							items="${gender}" />
					</div>
				</div>

				<div class="form-row col-8">
					<div class="form-group col-6">
						<label for="cccd" class="room__label">CMND/CCCD</label>
						<form:input type="number" id="cccd" class="form-control"
							path="CCCD" />
					</div>
					<div class="form-group col-6">
						<label for="email" class="room__label">Email</label>
						<form:input id="email" class="form-control" path="EMAIL" />

					</div>
				</div>

				<div class="form-row col-8">
					<div class="form-group col-6">
						<label for="std" class="room__label">SĐT</label>
						<form:input id="sdt" class="form-control" path="SDT" />
					</div>
					<div class="form-group col-6">
						<label for="nghenghiep" class="room__label">Nghề nghiệp</label>
						<form:input id="nghenghiep" class="form-control" path="NGHENGHIEP" />
					</div>

				</div>
				<div class="col-8">
					<label for="diachi" class="room__label">Địa chỉ</label>
					<form:textarea id="diachi" class="form-control" path="DIACHI" />
				</div>
				<div class="col-8">
					<button class="btn btn-warning mt-3 quaylai">
						<i class='bx bxs-chevrons-left'
							style="font-size: 18px; position: relative; top: 2px;"></i> Quay
						lại
					</button>
					<button type="submit" class="btn btn-success mt-3">
						Tiếp <i class='bx bxs-chevrons-right'
							style="font-size: 18px; position: relative; top: 2px;"></i>
					</button>
				</div>
			</form:form>

		</div>
	</main>
</div>

<script>
	document.getElementById("formCCCD").onsubmit=((e)=>{
		if(document.getElementById("tenKC").value.length == 0){
			e.preventDefault();
			alert("CCCD không được để trống !")
		}
	})
	let btnXoa = document.getElementById("step1");
	console.log(btnXoa);
	function validateEmail(value) {
		let regex = /^[a-zA-Z]+[1-9a-zA-Z]*@([a-zA-Z]+\.)+[a-zA-Z]+$/g
		return regex.test(value) ? true : false;
	}
	function validatePhone(value){
        let regex=/^(0)[0-9]{9}$/g
        return regex.test(value)?true:false;
    }
	btnXoa.addEventListener('click',()=>{
		let x = document.getElementById("loaikhach").value;
		document.getElementById("s1_loaikhach").style.display="none";
		if(x == 2){
			document.getElementById("s2_loaikhach").style.display="block";
		}else
		{
			document.getElementById("s21_loaikhach").style.display="block";			
		}
	})
	let btnQuayLai = document.getElementsByClassName("quaylai");
	const backF=(e)=>
	{
		e.preventDefault();
		document.getElementById("s2_loaikhach").style.display="none";
		document.getElementById("s21_loaikhach").style.display="none";	
		document.getElementById("s1_loaikhach").style.display="block";
	}
	for (let i = 0, l = btnQuayLai.length; i < l; i++) {
		btnQuayLai[i].addEventListener('click', backF, false);
	}
    let inputsText = document.querySelectorAll('.addKhachMoi [type="text"]')
	let formAddNew = document.getElementsByClassName("addKhachMoi")[0];
    formAddNew.onsubmit=((e)=>{
    	if(validateEmail(document.getElementById("email").value.trim()) == false)
		{
			e.preventDefault();
			alert("Email không đúng định dạng !")
			return;
		}
	if(validatePhone(document.getElementById("sdt").value.trim()) == false)
	{
		e.preventDefault();
		alert("Số điện thoại không đúng !")
		return;
	}
    	if(document.getElementById("namsinh").value<1900 || document.getElementById("namsinh").value>2022){
    		e.preventDefault();
    		alert("Năm sinh không hợp lệ !")
    		return;
    	} 
    	if(document.getElementById("cccd").value.length !=12){
    		e.preventDefault();
    		alert("CCCD phải đúng 12 số!")
    		return;
    	}
    	
    	for (let i = 1, l = inputsText.length; i < l; i++) {
    		if(inputsText[i].value.trim().length ==0)
    			{
    			e.preventDefault();
    			alert("Không được để trống bất kỳ mục nào !")
    			return;
    			}	
    	}
    })
    
</script>
</body>
</html>
