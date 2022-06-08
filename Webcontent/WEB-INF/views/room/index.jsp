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

.search_box {
	border: 1px solid #cbc5c5;
	border-radius: 9px;
	overflow: hidden;
	display: flex;
}

#searchRoom {
	border: none;
}

#typeInput {
	border: none;
	outline: none;
}
</style>

<div class="app">
	<%@include file="/WEB-INF/views/includes/navbar.jsp"%>
	<div class="pseudo"></div>
	<main class="container_cus">

		<div class="room">
			<c:if test="${formHide != null }">
				<h2 class="room_name">Thêm phòng</h2>
				<hr />
				<form:form modelAttribute="room" method="POST"
					action="room/index.htm" id="formE">
					<form:input path="MAPHONG" style="display:none" />
					<div class="form-row col-8">
						<div class="col-6" style="display: none">
							<label for="TrangThai" class="room__label">Trạng thái</label>
							<form:select id="TrangThai" class="form-control"
								path="trangThai.MATT" items="${TrangThaiSelect}"
								itemLabel="TENTT" itemValue="MATT">
							</form:select>
						</div>

						<div class="col-12">
							<label for="NhaTro" class="room__label">Nhà trọ</label>
							<form:select id="NhaTro" class="form-control" path="nhatro.MANT"
								items="${KhuSelect}" itemLabel="TENNT" itemValue="MANT">
							</form:select>
						</div>
					</div>
					<div class="col-8 form-row">
						<div class="col-6">
							<label for="loaiPhong" class="room__label">Loại Phòng</label>
							<form:select id="loaiPhong" class="form-control"
								path="loaiPhong.MALOAI" items="${LoaiPhongSelect}"
								itemLabel="TENLOAI" itemValue="MALOAI">
							</form:select>
						</div>

						<div class="col-6">
							<label for="Phong" class="room__label">Tên Phòng</label>
							<form:input id="Phong" class="form-control" path="TENPHONG" />
						</div>
					</div>


					<div class="col-8">
						<label for="MoTa" class="room__label">Mô Tả Riêng(optinal)</label>
						<form:textarea class="form-control" id="MoTa" rows="3"
							path="MOTARIENG" value="Phòng ngonnnnn"></form:textarea>
						<form:errors path="MOTARIENG" />
					</div>
					<button type="submit" name="${btnStatus}"
						class="btn btn-success mt-3" id="btnSubmit">
						<i class='bx bx-save'
							style="font-size: 18px; position: relative; top: 2px;"></i> Lưu
					</button>
				</form:form>
			</c:if>

			<c:if test="${formHide == null }">
				<h2 class="room_name">Danh sách phòng</h2>
				<hr />
				<c:if test="${message != null }">
					<div class="alert alert-primary" role="alert">${message}</div>
				</c:if>
				<c:if test="${sl != 0 }">
					<div class="d-flex justify-content-between">
						<div class="col-6">
							<div class="form-group search_box">
								<input type="text" id="searchRoom" class="form-control"
									onkeyup="searchRoom()" name="search"
									placeholder="Tìm kiếm theo ..." /> <select name="type"
									id="typeInput">
									<option value="tenPhong" selected="selected">Tên Phòng</option>
									<option value="dienTich">Diện tích</option>
									<option value="SLNTD">Số người tối đa</option>
									<option value="loaiPhong">Loại phòng</option>
									<option value="nhaTro">Nhà trọ</option>
									<option value="trangThai">Trạng thái</option>
								</select>
							</div>
						</div>
						<div class="mb-4 d-flex flex-row-reverse">
							<div class="ml-2">
								<a href="room/index.htm?linkAdd">
									<button type="button" class="btn btn-primary">
										<i class='bx bx-plus-medical'></i> Thêm Phòng
									</button>
								</a>
							</div>
							<a href="room/typeroom/index.htm">
								<button type="button" class="btn btn-success">Xem Loại
									Phòng</button>
							</a>
						</div>


					</div>
				</c:if>


				<table class="table table-bordered" id="customers">
					<thead>
						<tr style="text-align: center">
							<th scope="col">Phòng</th>
							<th scope="col">Loại phòng</th>
							<th scope="col">Diện tích</th>
							<th scope="col">Số người tối đa</th>
							<th scope="col">Đơn giá</th>
							<th scope="col">Khu</th>
							<th scope="col">Trạng thái</th>
							<th scope="col">Mô tả</th>
							<th scope="col">Xóa</th>
							<th scope="col">Sửa</th>
							<th scope="col">Thêm khách</th>
							<th scope="col">Thêm dịch vụ</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="r" items="${rooms}">
							<tr class="dataTableRow">
								<td  style="width: 7%" class="tdInfo">
								<p id="tenPhong">
								<a
									href="room/desc-hop-dong/${r.MAPHONG}.htm"> ${r.TENPHONG} </a>
								</p>
								</td>
								<td style="width: 8%" class="tdInfo"><p id="loaiPhong">${r.loaiPhong.TENLOAI}</p></td>
								<td style="width: 8%" class="tdInfo"><p id="dienTich">${r.loaiPhong.DIENTICH}</p></td>
								<td style="width: 8%" class="tdInfo"><p id="SLNTD">${r.loaiPhong.SLNGUOITD}</p></td>
								<td style="width: 15%">${r.loaiPhong.DONGIA }</td>
								<td style="width: 10%" class="tdInfo"><p id="nhaTro">${r.nhatro.TENNT }</p></td>
								<td style="width: 10%" class="tdInfo"><p id="trangThai">${r.trangThai.TENTT }</p></td>
								<td style="width: 30%">${r.MOTARIENG }</td>
								<td style="text-align: center;"><a class="btnXoaPhong"
									href="room/index/${r.MAPHONG}.htm?linkDelete"><i
										class='bx bx-folder-minus' style="font-size: 18px;"></i></a></td>
								<td style="text-align: center;"><a
									href="room/index/${r.MAPHONG}.htm?linkEdit"><i
										class='bx bx-edit' style="font-size: 18px;"></i></a></td>
								<td style="font-size: 24px; text-align: center"><c:choose>
										<c:when test="${r.trangThai.MATT == 3 }">
											<i class='bx bx-user-plus'
												style="font-size: 22px; color: gray"></i>
										</c:when>
										<c:otherwise>
											<a href="room/addCustomer/${r.MAPHONG}.htm"
												style="position: relative; top: -8px;"> <i
												class='bx bx-user-plus' style="font-size: 22px"></i>
											</a>
										</c:otherwise>
									</c:choose></td>
								<td><a href="service/addService/${r.MAPHONG}.htm"> <i
										class='bx bx-add-to-queue'></i>
								</a></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>

			</c:if>

		</div>
	</main>
