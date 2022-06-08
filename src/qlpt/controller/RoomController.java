package qlpt.controller;

import java.awt.print.Pageable;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import qlpt.entity.CTKhachThueEntity;
import qlpt.entity.ChuTroEntity;
import qlpt.entity.DichVuEntity;
import qlpt.entity.HoaDonEntity;
import qlpt.entity.HopDongEntity;
import qlpt.entity.KhachThueEntity;
import qlpt.entity.LoaiPhongEntity;
import qlpt.entity.NhaTroEntity;
import qlpt.entity.PhongEntity;
import qlpt.entity.TrangThaiEntity;

@Transactional
@Controller
@RequestMapping("room/")
public class RoomController {

	@Autowired
	SessionFactory factory;

	private String mact;

	@RequestMapping("index")
	public String index(ModelMap model, @ModelAttribute("room") PhongEntity room, HttpSession ss) {
		mact = ss.getAttribute("mact").toString();
		System.out.println("index " + mact);
		List<PhongEntity> rooms = this.getRooms();
		model.addAttribute("rooms", rooms);
		model.addAttribute("formHide", null);
//		if(page + 1 <= this.getRooms().size() / 6 && page >= 0)
//		{
//			model.addAttribute("page",page);
//		}else
//		{
//			model.addAttribute("page",-1);
//		}
//		if(page == 0)
//		{
//			System.out.println(this.getRooms().size() / 6 + 1);
//			model.addAttribute("page",this.getRooms().size() / 6 + 1);
//		}
		int sl = this.getSLNT();
		model.addAttribute("sl", sl);
		return "room/index";
	}

	@RequestMapping(value = "index", params = "linkAdd")
	public String redirectLinkAdd(ModelMap model, @ModelAttribute("room") PhongEntity room) {
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("formHide", 1);

		return "room/index";
	}

	public List<String> getMANT() {
		System.out.println("getMANT " + mact);
		Session session = factory.getCurrentSession();
		String hql = "SELECT MANT FROM NhaTroEntity WHERE MACT = :mact";
		Query query = session.createQuery(hql);
		query.setParameter("mact", mact);
		List<String> list = query.list();
		return list;
	}

	public List<PhongEntity> getRooms() {
//		String result = String.join(",",this.getMANT());
//		System.out.println(result);
		List<String> listnt = this.getMANT();
		if (!listnt.isEmpty()) {
			Session session = factory.getCurrentSession();
			String hql = "FROM PhongEntity WHERE MANT in (:listMANT)";
			Query query = session.createQuery(hql);
			query.setParameterList("listMANT", this.getMANT());
			List<PhongEntity> list = query.list();
			return list;
		} else {
			return Collections.emptyList();
		}

	}

	public List<PhongEntity> getRooms(int page, int amount) {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhongEntity p ORDER BY p.MAPHONG DESC";
		Query query = session.createQuery(hql);
		query.setFirstResult(page * amount);
		query.setMaxResults(amount);
		List<PhongEntity> list = query.list();
		return list;
	}

	@ModelAttribute("KhuSelect")
	public List<NhaTroEntity> getNhaTros() {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhaTroEntity WHERE MACT = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", mact);
		List<NhaTroEntity> list = query.list();

		return list;
	}

	public TrangThaiEntity getTrangThaiRieng(Integer maTT) {
		Session session = factory.getCurrentSession();
		String hql = "FROM TrangThaiEntity where MATT = :maTT";
		Query query = session.createQuery(hql);
		query.setParameter("maTT", maTT);
		TrangThaiEntity list = (TrangThaiEntity) query.list().get(0);
		return list;
	}

	@ModelAttribute("LoaiPhongSelect")
	public List<TrangThaiEntity> getLoaiPhongg() {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiPhongEntity";
		Query query = session.createQuery(hql);
		List<TrangThaiEntity> list = query.list();
		return list;
	}

	// ADD room
	public Integer insertRoom(PhongEntity phong) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(phong);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	@RequestMapping(value = "index", params = "btnAdd")
	public String addRoom(@ModelAttribute("room") PhongEntity room, ModelMap model, BindingResult errors) {
		room.setTrangThai(this.getTrangThaiRieng(1));
		Integer temp = this.insertRoom(room);
		if (temp != 0)
			model.addAttribute("message", "Thêm thành công");
		else
			model.addAttribute("message", "Thêm thất bại!");
		model.addAttribute("rooms", this.getRooms());

		return "room/index";
	}

