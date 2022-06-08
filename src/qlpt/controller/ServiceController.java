package qlpt.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import qlpt.entity.CTDichVuEntity;
import qlpt.entity.DichVuEntity;
import qlpt.entity.HoaDonEntity;
import qlpt.entity.HopDongEntity;
import qlpt.entity.NhaTroEntity;
import qlpt.entity.PhongEntity;
import qlpt.entity.QuyDinhEntity;
import qlpt.entity.ThoiGianEntity;

@Transactional
@Controller
@RequestMapping("service/")
public class ServiceController {
	@Autowired
	SessionFactory factory;

	private String mact;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(ModelMap model, HttpSession ss) {
		mact = ss.getAttribute("mact").toString();
		List<DichVuEntity> services = getServices();
		model.addAttribute("services", services);
		List<NhaTroEntity> nt = getDSNhaTro();
		model.addAttribute("cbNhaTro", nt);
		if (nt.size() > 0) {
			model.addAttribute("MANT", getDSNhaTro().get(0).getMANT());
			model.addAttribute("nhatro", getDSNhaTro().get(0));
		} else {
			model.addAttribute("nhatro", new NhaTroEntity());
		}
		return "service/index";
	}

	@RequestMapping(value = "index", method = RequestMethod.POST)
	public String index1(ModelMap model, @ModelAttribute("nhatro") NhaTroEntity nhaTro) {
		List<DichVuEntity> services = getServices();
		model.addAttribute("cbNhaTro", getDSNhaTro());
		model.addAttribute("services", getServices());
		model.addAttribute("MANT", nhaTro.getMANT());
		return "service/index";
	}

	public List<NhaTroEntity> getDSNhaTro() {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhaTroEntity WHERE chuTro.MACT= :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("MACT", mact);
		List<NhaTroEntity> nhaTros = query.list();
		return nhaTros;
	}