</div>
<script>
/* 	chuc nang search */
let typeInput = document.getElementById("typeInput");
let typeSearch;
typeInput.onchange=(e)=>
{
	typeSearch = typeInput.value;
}


function searchRoom() {
	
	let input, filter, a;
	let td = [];
	input = document.getElementById("searchRoom");
	filter = input.value.toUpperCase();
	tr = document.getElementsByClassName("dataTableRow");
	for(let i = 0 ; i<tr.length ; i++)
		{
			for(let j =0 ; j < tr[i].getElementsByClassName('tdInfo').length ; j++)
				{
					td.push(tr[i].getElementsByClassName('tdInfo')[j]);
				}
		}
		
	var idCheck = "#"+typeSearch;
	for(let j =0 ; j < td.length ; j++)
		{
		if(td[j].querySelector(idCheck) != undefined)
		{
		
			if (td[j].querySelector(idCheck).outerText.toUpperCase().indexOf(filter) > -1) {
				tr[Math.floor(j/6)].style.display = "";
			}else
			{
				tr[Math.floor(j/6)].style.display = "none";
			}
		}
		}
}
/* finish chuc nang search */
</script>
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
	document.getElementById("formE").onsubmit = function(e) {
		if (document.getElementById("Phong").value.length == 0) {
			e.preventDefault();
			alert("Không được bỏ trống tên phòng !")
		}
	}

	
</script>
</body>
</html>