	// Finish add room
	// Edit room
	public PhongEntity getRoom(Integer id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhongEntity where MAPHONG = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		PhongEntity list = (PhongEntity) query.list().get(0);

		return list;
	}

	@RequestMapping(value = "index", params = "btnEdit")
	public String edit_Room(ModelMap model, @ModelAttribute("room") PhongEntity room) {

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			System.out.println(room.getMAPHONG());
			String hql = "update PhongEntity set MOTARIENG = :motarieng, MANT = :mant, MALOAI = :maloai, TENPHONG = :tenphong where MAPHONG = "
					+ String.valueOf(room.getMAPHONG());
			Query query = session.createQuery(hql);
			query.setParameter("motarieng", room.getMOTARIENG());
			query.setParameter("mant", room.getNhatro().getMANT());
			query.setParameter("maloai", room.getLoaiPhong().getMALOAI());
			query.setParameter("tenphong", room.getTENPHONG());
			int a = query.executeUpdate();
			model.addAttribute("message", "Update thành công");
			t.commit();

		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			e.printStackTrace();
			model.addAttribute("message", "Update thất bại!");
		} finally {
			session.close();
		}

		model.addAttribute("rooms", this.getRooms());

		return "room/index";
	}

	// Thả dữ liệu vô form
	@RequestMapping(value = "index/{id}", params = "linkEdit")
	public String editRoommmm(@ModelAttribute("room") PhongEntity room, ModelMap model, @PathVariable("id") int id) {
		model.addAttribute("btnStatus", "btnEdit");
//		System.out.println("check1");
		model.addAttribute("room", this.getRoom(id));
		// System.out.println(this.getRoom(id).getMAPHONG());

//     	model.addAttribute("rooms", this.getRooms());
//		System.out.println("check2");
		model.addAttribute("formHide", 1);
		return "room/index";
	}
	// Finish Edit room
	// DELETE ROOM

	@RequestMapping(value = "/index/{id}.htm", params = "linkDelete")
	public String delete(@PathVariable("id") int id, ModelMap model, @ModelAttribute("room") PhongEntity room) {
		System.out.println("check");
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
//        String hql = "FROM PhongEntity where MAPHONG = :id";	
		String hql = "DELETE FROM PhongEntity where MAPHONG = :id";
		try {
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
//		PhongEntity room1 = (PhongEntity) query.list().get(0);
//		session.delete(room1);
			query.executeUpdate();
			t.commit();
			String success = "Xóa thành công phòng " + id + " !";
			model.addAttribute("message", success);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			e.printStackTrace();
			t.rollback();
			String failure = "Xóa thất bại phòng vì đã có khách ở rồi !";
			model.addAttribute("message", failure);

		} finally {
			session.close();
		}
		model.addAttribute("rooms", this.getRooms());
		return "room/index";

	}

	// FINISH DELETE ROOM
	// TYPE ROOM
	@RequestMapping("typeroom/index")
	public String typeRoom(ModelMap model) {
		model.addAttribute("loaiPhong", this.getTypeRooms());
		model.addAttribute("formHide", null);

		return "room/typeroom";
	}

	// get type room
	public List<LoaiPhongEntity> getTypeRooms() {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiPhongEntity";
		Query query = session.createQuery(hql);
		List<LoaiPhongEntity> list = query.list();
		return list;
	}
	// END TYPE ROOM
	// ADD type room

	public Integer insertArea(LoaiPhongEntity loai) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(loai);
			t.commit();
		} catch (Exception e) {
			System.out.println("Loi Loai phong " + e.getMessage());
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	@RequestMapping(value = "typeroom/index", params = "linkAdd")
	public String redirectLinkAdd(ModelMap model, @ModelAttribute("loaiPhong") LoaiPhongEntity loai) {
		model.addAttribute("formHide", 1);
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("areas", this.getTypeRooms());
		return "room/typeroom";
	}

	@RequestMapping(value = "typeroom/index", params = "btnAdd")
	public String addTypeRoom(ModelMap model, @ModelAttribute("loaiPhong") LoaiPhongEntity loai) {
		Integer temp = this.insertArea(loai);
		if (temp != 0)
			model.addAttribute("message", "Thêm thành công");
		else
			model.addAttribute("message", "Thêm thất bại!");
		model.addAttribute("loaiPhong", this.getTypeRooms());
		return "room/typeroom";
	}

	// end add type room
	public Integer deleteRoom(Integer maLoai) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		String hql = "DELETE FROM PhongEntity where MALOAI = :maLoai";
		try {
			Query query = session.createQuery(hql);
			query.setParameter("maLoai", maLoai);
			query.executeUpdate();
			t.commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
	}

	@RequestMapping(value = "/typeroom/index/{id}.htm", params = "linkDelete")
	public String deleteTypeRoom(@PathVariable("id") int id, ModelMap model,
			@ModelAttribute("loaiPhong") LoaiPhongEntity loaiPhong) {
		System.out.println("check");
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		String hql = "DELETE FROM LoaiPhongEntity where MALOAI = :id";
		try {
			this.deleteRoom(id);
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			query.executeUpdate();
			t.commit();
			String success = "Xóa thành công !";
			model.addAttribute("message", success);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			e.printStackTrace();
			t.rollback();
			String failure = "Xóa thất bại !";
			model.addAttribute("message", failure);

		} finally {
			session.close();
		}
		model.addAttribute("loaiPhong", this.getTypeRooms());
		return "room/typeroom";
	}
	// delete room

	// edit type room
	public LoaiPhongEntity getOneType(Integer id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiPhongEntity where MALOAI = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		LoaiPhongEntity list = (LoaiPhongEntity) query.list().get(0);

		return list;
	}

	@RequestMapping(value = "typeroom/index", params = "btnEdit")
	public String edit_TypeRoom(ModelMap model, @ModelAttribute("loaiPhong") LoaiPhongEntity loaiPhong) {

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			System.out.println(loaiPhong.getMALOAI());
			String hql = "update LoaiPhongEntity set TENLOAI = :tenloai , DIENTICH = :dientich , SLNGUOITD = :sltd , DONGIA = :dongia , MOTA =:mota WHERE MALOAI ="
					+ String.valueOf(loaiPhong.getMALOAI());

			System.out.println(hql);
			Query query = session.createQuery(hql);
			query.setParameter("tenloai", loaiPhong.getTENLOAI());
			query.setParameter("dientich", loaiPhong.getDIENTICH());
			query.setParameter("sltd", loaiPhong.getSLNGUOITD());
			query.setParameter("dongia", loaiPhong.getDONGIA());
			query.setParameter("mota", loaiPhong.getMOTA());

			int a = query.executeUpdate();
			model.addAttribute("message", "Update thành công");
			t.commit();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Loi edit type " + e.getMessage());
			t.rollback();
			e.printStackTrace();
			model.addAttribute("message", "Update thất bại!");
		} finally {
			session.close();
		}

		model.addAttribute("loaiPhong", this.getTypeRooms());

		return "room/typeroom";
	}

	// Thả dữ liệu vô form
	@RequestMapping(value = "typeroom/index/{id}", params = "linkEdit")
	public String typeRoomEdit(ModelMap model, @PathVariable("id") Integer id,
			@ModelAttribute("loaiPhong") LoaiPhongEntity loaiPhong) {
		System.out.println("Check");
		model.addAttribute("btnStatus", "btnEdit");
		model.addAttribute("loaiPhong", this.getOneType(id));
		model.addAttribute("formHide", 1);
		return "room/typeroom";
	}
	// end edit type room