	public NhaTroEntity getNhaTroTheoMa(String MANT) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhaTroEntity where MANT=:MANT AND chuTro.MACT= :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("MANT", MANT);
		query.setParameter("MACT", mact);
		NhaTroEntity nhaTro = (NhaTroEntity) query.list().get(0);
		return nhaTro;
	}

	/* Get Service */
	public List<DichVuEntity> getServices() {
		Session session = factory.getCurrentSession();
		String hql = "FROM DichVuEntity";
		Query query = session.createQuery(hql);
		List<DichVuEntity> services = query.list();
		return services;
	}

	public DichVuEntity getService(Integer maDV) {
		Session session = factory.getCurrentSession();
		String hql = "FROM DichVuEntity WHERE MADV = :MADV";
		Query query = session.createQuery(hql);
		query.setParameter("MADV", maDV);
		DichVuEntity dv = (DichVuEntity) query.list().get(0);
		return dv;
	}

	public QuyDinhEntity getQuyDinh(Integer MADV, String MANT) {
		Session session = factory.getCurrentSession();
		String hql = "FROM QuyDinhEntity where dichVu.MADV = :MADV AND nhaTro.MANT= :MANT AND nhaTro.chuTro.MACT= :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("MADV", MADV);
		query.setParameter("MANT", MANT);
		query.setParameter("MACT", mact);
		QuyDinhEntity dv = (QuyDinhEntity) query.list().get(0);
		return dv;
	}

	public List<QuyDinhEntity> getDsQuyDinh() {
		Session session = factory.getCurrentSession();
		String hql = "FROM QuyDinhEntity where nhaTro.chuTro.MACT= :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("MACT", mact);
		List<QuyDinhEntity> services = query.list();
		return services;
	}

	/*
	 * public List<QuyDinhEntity> getDsQuyDinhTheoThangNam(int maPhong,int THANG,
	 * int NAM) { HopDongEntity hopDong=getHopDongTheoMaPhong(maPhong);
	 * List<CTDichVuEntity> dsCTDV=(List<CTDichVuEntity>)
	 * getCTDVTheoMaHD_TG(hopDong.getMAHOPDONG(), THANG, NAM); List<QuyDinhEntity>
	 * dsQD=getDsQuyDinh(); for(QuyDinhEntity q:dsQD) { for(CTDichVuEntity d:dsCTDV)
	 * { if(q.getDichVu().getMADV()==d.getDichVu().getMADV() && q.get } } }
	 */

	/* Begin Add Service */
	@RequestMapping("create")
	public String create(ModelMap model, @ModelAttribute("service") DichVuEntity service) {
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("cbNhaTro", getDSNhaTro());
		model.addAttribute("cbDichVu", getServices());
		model.addAttribute("abc", getDsQuyDinh());
		model.addAttribute("title", "Thêm dịch vụ");
		return "service/create";
	}

	@RequestMapping(value = "create", params = "btnAdd", method = RequestMethod.POST)
	public String addService(@ModelAttribute("service") DichVuEntity service, ModelMap model,
			HttpServletRequest resquest) {
		String maDVStr = resquest.getParameter("MADV1");
		String maNT = resquest.getParameter("MANT");
		if (resquest.getParameter("dongia").equals("")) {
			model.addAttribute("lbTBDonGiaNull", "Đơn giá không được bỏ trống!");
			model.addAttribute("btnStatus", "btnAdd");
			model.addAttribute("cbNhaTro", getDSNhaTro());
			model.addAttribute("cbDichVu", getServices());
			model.addAttribute("abc", getDsQuyDinh());
			model.addAttribute("title", "Thêm dịch vụ");
			return "service/create";
		}
		Double donGia = Double.parseDouble(resquest.getParameter("dongia"));
		String mota = resquest.getParameter("mota");
		LocalDateTime now = LocalDateTime.now();
		Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
		Date date = Date.from(instant);

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {

			Integer maDVInt;
			if (!maDVStr.equals("isSelected")) {
				maDVInt = Integer.parseInt(maDVStr);
				DichVuEntity sv = getService(maDVInt);
				boolean kt = ktDV_THUOC_NT(sv.getMADV(), maNT);
				if (kt) {
					model.addAttribute("lbTBTenDVTrung", "Tên dịch vụ đã tồn tại trong nhà trọ!");
					model.addAttribute("btnStatus", "btnAdd");
					model.addAttribute("cbNhaTro", getDSNhaTro());
					model.addAttribute("cbDichVu", getServices());
					model.addAttribute("abc", getDsQuyDinh());
					model.addAttribute("title", "Thêm dịch vụ");
					return "service/create";
				} else {
					QuyDinhEntity qd = new QuyDinhEntity(getNhaTroTheoMa(maNT), sv, donGia, mota, date);
					session.save(qd);
				}
			} else {
				if (service.getTENDV().equals("")) {
					model.addAttribute("lbTBTenDVTrung", "Tên dịch vụ không được bỏ trống!");
					model.addAttribute("btnStatus", "btnAdd");
					model.addAttribute("cbNhaTro", getDSNhaTro());
					model.addAttribute("cbDichVu", getServices());
					model.addAttribute("abc", getDsQuyDinh());
					model.addAttribute("title", "Thêm dịch vụ");
					return "service/create";
				}
				boolean kt = ktTenDV(service.getTENDV());
				if (kt) {
					model.addAttribute("lbTBTenDVTrung", "Tên dịch vụ đã tồn tại!");
					model.addAttribute("btnStatus", "btnAdd");
					model.addAttribute("cbNhaTro", getDSNhaTro());
					model.addAttribute("cbDichVu", getServices());
					model.addAttribute("abc", getDsQuyDinh());
					model.addAttribute("title", "Thêm dịch vụ");
					return "service/create";
				} else {
					QuyDinhEntity qd = new QuyDinhEntity(getNhaTroTheoMa(maNT), service, donGia, mota, date);
					service.setTENDV(service.getTENDV().toUpperCase());
					session.save(service);
					session.save(qd);
				}
			}
			t.commit();
			model.addAttribute("message", "Thêm thành công!");
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Thêm thất bại!");
		} finally {
			session.close();
		}
		model.addAttribute("services", getServices());
		model.addAttribute("cbNhaTro", getDSNhaTro());
		model.addAttribute("nhatro", getNhaTroTheoMa(maNT));
		model.addAttribute("MANT", maNT);
		return "service/index";
	}

	/* Update Service */
	@RequestMapping(value = "create/{MADV}", params = "linkEdit")
	public String editService(ModelMap model, @ModelAttribute("service") DichVuEntity service,
			@PathVariable("MADV") Integer MADV, HttpServletRequest request) {
		model.addAttribute("cbNhaTro", getDSNhaTro());
		model.addAttribute("service", getService(MADV));
		model.addAttribute("btnStatus", "btnUpdate");
		model.addAttribute("title", "Sửa dịch vụ");
		model.addAttribute("type", 1);
		model.addAttribute("dongia", request.getParameter("dongia"));
		model.addAttribute("mota", request.getParameter("mota"));
		model.addAttribute("MANT", request.getParameter("MANT"));
		return "service/create";
	}

	@RequestMapping(value = "create", params = "btnUpdate")
	public String upDateService(ModelMap model, @ModelAttribute("service") DichVuEntity service,
			HttpServletRequest request) {
		String maNT = request.getParameter("MANT");
		String mota = request.getParameter("mota");
		if (request.getParameter("dongia").equals("")) {
			model.addAttribute("lbTBDonGiaNull", "Đơn giá không được bỏ trống!");
			model.addAttribute("cbNhaTro", getDSNhaTro());
			model.addAttribute("service", getService(service.getMADV()));
			model.addAttribute("btnStatus", "btnUpdate");
			model.addAttribute("title", "Sửa dịch vụ");
			model.addAttribute("type", 1);
			model.addAttribute("dongia", request.getParameter("dongia"));
			model.addAttribute("mota", request.getParameter("mota"));
			model.addAttribute("MANT", request.getParameter("MANT"));
			return "service/create";
		}
		Double donGia = Double.parseDouble(request.getParameter("dongia"));
		LocalDateTime now = LocalDateTime.now();
		Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
		Date date = Date.from(instant);
		QuyDinhEntity qd = new QuyDinhEntity(getNhaTroTheoMa(maNT), service, donGia, mota, date);
		Integer t2 = this.updateQuyDinh(qd);
		if (t2 != 0) {
			model.addAttribute("message", "Sửa thành công!");
		} else {
			model.addAttribute("message", "Sửa thất bại!");
		}
		model.addAttribute("services", getServices());
		model.addAttribute("cbNhaTro", getDSNhaTro());
		model.addAttribute("nhatro", new NhaTroEntity());
		return "service/index";
	}

	public int updateDichVu(DichVuEntity dv) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(dv);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public int updateQuyDinh(QuyDinhEntity qd) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(qd);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public boolean ktDichVuCoTonTaiTrongCTDV(int maDV, String maNT) {
		List<CTDichVuEntity> dsct = (List<CTDichVuEntity>) getDSCTDVTheoMaDV(maDV);
		for (CTDichVuEntity c : dsct) {
			if (c.getDichVu().getMADV() == maDV && c.getHopDong().getPhong().getNhatro().getMANT().equals(maNT)) {
				return true;
			}
		}
		return false;
	}

	/* Delete Service */
	@RequestMapping(value = "index/{MADV}", params = "linkDelete")
	public String deleteService(ModelMap model, @PathVariable("MADV") Integer MADV, HttpServletRequest request) {
		String MANT = request.getParameter("MANT");
		if (ktDichVuCoTonTaiTrongCTDV(MADV, MANT)) {
			model.addAttribute("message",
					"Dịch vụ " + getService(MADV).getTENDV() + " đang được sử dụng, không thể xóa!");
		} else {

			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			QuyDinhEntity q = getQuyDinh(MADV, MANT);
			try {
				session.delete(getQuyDinh(MADV, MANT));
				t.commit();
				model.addAttribute("message", "Xóa thành công!");
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message", "Xóa thất bại!");
			} finally {
				session.close();
			}
		}
		model.addAttribute("services", getServices());
		model.addAttribute("cbNhaTro", getDSNhaTro());
		model.addAttribute("nhatro", new NhaTroEntity());
		return "service/index";
	}

	/* Search Service */
	public List<DichVuEntity> searchServices(String TENDV) {
		Session session = factory.openSession();
		String hql = "FROM DichVuEntity where TENDV LIKE :TENDV";
		Query query = session.createQuery(hql);
		query.setParameter("TENDV", "%" + TENDV + "%");
		List<DichVuEntity> list = query.list();
		return list;
	}

	@RequestMapping(value = "index", params = "btnSearch")
	public String searchServices(ModelMap model, HttpServletRequest request,
			@ModelAttribute("nhatro") NhaTroEntity nhaTro) {
		model.addAttribute("services", searchServices(request.getParameter("searchInput")));
		model.addAttribute("cbNhaTro", getDSNhaTro());
		model.addAttribute("MANT", nhaTro.getMANT());
		return "service/index";
	}

	public boolean ktTenDV(String tenDV) {
		List<DichVuEntity> dsDichVu = getServices();
		for (DichVuEntity d : dsDichVu) {
			if (d.getTENDV().toLowerCase().equals(tenDV.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public boolean ktDV_THUOC_NT(int maDV, String maNT) {
		List<QuyDinhEntity> dsQuyDinh = getDsQuyDinh();
		for (QuyDinhEntity d : dsQuyDinh) {
			if (d.getDichVu().getMADV() == maDV && d.getNhaTro().getMANT().equals(maNT)) {
				return true;
			}
		}
		return false;
	}

	public PhongEntity getPhongTheoMaPhong(Integer maPhong) {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhongEntity WHERE MAPHONG = :MAPHONG";
		Query query = session.createQuery(hql);
		query.setParameter("MAPHONG", maPhong);
		PhongEntity dv = (PhongEntity) query.list().get(0);
		return dv;
	}

	public HopDongEntity getHopDongTheoMaPhong(Integer maPhong) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDongEntity WHERE DAHUY=0 and phong.MAPHONG = :MAPHONG";
		Query query = session.createQuery(hql);
		query.setParameter("MAPHONG", maPhong);
		if (query.list().size() > 0) {
			return (HopDongEntity) query.list().get(0);
		}
		return null;
	}

	public List<String> getMANT() {
		Session session = factory.getCurrentSession();
		String hql = "SELECT MANT FROM NhaTroEntity WHERE MACT = :mact";
		Query query = session.createQuery(hql);
		query.setParameter("mact", mact);
		List<String> list = query.list();
		return list;
	}

	public List<PhongEntity> getRooms() {
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

	public List<ThoiGianEntity> getDsThoiGian() {
		Session session = factory.getCurrentSession();
		String hql = "FROM ThoiGianEntity";
		Query query = session.createQuery(hql);
		List<ThoiGianEntity> dv = query.list();
		return dv;
	}

	public CTDichVuEntity getCTDVTheoMaHD_DV_TG(Integer MAHOPDONG, Integer MADV, Integer MATG) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTDichVuEntity where MAHOPDONG = :MAHOPDONG and MADV= :MADV and MATG= :MATG";
		Query query = session.createQuery(hql);
		query.setParameter("MAHOPDONG", MAHOPDONG);
		query.setParameter("MADV", MADV);
		query.setParameter("MATG", MATG);
		CTDichVuEntity dv = new CTDichVuEntity();
		if (query.list().size() == 0) {
			return null;
		} else {
			dv = (CTDichVuEntity) query.list().get(0);
		}
		return dv;
	}

	public List<CTDichVuEntity> getCTDVTheoMaHD_TG(Integer MAHOPDONG, int THANG, int NAM) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTDichVuEntity where MAHOPDONG = :MAHOPDONG and thoiGian.THANG= :THANG and thoiGian.NAM= :NAM";
		Query query = session.createQuery(hql);
		query.setParameter("MAHOPDONG", MAHOPDONG);
		query.setParameter("THANG", THANG);
		query.setParameter("NAM", NAM);
		return query.list();
	}

	public List<CTDichVuEntity> getDSCTDVTheoMaDV(Integer MADV) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTDichVuEntity where MADV= :MADV";
		Query query = session.createQuery(hql);
		query.setParameter("MADV", MADV);
		return query.list();
	}

	public ThoiGianEntity getThoiGianTheoThangNam(int THANG, int NAM) {
		List<ThoiGianEntity> dsTG = getDsThoiGian();
		ThoiGianEntity tg = new ThoiGianEntity();
		for (ThoiGianEntity t : dsTG) {
			if (t.getTHANG() == THANG && t.getNAM() == NAM) {
				tg = t;
			}
		}
		return tg;
	}

	public Integer themThoiGian(int THANG, int NAM) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		ThoiGianEntity tg = new ThoiGianEntity(THANG, NAM);
		try {
			session.save(tg);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	@RequestMapping("addService/{id}.htm")
	public String addService(HttpServletRequest request, @PathVariable("id") int id, ModelMap model,
			@ModelAttribute("room") PhongEntity room, HttpSession ss) {
		HopDongEntity h = getHopDongTheoMaPhong(id);
		if (h == null) {
			mact = ss.getAttribute("mact").toString();
			List<PhongEntity> rooms = this.getRooms();
			model.addAttribute("rooms", rooms);
			model.addAttribute("formHide", null);
			model.addAttribute("message", "Vui lòng tạo hợp đồng trước khi thêm dịch vụ!");
			return "room/index";
		}

		String maNT = getPhongTheoMaPhong(id).getNhatro().getMANT();

		List<QuyDinhEntity> dsQD = getDsQuyDinhTheoMaNT(maNT);
		if (dsQD.size() <= 0) {
			mact = ss.getAttribute("mact").toString();
			List<PhongEntity> rooms = this.getRooms();
			model.addAttribute("rooms", rooms);
			model.addAttribute("formHide", null);
			model.addAttribute("message", "Vui lòng thêm dịch vụ cho nhà trọ trước khi thêm cho phòng!");
			return "room/index";
		}
		int THANG = 0;
		int NAM = 0;
		String dateStr = "";
		if (request.getParameter("THANG") != null) {
			try {
				THANG = Integer.parseInt(request.getParameter("THANG"));
				NAM = Integer.parseInt(request.getParameter("NAM"));
				
			} catch (Exception e) {
				request.setAttribute("message", "Thời gian không hợp lệ (dịch vụ chỉ được thêm kể từ ngày ký hợp đồng)!");
				request.setAttribute("maPhong", id);
				return "service/addServiceForRoom";
			}
			if (THANG < 10) {
				dateStr += "0" + THANG + "/" + NAM;
			} else {
				dateStr = THANG + "/" + NAM;
			}
		} else {
			LocalDate now = LocalDate.now();
			dateStr=now.format(DateTimeFormatter.ofPattern("MM/yyyy"));
			THANG = now.getMonthValue();
			NAM = now.getYear();
		}
		Date d = new java.sql.Date(NAM-1900, THANG, 1);
		if (d.compareTo(h.getNGAYKY()) < 0) {
			request.setAttribute("message", "Thời gian không hợp lệ (dịch vụ chỉ được thêm kể từ ngày ký hợp đồng)!");
			request.setAttribute("maPhong", id);
			return "service/addServiceForRoom";
		}
		ThoiGianEntity t = getThoiGianTheoThangNam(THANG, NAM);
		if (t.getMATG() == 0) {
			this.themThoiGian(THANG, NAM);
		}
		request.setAttribute("date", dateStr);
		request.setAttribute("THANG", THANG);
		request.setAttribute("NAM", NAM);
		request.setAttribute("dsQuyDinh", dsQD);
		request.setAttribute("maPhong", id);
		return "service/addServiceForRoom";
	}

	@RequestMapping(value = "addService")
	public String getDSDichVu(HttpServletRequest request) {
		String dateStr = request.getParameter("date");
		String maPhong = request.getParameter("maPhong");
		int THANG=0;
		int NAM=0;
		try {
			THANG = Integer.parseInt(dateStr.substring(0, dateStr.indexOf("/")));
			NAM = Integer.parseInt(dateStr.substring(dateStr.indexOf("/") + 1));
		} catch (Exception e) {
			request.setAttribute("message", "Thời gian không hợp lệ (dịch vụ chỉ được thêm kể từ ngày ký hợp đồng)!");
			request.setAttribute("maPhong", maPhong);
			return "service/addServiceForRoom";
		}
		HopDongEntity h=getHopDongTheoMaPhong(Integer.parseInt(maPhong));
		Date d = new java.sql.Date(NAM-1900, THANG, 1);
		if (d.compareTo(h.getNGAYKY()) < 0) {
			request.setAttribute("maPhong", maPhong);
			return "service/addServiceForRoom";
		}
		String maNT = getPhongTheoMaPhong(Integer.parseInt(maPhong)).getNhatro().getMANT();

		request.setAttribute("date", dateStr);
		List<QuyDinhEntity> dsQD = getDsQuyDinhTheoMaNT(maNT);
		request.setAttribute("THANG", THANG);
		request.setAttribute("NAM", NAM);
		request.setAttribute("dsQuyDinh", dsQD);
		request.setAttribute("maPhong", maPhong);
		return "service/addServiceForRoom";

	}

	// sua lai RedirectView
	@RequestMapping(value = "addService/{id}.htm", method = RequestMethod.POST)
	public RedirectView saveCTDVChoPhong(HttpServletRequest request, @PathVariable("id") int id,
			HttpServletResponse res, RedirectAttributes ra) throws IOException {
		String check = request.getParameter("check");
		String maNT = getPhongTheoMaPhong(id).getNhatro().getMANT();
		String soLuongStr = request.getParameter("soLuong");
		int soLuong = 0;
		int THANG=0;
		int NAM=0;
		String dateStr = "";
		try {
			THANG = Integer.parseInt(request.getParameter("THANG"));
			NAM = Integer.parseInt(request.getParameter("NAM"));
			
		} catch (Exception e) {
			ra.addFlashAttribute("message", "Thời gian không hợp lệ (dịch vụ chỉ được thêm kể từ ngày ký hợp đồng)");
			RedirectView rv = new RedirectView("/service/addService/"+id+".htm", true);
			return rv;
		}
		if (THANG < 10) {
			dateStr += "0" + THANG + "/" + NAM;
		} else {
			dateStr = THANG + "/" + NAM;
		}
		ThoiGianEntity t = getThoiGianTheoThangNam(THANG, NAM);
		String message = "";
		if (check != null) {
			if (soLuongStr.equals("")) {
				ra.addFlashAttribute("message", "Vui lòng nhập số lượng");
				ra.addFlashAttribute("date", dateStr);
				ra.addFlashAttribute("THANG", THANG);
				ra.addFlashAttribute("NAM", NAM);
				RedirectView rv = new RedirectView("/service/addService/"+id+".htm", true);
				return rv;
			} else {
				try {
					soLuong = Integer.parseInt(soLuongStr);
					if (soLuong <= 0) {
						ra.addFlashAttribute("message", "Số lượng phải lớn hơn 0");
						ra.addFlashAttribute("date", dateStr);
						ra.addFlashAttribute("THANG", THANG);
						ra.addFlashAttribute("NAM", NAM);
						RedirectView rv = new RedirectView("/service/addService/"+id+".htm", true);
						return rv;
					}
				} catch (Exception e) {
					ra.addFlashAttribute("message", "Số lượng không hợp lệ");
					ra.addFlashAttribute("date", dateStr);
					ra.addFlashAttribute("THANG", THANG);
					ra.addFlashAttribute("NAM", NAM);
					RedirectView rv = new RedirectView("/service/addService/"+id+".htm", true);
					return rv;
				}
			}

		}

		int maDV = Integer.parseInt(request.getParameter("maDV"));
		HopDongEntity h = getHopDongTheoMaPhong(id);
		CTDichVuEntity c = getCTDVTheoMaHD_DV_TG(h.getMAHOPDONG(), maDV, getThoiGianTheoThangNam(THANG, NAM).getMATG());
		if (check != null && check.equals("checked")) {
			if (c != null) {
				Integer i = this.updateCTDV(c, 0, soLuong);
				if (i != 0) {
					ra.addFlashAttribute("message", "Lưu thành công!");
				} else {
					ra.addFlashAttribute("message", "Lưu thất bại!");
				}
			} else {
				c = new CTDichVuEntity(getService(maDV), h, getThoiGianTheoThangNam(THANG, NAM), 0, soLuong);
				Integer i = this.themCTDV(c);
				if (i != 0) {
					ra.addFlashAttribute("message", "Lưu thành công!");
				} else {
					ra.addFlashAttribute("message", "Lưu thất bại!");
				}
			}
		} else if (check == null && c != null) {
			Integer i = this.xoaCTDV(c);
			if (i != 0) {
				ra.addFlashAttribute("message", "Lưu thành công!");
			} else {
				ra.addFlashAttribute("message", "Lưu thất bại!");
			}
		}
		ra.addFlashAttribute("date", dateStr);
		ra.addFlashAttribute("THANG", THANG);
		ra.addFlashAttribute("NAM", NAM);
		RedirectView rv = new RedirectView("/service/addService/"+id+".htm", true);   
		return rv;
	}

	public Integer themCTDV(CTDichVuEntity ctdv) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(ctdv);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public Integer xoaCTDV(CTDichVuEntity ctdv) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.delete(ctdv);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public Integer updateCTDV(CTDichVuEntity ctdv, int csc, int csm) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery("update CTDichVuEntity " + "set CHISOCU= " + csc + ", CHISOMOI= " + csm
					+ "  where hopDong.MAHOPDONG= " + ctdv.getHopDong().getMAHOPDONG() + " and thoiGian.MATG= "
					+ ctdv.getThoiGian().getMATG() + " and dichVu.MADV= " + ctdv.getDichVu().getMADV());
			int update = query.executeUpdate();
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public List<QuyDinhEntity> getDsQuyDinhTheoMaNT(String maNT) {
		Session session = factory.getCurrentSession();
		String hql = "FROM QuyDinhEntity where nhaTro.MANT= :MANT";
		QuyDinhEntity q = new QuyDinhEntity();
		Query query = session.createQuery(hql);
		query.setParameter("MANT", maNT);
		List<QuyDinhEntity> services = query.list();
		return services;
	}

}
