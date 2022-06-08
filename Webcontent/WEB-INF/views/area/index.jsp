<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/includes/header.jsp"%>
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

		<div class="room">
			<c:if test="${formHide == null }">
				<h2 class="room_name">Danh sách nhà trọ</h2>
				<hr />
				<c:if test="${message != null }">
					<div class="alert alert-primary" role="alert">${message}</div>
				</c:if>

				<div class="d-flex justify-content-between">
					<div class="col-6">
						<div class="form-group">
							<input type="text" id="searchRoom" class="form-control"
								onkeyup="searchRoom()" name="search" placeholder="Tìm kiếm ..." />
						</div>
					</div>
					<div class="mb-4 d-flex flex-row-reverse">
						<a href="area/index.htm?linkAdd">
							<button type="button" class="btn btn-primary">
								<i class='bx bx-plus-medical'></i> Thêm nhà trọ
							</button>
						</a>

					</div>

				</div>

				<table class="table table-bordered" id="customers">
					<thead>
						<tr style="text-align: center">

							<th scope="col">Tên nhà trọ</th>
							<th scope="col">Tỉnh/TP</th>
							<th scope="col">Quận/Huyện</th>
							<th scope="col">Phường/Xã</th>
							<th scope="col">Chủ Trọ</th>
							<th scope="col">Địa chỉ</th>
							<th scope="col">Xóa</th>
							<th scope="col">Sửa</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach var="k" items="${areas}">
							<tr class="dataTableRow">

								<td class="dataTableCol" style="width: 15%"><p>${k.TENNT}</p></td>
								<td style="width: 15%">${k.TINH_TP }</td>
								<td style="width: 10%">${k.QUAN_HUYEN }</td>
								<td style="width: 10%">${k.PHUONG_XA }</td>
								<td style="width: 10%"><span>${k.chutro.HO } </span><span>${k.chutro.TEN }</span></td>
								<td style="width: 23%">${k.DIACHI }</td>
								<td style="text-align: center;"><a class='btnXoaPhong'
									href="area/index/${k.MANT}.htm?linkDelete"><i
										class='bx bx-folder-minus' style="font-size: 18px;"></i></a></td>
								<td style="text-align: center;"><a
									href="area/index/${k.MANT}.htm?linkEdit"><i
										class='bx bx-edit' style="font-size: 18px;"></i></a></td>
							</tr>
						</c:forEach>

						<tr>

						</tr>
					</tbody>
				</table>


			</c:if>




			<c:if test="${formHide != null }">
				<h2 class="room_name">Thêm nhà trọ</h2>
				<hr />
				<form:form modelAttribute="area" method="POST"
					action="area/index.htm" id="formSelect">
					<form:input path="MANT" style="display:none" />
					<div class="form-row">
						<div class="form-group col-4">
							<label for="tenKhu" class="room__label">Tên nhà trọ</label>
							<form:input type="text" class="form-control" id="tenKhu"
								placeholder="Nhập tên khu" path="TENNT" />
						</div>
						<div class="form-group col-4">
							<label for="Tinh" class="room__label">Tỉnh/TP </label>
							<form:input type="text" class="form-control" id="Tinh"
								placeholder="Nhập Tỉnh/TP" path="TINH_TP" />
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-4">
							<label for="quanhuyen" class="room__label">Quận/Huyện</label>
							<form:input type="text" class="form-control" id="quanhuyen"
								placeholder="Nhập Quận/Huyện" path="QUAN_HUYEN" />
						</div>
						<div class="form-group col-4">
							<label for="phuongxa" class="room__label">Phường/Xã </label>
							<form:input type="text" class="form-control" id="phuongxa"
								placeholder="Nhập Phường / Xã" path="PHUONG_XA" />
						</div>
					</div>
					<div class="form-group col-8" style="display: none">
						<label for="chuTro" class="room__label">Chủ Trọ</label>
						<form:select id="chuTro" class="form-control" path="chutro.MACT"
							items="${ChuTroSelect}" itemLabel="TEN" itemValue="MACT">
						</form:select>
					</div>
					<div class="form-row">
						<div class="form-group col-8">
							<label for="diachi" class="room__label">Địa chỉ</label>
							<form:textarea class="form-control" id="diachi" rows="3"
								path="DIACHI"></form:textarea>
						</div>
					</div>
					<button type="submit" name="${btnStatus}"
						class="btn btn-success mt-3">
						<i class='bx bx-save'
							style="font-size: 18px; position: relative; top: 2px;"></i> Lưu
					</button>
				</form:form>

			</c:if>
		</div>
	</main>
</div>
<script>
	let btnXoa = document.getElementsByClassName("btnXoaPhong");
	console.log(btnXoa);
	const confirmIt = function(e) {
		if (!confirm('Bạn có chắc chắn muốn xoá không ?'))
			e.preventDefault();
	};
	for (let i = 0, l = btnXoa.length; i < l; i++) {
		btnXoa[i].addEventListener('click', confirmIt, false);
	}
	document.getElementById("tenKhu")

	let formSelect = document.getElementById("formSelect");
	console.log(formSelect);
	let tenKhu = document.getElementById("tenKhu");
	let tinh = document.getElementById("Tinh");
	let quanhuyen = document.getElementById("quanhuyen");
	let phuongxa = document.getElementById("phuongxa");
	let diachi = document.getElementById("diachi");
	formSelect.onsubmit = function(e) {

		if (tenKhu.value.length == 0 || tinh.value.trim().length == 0
				|| quanhuyen.value.trim().length == 0
				|| phuongxa.value.trim().length == 0
				|| diachi.value.trim().length == 0) {
			e.preventDefault();
			alert("Không được bỏ trống bất kì mục nào !")
		}
	}

	/* 	chuc nang search */
	function searchRoom() {
		let input, filter, p, i;
		input = document.getElementById("searchRoom");
		filter = input.value.toUpperCase();
		tr = document.getElementsByClassName("dataTableRow");
		td = document.getElementsByClassName("dataTableCol");
		for (i = 0; i < td.length; i++) {
			p = td[i].getElementsByTagName("p")[0];
			if (p.innerHTML.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
	/* finish chuc nang search */
</script>
</body>
</html>