//KHACH THUE
	@ModelAttribute("gender")
	public String[] getGender() {
		String[] genders = { "Nam", "Nữ" };
		return genders;
	}

	@RequestMapping("addCustomer/{id}.htm")
	public String showForm(@ModelAttribute("customer") KhachThueEntity customer, ModelMap model,
			@PathVariable("id") String id) {
		model.addAttribute("id", id);
		return "room/addCus";
	}

	public Integer insertCustomer(KhachThueEntity customer) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(customer);
			t.commit();
		} catch (Exception e) {
			System.out.println("Loi area " + e.getMessage());
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	private KhachThueEntity kt;
	private Integer idPhong;
	private PhongEntity phongtemp;

	@RequestMapping(value = "addCustomer/{id}.htm", method = RequestMethod.POST)
	public RedirectView addCustomer(ModelMap model, @ModelAttribute("customer") KhachThueEntity customer,
			HttpServletResponse res, HttpServletRequest req, @PathVariable("id") int id,
			RedirectAttributes redirectAttributes) {
		System.out.println("check add Cus");
		String uuid = UUID.randomUUID().toString();
		customer.setMAKT(uuid);
		this.kt = customer;
		this.idPhong = id;
		KhachThueEntity kt = this.checkNguoiDaiDien(id);
		RedirectView rv;
		if (kt == null) {
			rv = new RedirectView("/room/hopdong.htm", true);
		} else {
			CTKhachThueEntity ctkt = new CTKhachThueEntity();
			ctkt.setKhachThue(this.kt);
			ctkt.setTRANGTHAI(true);
			ctkt.setHopDong(this.getHD(id));
			Integer temp, temp1;
			temp = this.insertCustomer(this.kt);
			temp1 = this.insertCTKT(ctkt);
			if (temp != 0 && temp1 != 0) {
				HopDongEntity hopdong = this.getHD(id);
				int sltd = hopdong.getPhong().getLoaiPhong().getSLNGUOITD();
				Long x = checkSoNguoiThue(hopdong.getMAHOPDONG());
				if (sltd == x) {
					this.updateTrangThaiPhong(this.idPhong, 3);
				} else if ((sltd < x) && (x > 0)) {
					this.updateTrangThaiPhong(this.idPhong, 2);
				}
				redirectAttributes.addFlashAttribute("message", "Thêm khách vào phòng thành công");
			} else {
				redirectAttributes.addFlashAttribute("message", "Thêm khách vào phòng thất bại");
			}

			rv = new RedirectView("/room/desc-hop-dong/" + this.idPhong + ".htm", true);

		}

		return rv;
	}

	private KhachThueEntity checkNguoiDaiDien(int id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDongEntity hd WHERE hd.phong.MAPHONG = :idPhong AND DAHUY = False";
		Query query = session.createQuery(hql);
		query.setParameter("idPhong", id);
		List<HopDongEntity> ktdt = query.list();

		if (ktdt.size() == 0)
			return null;
		return ktdt.get(0).getKhachThueDaiDien();
	}

	private HopDongEntity getHD(KhachThueEntity xxx) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDongEntity hd WHERE khachThueDaiDien = :ktdd";
		Query query = session.createQuery(hql);
		query.setParameter("ktdd", xxx);
		List<HopDongEntity> ktdt = query.list();

		return ktdt.get(0);
	}

	private HopDongEntity getHD(Integer maPhong) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDongEntity hd WHERE hd.phong.MAPHONG  = :maPhong AND DAHUY = false";
		Query query = session.createQuery(hql);
		query.setParameter("maPhong", maPhong);
		List<HopDongEntity> hd = query.list();

		return hd.get(hd.size() - 1);
	}

	@RequestMapping("hopdong")
	public String hopdong(@ModelAttribute("hopdong") HopDongEntity hopdong) {

		return "room/hopdong";
	}

	public Integer insertHopDong(HopDongEntity hd) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(hd);
			t.commit();
		} catch (Exception e) {
			System.out.println("Loi hd " + e.getMessage());
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public Integer insertCTKT(CTKhachThueEntity ctkt) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(ctkt);
			t.commit();
		} catch (Exception e) {
			System.out.println("Loi ctkt " + e.getMessage());
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	@RequestMapping(value = "themHopDong", method = RequestMethod.POST)
	public RedirectView addHopDong(ModelMap model, @ModelAttribute("hopdong") HopDongEntity hopdong,
			RedirectAttributes redirectAttributes) throws ParseException {
		CTKhachThueEntity ctkt = new CTKhachThueEntity();

		ctkt.setKhachThue(this.kt);
		ctkt.setTRANGTHAI(true);
		Integer temp = 1, temp1, temp2;
		KhachThueEntity a = this.checkNguoiDaiDien(this.idPhong);
		hopdong.setKhachThueDaiDien(this.kt);
		hopdong.setPhong(this.getRoom(this.idPhong));
		hopdong.setDAHUY(false);

		RedirectView rv;
		if (hopdong.getNGAYKY().after(hopdong.getTHOIHAN())) {
			redirectAttributes.addFlashAttribute("message", "Ngày ký không thể sau ngày kết thúc !");
			rv = new RedirectView("/room/hopdong.htm", true);
			return rv;
		}

		ctkt.setHopDong(hopdong);
		if (this.checkKhachTungO(this.kt.getCCCD()) == null) {
			temp = this.insertCustomer(this.kt);
		}

		temp1 = this.insertHopDong(hopdong);
		temp2 = this.insertCTKT(ctkt);
		this.updateTrangThaiPhong(this.idPhong, 2);
		if (temp != 0 && temp2 != 0) {
			redirectAttributes.addFlashAttribute("message", "Thêm khách vào phòng thành công");
		} else
			redirectAttributes.addFlashAttribute("message", "Thêm khách thất bại!");
		redirectAttributes.addFlashAttribute("rooms", this.getRooms());
		rv = new RedirectView("/room/desc-hop-dong/" + this.idPhong + ".htm", true);
		return rv;
	}

	@RequestMapping("desc-hop-dong/{id}")
	public String xemHopDong(@PathVariable("id") int id, ModelMap model) {
		Session session = factory.getCurrentSession();
		String hql = "Select MAHOPDONG FROM HopDongEntity hd WHERE hd.phong.MAPHONG = :id AND hd.DAHUY=False";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		List<Integer> listHD = query.list();
		if (listHD.size() == 0) {
			model.addAttribute("khach", null);
			return "room/descPhong";
		}
		String hql1 = "Select khachThue.MAKT FROM CTKhachThueEntity ct WHERE ct.hopDong.MAHOPDONG IN (:mahd) AND ct.TRANGTHAI = True";
		Session s1 = factory.getCurrentSession();
		Query query1 = s1.createQuery(hql1);
		query1.setParameterList("mahd", listHD);
		List<String> listKT = query1.list();
		if (listKT.size() == 0) {
			model.addAttribute("khach", null);
			return "room/descPhong";
		}

		String hql3 = "FROM KhachThueEntity kt WHERE kt.MAKT IN (:listmakt)";
		Session s2 = factory.getCurrentSession();
		Query query2 = s2.createQuery(hql3);
		query2.setParameterList("listmakt", listKT);
		List<KhachThueEntity> list = query2.list();
		model.addAttribute("khach", list);
		model.addAttribute("maPhong", id);

		model.addAttribute("r", this.getRoom(id));
		return "room/descPhong";
	}

	// check so nguoi thue hien tai de doi trang thai
	public Long checkSoNguoiThue(Integer mahd) {
		Session session = factory.getCurrentSession();
		String hql = "Select count (*) FROM CTKhachThueEntity ct WHERE ct.hopDong.MAHOPDONG= :mahd AND TRANGTHAI = true";
		Query query = session.createQuery(hql);
		query.setParameter("mahd", mahd);
		Long soNguoi = (Long) query.uniqueResult();
		return soNguoi;
	}

	public Integer updateTrangThaiPhong(int maPhong, int maTT) {

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			String hql = "update PhongEntity set trangThai.MATT = :maTT where MAPHONG = :maPhong";
			Query query = session.createQuery(hql);
			query.setParameter("maTT", maTT);
			query.setParameter("maPhong", maPhong);
			int a = query.executeUpdate();
			t.commit();
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
	}

	// end check so nguoi
// HUỶ KHÁCH THUÊ K THUÊ NỮA
	public CTKhachThueEntity getCTKhachThue(String maKT, Integer maHopDong) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTKhachThueEntity ct where ct.khachThue.MAKT = :maKT AND ct.hopDong.MAHOPDONG = :maHopDong AND ct.TRANGTHAI = true";
		Query query = session.createQuery(hql);
		query.setParameter("maKT", maKT);
		query.setParameter("maHopDong", maHopDong);
		CTKhachThueEntity list = (CTKhachThueEntity) query.list().get(0);
		return list;
	}

	public HopDongEntity getHopDong(Integer maHopDong) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDongEntity where MAHOPDONG = :maHopDong";
		Query query = session.createQuery(hql);
		query.setParameter("maHopDong", maHopDong);
		HopDongEntity list = (HopDongEntity) query.list().get(0);
		return list;
	}

	public CTKhachThueEntity getKT(Integer mahd) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTKhachThueEntity ct WHERE ct.hopDong.MAHOPDONG = :mahd AND ct.TRANGTHAI = True";
		Query query = session.createQuery(hql);
		query.setParameter("mahd", mahd);
		List<CTKhachThueEntity> ct = query.list();
		return ct.get(0);
	}

	@RequestMapping(value = "huyhopdong/{maKT}/{maPhong}")
	public RedirectView huyHopDong(RedirectAttributes ra, @PathVariable("maKT") String maKT,
			@PathVariable("maPhong") Integer maPhong) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		HopDongEntity hd = this.getHD(maPhong);
		CTKhachThueEntity ctkt = this.getCTKhachThue(maKT, hd.getMAHOPDONG());
		HopDongEntity hoaDon = ctkt.getHopDong();
		if (ktTrangThaiHoaDon(hoaDon.getMAHOPDONG(), maKT)) {
			ra.addFlashAttribute("message", "Vui lòng thanh toán hóa đơn trước khi rời đi!");
			RedirectView rv = new RedirectView("/room/desc-hop-dong/" + maPhong + ".htm", true);
			return rv;
		}
		try {
			String hql = "update CTKhachThueEntity set TRANGTHAI = false WHERE khachThue.MAKT = :maKT AND hopDong.MAHOPDONG = :mahopdong";
			Query query = session.createQuery(hql);
			query.setParameter("maKT", maKT);
			query.setParameter("mahopdong", hd.getMAHOPDONG());
			int a = query.executeUpdate();
			t.commit();
			ra.addFlashAttribute("message", "Đã huỷ hợp đồng của khách thành công !");
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			e.printStackTrace();
			System.out.println("Loi update Huy Khach" + e.getMessage());
			ra.addFlashAttribute("message", "Lỗi khi xoá khách thuê khỏi phòng !");
			RedirectView rv = new RedirectView("/room/desc-hop-dong/" + maPhong + ".htm", true);
			return rv;
		} finally {
//			session.close();
		}
//		Truong la khach dai dien
		Integer mahd = hd.getMAHOPDONG();
		// truong hop phong con nguoi
		if (maKT.equals(hd.getKhachThueDaiDien().getMAKT()) && (this.checkSoNguoiThue(mahd) > 0)) {

			HopDongEntity hdNew = new HopDongEntity(hd.getNGAYKY(), hd.getTIENCOC(), false, hd.getTHOIHAN(),
					this.getKT(mahd).getKhachThue(), hd.getPhong());

			Integer temp = this.insertHopDong(hdNew);

			String hql = "update CTKhachThueEntity ct set ct.hopDong.MAHOPDONG = :mahdNew WHERE ct.hopDong.MAHOPDONG = :mahdOld AND ct.khachThue.MAKT <> :maktdt";
			Query query = session.createQuery(hql);
			query.setParameter("mahdNew", hdNew.getMAHOPDONG());
			query.setParameter("mahdOld", hd.getMAHOPDONG());
			query.setParameter("maktdt", hd.getKhachThueDaiDien().getMAKT());
			int affectedRows = query.executeUpdate();

			hql = "UPDATE HopDongEntity SET DAHUY = true WHERE MAHOPDONG = :mahd";
			query = session.createQuery(hql);
			query.setParameter("mahd", hd.getMAHOPDONG());
			query.executeUpdate();

			this.updateTrangThaiPhong(maPhong, 2);

		} // Truong hop phong da het nguoi
		else if (maKT.equals(hd.getKhachThueDaiDien().getMAKT()) && (this.checkSoNguoiThue(mahd) == 0)) {
			String hql = "UPDATE HopDongEntity SET DAHUY = true WHERE MAHOPDONG = :mahd";
			Query query = session.createQuery(hql);
			query.setParameter("mahd", mahd);
			int affectedRows = query.executeUpdate();
			session.close();
			this.updateTrangThaiPhong(maPhong, 1);
		}

		else if (!maKT.equals(hd.getKhachThueDaiDien().getMAKT()))
			this.updateTrangThaiPhong(maPhong, 2);

		RedirectView rv = new RedirectView("/room/desc-hop-dong/" + maPhong + ".htm", true);
		return rv;
	}

// END HUỶ KHÁCH
//END KHACHTHUE
// XEM HOP DONG
	private ChuTroEntity getChuTro(HttpSession ss) {
		String mact = ss.getAttribute("mact").toString();
		Session session = factory.getCurrentSession();
		String hql = "FROM ChuTroEntity where MACT = :mact";
		Query query = session.createQuery(hql);
		query.setParameter("mact", mact);
		ChuTroEntity list = (ChuTroEntity) query.list().get(0);
		return list;
	}

	@RequestMapping("xemhopdong/{maPhong}.htm")
	public String xemHopDong(@PathVariable("maPhong") Integer maPhong, ModelMap model, HttpSession ss) {

		ChuTroEntity ct = this.getChuTro(ss);
		model.addAttribute("chu", ct);

		HopDongEntity hd = this.getHD(maPhong);
		model.addAttribute("hd", hd);
		model.addAttribute("khach", hd.getKhachThueDaiDien());

		return "hopdong/index";
	}

	// END XEM HOPDONG
	// GIA HẠN HỢP ĐỒNG
	@RequestMapping("giahanhopdong/{maPhong}")
	public String giahanGet(@PathVariable("maPhong") Integer maPhong, ModelMap model) {
		model.addAttribute("maPhong", maPhong);
		return "room/giahan";
	}

	@RequestMapping(value = "giahanhopdong/{maPhong}", method = RequestMethod.POST)
	public RedirectView giahanPost(HttpServletRequest req, @PathVariable("maPhong") Integer maPhong,
			RedirectAttributes ra) throws ParseException {
		String thoihanmoi = req.getParameter("THOIHANMOI");
		Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(thoihanmoi);
		HopDongEntity hd = this.getHD(maPhong);
		hd.setTHOIHAN(date1);

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(hd);
			t.commit();
			ra.addFlashAttribute("mess", "Cập nhật hợp đồng thành công !");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			ra.addFlashAttribute("mess", "Cập nhật hợp đồng thành công !");
		} finally {
			session.close();
		}
		RedirectView rv = new RedirectView("/room/desc-hop-dong/" + maPhong + ".htm", true);
		return rv;

	}

	// END GIA HẠN HD
	// bắt 1 số lỗi
	public int getSLNT() {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhaTroEntity nt where nt.chuTro.MACT = :mact";
		Query query = session.createQuery(hql);
		query.setParameter("mact", this.mact);
		List<NhaTroEntity> list = query.list();
		if (list.size() == 0)
			return 0;
		return 1;
	}
	// end

	// TRẢ PHÒNG
	@RequestMapping("traphong/{maPhong}")
	public RedirectView traPhongGet(@PathVariable("maPhong") Integer maPhong, RedirectAttributes ra) {
		HopDongEntity hd = this.getHD(maPhong);
		HopDongEntity hoaDon = getHD(maPhong);
		if (ktTrangThaiHoaDontheoMaHD(hoaDon.getMAHOPDONG())) {
			ra.addFlashAttribute("mess", "Vui lòng thanh toán hóa đơn trước khi rời đi!");
			RedirectView rv = new RedirectView("/room/desc-hop-dong/" + maPhong + ".htm", true);
			return rv;
		}
		Session session = factory.openSession();

		Transaction t = session.beginTransaction();
		try {
			String hql = "update HopDongEntity hd set hd.DAHUY = 1 where hd.MAHOPDONG = :maHD";
			Query query = session.createQuery(hql);
			query.setParameter("maHD", this.getHD(maPhong).getMAHOPDONG());
			int a = query.executeUpdate();

			Integer maHD = hd.getMAHOPDONG();
			hql = "update CTKhachThueEntity ct set ct.TRANGTHAI = false where ct.hopDong.MAHOPDONG = :maHD";
			query = session.createQuery(hql);
			query.setParameter("maHD", maHD);
			int b = query.executeUpdate();

			t.commit();
			ra.addFlashAttribute("mess", "Đã trả phòng thành công !");
			this.updateTrangThaiPhong(maPhong, 1);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			ra.addFlashAttribute("mess", "Trả phòng thất bại !");
		} finally {
			session.close();
		}
		RedirectView rv = new RedirectView("/room/desc-hop-dong/" + maPhong + ".htm", true);
		return rv;
	}

	// END TRẢ PHÒNG
	// KHACH TUNG Ở
	public String checkKhachTungO(String cccd) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTKhachThueEntity nt where nt.khachThue.CCCD = :cccd";
		Query query = session.createQuery(hql);
		query.setParameter("cccd", cccd);
		List<CTKhachThueEntity> list = query.list();
		if (list.size() == 0)
			return null;
		return list.get(0).getKhachThue().getMAKT();
	}

	public KhachThueEntity getKhachThue(String makt) {
		Session session = factory.getCurrentSession();
		String hql = "FROM KhachThueEntity kt where kt.MAKT = :makt";
		Query query = session.createQuery(hql);
		query.setParameter("makt", makt);
		List<KhachThueEntity> list = query.list();
		if (list.size() == 0)
			return null;
		return list.get(0);
	}

	@RequestMapping("addCustomer/khachcu/{idPhong}")
	public String khachCuPost(@PathVariable("idPhong") Integer idPhong, HttpServletRequest req, ModelMap model,
			@ModelAttribute("k") KhachThueEntity k) {
		String cccd = req.getParameter("cccdCK");
		String maKT = this.checkKhachTungO(cccd);
		KhachThueEntity kt = this.getKhachThue(maKT);
		if (kt == null) {
			model.addAttribute("k", null);
			return "room/xacnhankhachcu";
		}
		model.addAttribute("k", kt);
		model.addAttribute("maPhong", idPhong);
		return "room/xacnhankhachcu";
	}

	@RequestMapping(value = "addCustomerOld/{id}.htm", method = RequestMethod.POST)
	public RedirectView addCustomerOld(ModelMap model, @ModelAttribute("k") KhachThueEntity k, HttpServletResponse res,
			HttpServletRequest req, @PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		this.kt = k;
		this.idPhong = id;
		KhachThueEntity kt = this.checkNguoiDaiDien(id);
		RedirectView rv = null;

		if (kt == null) {
			rv = new RedirectView("/room/hopdong.htm", true);
		} else {
			CTKhachThueEntity ctktt = this.checkCTKhachThue(k.getMAKT(), this.getHD(id).getMAHOPDONG());
			if (ctktt == null) {
				CTKhachThueEntity ctkt = new CTKhachThueEntity();
				ctkt.setKhachThue(this.kt);
				ctkt.setTRANGTHAI(true);
				ctkt.setHopDong(this.getHD(id));
				Integer temp1;
				temp1 = this.insertCTKT(ctkt);
				if (temp1 != 0) {
					HopDongEntity hopdong = this.getHD(id);
					int sltd = hopdong.getPhong().getLoaiPhong().getSLNGUOITD();
					Long x = checkSoNguoiThue(hopdong.getMAHOPDONG());
					if (sltd == x) {
						this.updateTrangThaiPhong(this.idPhong, 3);
					} else if ((sltd < x) && (x > 0)) {
						this.updateTrangThaiPhong(this.idPhong, 2);
					}

				}
			} else {
				ctktt.setTRANGTHAI(true);
				Session session = factory.openSession();
				Transaction t = session.beginTransaction();
				try {
					session.update(ctktt);
					t.commit();
					HopDongEntity hopdong = this.getHD(id);
					int sltd = hopdong.getPhong().getLoaiPhong().getSLNGUOITD();
					Long x = checkSoNguoiThue(hopdong.getMAHOPDONG());
					if (sltd == x) {
						this.updateTrangThaiPhong(this.idPhong, 3);
					} else if ((sltd < x) && (x > 0)) {
						this.updateTrangThaiPhong(this.idPhong, 2);
					}
					redirectAttributes.addFlashAttribute("message", "Thêm khách vào phòng thành công !");
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
					redirectAttributes.addFlashAttribute("message", "Thêm khách vào phòng thất bại !");
				} finally {
					session.close();
				}
			}

			rv = new RedirectView("/room/desc-hop-dong/" + this.idPhong + ".htm", true);
		}
		return rv;
	}

	// END KHACH TUNG O
	// kiểm tra hóa đơn đã thanh toán hay chưa
	public boolean ktTrangThaiHoaDon(Integer id, String maKTDD) {
		List<HoaDonEntity> dsHoaDon = getHoaDon(id, maKTDD);
		dsHoaDon.forEach(t -> {
			System.out.println(t.getMAHOADON());
		});
		if (dsHoaDon.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean ktTrangThaiHoaDontheoMaHD(Integer id) {
		List<HoaDonEntity> dsHoaDon = getHoaDonTheoMaHD(id);
		dsHoaDon.forEach(t -> {
			System.out.println(t.getMAHOADON());
		});
		if (dsHoaDon.size() > 0) {
			return true;
		}
		return false;
	}

	public List<HoaDonEntity> getHoaDon(Integer id, String maKTDD) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity where hopDong.MAHOPDONG= :MAHOPDONG and TRANGTHAI=0 and hopDong.khachThueDaiDien.MAKT= :MAKTDAIDIEN";
		Query query = session.createQuery(hql);
		query.setParameter("MAHOPDONG", id);
		query.setParameter("MAKTDAIDIEN", maKTDD);
		List<HoaDonEntity> list = (List<HoaDonEntity>) query.list();
		return list;
	}

	public List<HoaDonEntity> getHoaDonTheoMaHD(Integer id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity where hopDong.MAHOPDONG= :MAHOPDONG and TRANGTHAI=0";
		Query query = session.createQuery(hql);
		query.setParameter("MAHOPDONG", id);
		List<HoaDonEntity> list = (List<HoaDonEntity>) query.list();
		return list;
	}

	// CHECK NÓ ĐA Ở PHÒNG ĐÓ CHƯA
	public CTKhachThueEntity checkCTKhachThue(String maKT, Integer mahopdong) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTKhachThueEntity ct where ct.hopDong.MAHOPDONG= :mahopdong and ct.khachThue.MAKT= :maKT and ct.TRANGTHAI = false";
		Query query = session.createQuery(hql);
		query.setParameter("mahopdong", mahopdong);
		query.setParameter("maKT", maKT);
		List<CTKhachThueEntity> list = query.list();
		if (list.size() == 0)
			return null;
		CTKhachThueEntity ctkt = list.get(0);
		return ctkt;
	}
}