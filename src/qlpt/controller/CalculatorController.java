package qlpt.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import qlpt.entity.BaoTriEntity;
import qlpt.entity.CTDichVuEntity;
import qlpt.entity.DichVuEntity;
import qlpt.entity.HoaDonEntity;
import qlpt.entity.HopDongEntity;
import qlpt.entity.KhachThueEntity;
import qlpt.entity.NhaTroEntity;
import qlpt.entity.PhongEntity;
import qlpt.entity.QuyDinhEntity;
import qlpt.entity.ThoiGianEntity;
import qlpt.entity.TrangThaiEntity;

@Transactional
@Controller
@RequestMapping("calculator/")
public class CalculatorController {
	@Autowired
	SessionFactory factory;
	private String mact;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(ModelMap model, @ModelAttribute("CTDichVu") CTDichVuEntity ctDIchVu, HttpServletRequest request,
			HttpSession ss) {
		mact = ss.getAttribute("mact").toString();
		LocalDate now = LocalDate.now();
		int THANG = now.getMonthValue();
		int NAM = now.getYear();
		ThoiGianEntity t = getThoiGianTheoThangNam(THANG, NAM);
		if (t.getMATG() == 0) {
			this.themThoiGian(THANG, NAM);
		}

		model.addAttribute("date", THANG + "/" + NAM);
		model.addAttribute("dsCTDichVu", getCTDVTheoTG(THANG, NAM));
		model.addAttribute("dsNhaTro", getDSNhaTro());
		model.addAttribute("dsHoaDon", getDsHoaDon());
		return "calculator/index";
	}

	@RequestMapping(value = "index", params = "btnXem")
	public String index1(ModelMap model, HttpServletRequest request, @ModelAttribute("HopDong") HopDongEntity hopdong)
			throws ParseException {

		String dateStr = request.getParameter("date");// 05/2022
		String maNT = request.getParameter("nhaTro");
		int THANG = Integer.parseInt(dateStr.substring(0, dateStr.indexOf("/")));
		int NAM = Integer.parseInt(dateStr.substring(dateStr.indexOf("/") + 1));
		String s = "28/" + dateStr;
		LocalDateTime now = LocalDateTime.now();
		Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
		Date dates = Date.from(instant);
		SimpleDateFormat ngay = new SimpleDateFormat("dd/MM/yyyy");
		Date ngayxuat = ngay.parse(s);

		model.addAttribute("nhatroselect", maNT);
		model.addAttribute("date", dateStr);

		Date d = new java.sql.Date(NAM - 1900, THANG, 1);
		if (d.compareTo(dates) > 0) {
			model.addAttribute("Messsage", "Chưa đến thời gian xuất hóa đơn");
			model.addAttribute("dsNhaTro", getDSNhaTro());
			return "calculator/index";
		} else {
			HashMap<Integer, Double> dsTienDichVu = getHopDongTienDV(THANG, NAM);
			List<HoaDonEntity> dsHoaDon = new ArrayList<HoaDonEntity>();
			List<HopDongEntity> dsHopDong = getDsHopDong();

			dsTienDichVu.forEach((t, u) -> {
				HoaDonEntity h = new HoaDonEntity(ngayxuat, 0.0, Double.parseDouble(dsTienDichVu.get(t).toString()),
						false, getHopDongTheoMa(t));
				if(d.compareTo(h.getHopDong().getNGAYKY())>0)
				{
					h.setTHANHTIEN(h.getTienNha()+h.getTONGPHUTHU());
					dsHoaDon.add(h);
					if (checkTG(ngayxuat, h.getHopDong().getMAHOPDONG()) == 1) {
						this.themHoaDon1(h);
					}
					else
					{
						this.updateHoaDon(h.getHopDong().getMAHOPDONG(), h.getNGAYLAP(), h.getTHANHTIEN(), h.getTONGPHUTHU());
					}
				}
				
			});
			model.addAttribute("dsNhaTro", getDSNhaTro());
			model.addAttribute("dsHoaDon", getDsHoaDontheoThangNam(ngayxuat));
			return "calculator/index";
		}

	}

	public int updateHoaDon(int mahopdong, Date ngaylap, Double thanhtien, Double tongphuthu) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			String hql = "UPDATE HoaDonEntity SET THANHTIEN = :thanhtien, TONGPHUTHU = :tongphuthu WHERE hopDong.MAHOPDONG = :mahopdong and NGAYLAP = :ngaylap";
			Query query = session.createQuery(hql);
			query.setParameter("thanhtien", thanhtien);
			query.setParameter("ngaylap", ngaylap);
			query.setParameter("tongphuthu", tongphuthu);
			query.setParameter("mahopdong", mahopdong);
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

	public int checkTG(Date NGAYLAP, int HOPDONG) {
		List<HoaDonEntity> dshd = getDsHoaDon();
		for (HoaDonEntity h : dshd) {
			if (h.getNGAYLAP().compareTo(NGAYLAP) == 0 && h.getHopDong().getMAHOPDONG() == HOPDONG) {
				return 0;
			}
		}
		return 1;

	}

	public List<HoaDonEntity> getDsHoaDonTheoThang(Date NGAYLAP, int MAHOPDONG) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity WHERE NGAYLAP = :NGAYLAP and hopDong.MAHOPDONG = :MAHOPDONG";
		Query query = session.createQuery(hql);
		query.setParameter("NGAYLAP", NGAYLAP);
		query.setParameter("MAHOPDONG", MAHOPDONG);
		List<HoaDonEntity> dsHD = query.list();
		return dsHD;
	}

	public List<HoaDonEntity> getDsHoaDon() {
		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity";
		Query query = session.createQuery(hql);
		List<HoaDonEntity> dsHoaDon = query.list();
		return dsHoaDon;
	}

	public HoaDonEntity getHoaDon(int MAHOADON) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity WHERE MAHOADON = :MAHOADON";
		Query query = session.createQuery(hql);
		query.setParameter("MAHOADON", MAHOADON);
		return (HoaDonEntity) query.list().get(0);
	}

	public List<HoaDonEntity> getDsHoaDontheoThangNam(Date NGAYLAP) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity WHERE NGAYLAP= :NGAYLAP " + "and hopDong.DAHUY = 0";
		Query query = session.createQuery(hql);
		query.setParameter("NGAYLAP", NGAYLAP);
		List<HoaDonEntity> dsHoaDon = query.list();
		return dsHoaDon;
	}

	public HashMap<Integer, Double> getHopDongTienDV(int THANG, int NAM) {
		List<HopDongEntity> dsHopDong = getDsHopDong();
		List<CTDichVuEntity> dsCTDichVu = getCTDVTheoTG(THANG, NAM);
		HashMap<Integer, Double> map = new HashMap<Integer, Double>();
		for (HopDongEntity h : dsHopDong) {
			double tongTienDV = 0;
			for (CTDichVuEntity ctdv : dsCTDichVu) {
				if (h.getMAHOPDONG() == ctdv.getHopDong().getMAHOPDONG()) {
					double gia = getGiaTheoMaDV(h.getPhong().getNhatro().getMANT(), ctdv.getDichVu().getMADV())
							.getDONGIA();
					double tienDV = (ctdv.getCHISOMOI() - ctdv.getCHISOCU()) * gia;
					tongTienDV += tienDV;
				}
			}
			map.put(h.getMAHOPDONG(), tongTienDV);
		}
		return map;
	}

	public QuyDinhEntity getGiaTheoMaDV(String MANT, int MADV) {
		Session session = factory.getCurrentSession();
		String hql = "FROM QuyDinhEntity WHERE nhaTro.MANT= :MANT and dichVu.MADV= :MADV";
		Query query = session.createQuery(hql);
		query.setParameter("MANT", MANT);
		query.setParameter("MADV", MADV);
		QuyDinhEntity qd = (QuyDinhEntity) query.list().get(0);
		return qd;
	}

	public List<HopDongEntity> getDsHopDong() {
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDongEntity where DAHUY = 0";
		Query query = session.createQuery(hql);
		List<HopDongEntity> ds = query.list();
		return ds;
	}

	public List<CTDichVuEntity> getCTDichVu() {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTDichVuEntity";
		Query query = session.createQuery(hql);
		List<CTDichVuEntity> dsDichVu = query.list();
		return dsDichVu;
	}

	public List<CTDichVuEntity> getCTDVTheoTG(int THANG, int NAM) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTDichVuEntity WHERE thoiGian.THANG= :THANG and thoiGian.NAM= :NAM "
				+ "and hopDong.DAHUY = 0";
		Query query = session.createQuery(hql);
		query.setParameter("THANG", THANG);
		query.setParameter("NAM", NAM);
		List<CTDichVuEntity> dsDichVu = query.list();
		return dsDichVu;
	}

	public List<CTDichVuEntity> getCTDVTheoTG_MANT(int THANG, int NAM, String MANT) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTDichVuEntity WHERE thoiGian.THANG= :THANG and thoiGian.NAM= :NAM "
				+ "and hopDong.DAHUY = 0 and hopDong.phong.nhatro.MANT= :MANT";
		Query query = session.createQuery(hql);
		query.setParameter("THANG", THANG);
		query.setParameter("NAM", NAM);
		query.setParameter("MANT", MANT);
		List<CTDichVuEntity> dsDichVu = query.list();
		return dsDichVu;
	}

	public List<CTDichVuEntity> getCTDVTheoTG_MANT_MATT(int THANG, int NAM, String MANT, int MATT) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTDichVuEntity WHERE thoiGian.THANG= :THANG and thoiGian.NAM= :NAM "
				+ "and hopDong.DAHUY = 0 and hopDong.phong.nhatro.MANT= :MANT "
				+ "and hopDong.phong.trangThai.MATT= :MATT";
		Query query = session.createQuery(hql);
		query.setParameter("THANG", THANG);
		query.setParameter("NAM", NAM);
		query.setParameter("MANT", MANT);
		query.setParameter("MATT", MATT);
		List<CTDichVuEntity> dsDichVu = query.list();
		return dsDichVu;
	}

	public List<HopDongEntity> getHopDongMANT(String MANT) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDongEntity WHERE DAHUY = 0 and phong.nhatro.MANT= :MANT";
		Query query = session.createQuery(hql);
		query.setParameter("MANT", MANT);
		List<HopDongEntity> dsHopDong = query.list();
		return dsHopDong;
	}

	public List<PhongEntity> getDsPhong() {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhongEntity";
		Query query = session.createQuery(hql);
		List<PhongEntity> dsPhong = query.list();
		return dsPhong;
	}

	public List<KhachThueEntity> getDsKhachThue() {
		Session session = factory.getCurrentSession();
		String hql = "FROM KhachThueEntity";
		Query query = session.createQuery(hql);
		List<KhachThueEntity> dsKhachThue = query.list();
		return dsKhachThue;
	}

	public List<NhaTroEntity> getDSNhaTro() {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhaTroEntity WHERE chuTro.MACT= :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("MACT", mact);
		List<NhaTroEntity> dsNhaTro = query.list();
		return dsNhaTro;
	}

	// ==================================================

	// ==================================

	public ThoiGianEntity getThoiGianTheoMa(int MATG) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ThoiGianEntity where MATG = :MATG";
		Query query = session.createQuery(hql);
		query.setParameter("MATG", MATG);
		ThoiGianEntity dv = (ThoiGianEntity) query.list().get(0);
		return dv;
	}

	public List<ThoiGianEntity> getDsThoiGian() {
		Session session = factory.getCurrentSession();
		String hql = "FROM ThoiGianEntity";
		Query query = session.createQuery(hql);
		List<ThoiGianEntity> dv = query.list();
		return dv;
	}

	public HopDongEntity getHopDongTheoMa(int MAHOPDONG) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDongEntity where MAHOPDONG = :MAHOPDONG";
		Query query = session.createQuery(hql);
		query.setParameter("MAHOPDONG", MAHOPDONG);
		HopDongEntity dv = (HopDongEntity) query.list().get(0);
		return dv;
	}

	public CTDichVuEntity getCTDVTheoMa(Integer MAHOPDONG, Integer MADV, Integer MATG) {
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

	public Integer themHoaDon1(HoaDonEntity hd) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(hd);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public Integer themHoaDon(List<HoaDonEntity> dshd) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			for (HoaDonEntity c : dshd) {
				session.save(c);
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public int updateHoaDon(HoaDonEntity hd) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(hd);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	///// Đóng tiền
	@RequestMapping(value = "index/{MAHOADON}", params = "linkPayment")
	public String payment(ModelMap model, @ModelAttribute("hoaDon") HoaDonEntity hoadon,
			@PathVariable("MAHOADON") Integer MAHOADON) {
		if (updateTrangThaiHoaDOn(true, MAHOADON) == 1) {
			model.addAttribute("title", "Đóng tiền thành công");
		} else {
			model.addAttribute("title", "Khách đã đóng tiền");
		}
		model.addAttribute("hoadon", getDsHoaDon());
		model.addAttribute("dsNhaTro", getDSNhaTro());
		return "calculator/index";
	}

	public Integer updateTrangThaiHoaDOn(boolean trangThai, int maHoaDon) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery(
					"update HoaDonEntity " + "set TRANGTHAI= " + trangThai + "  where MAHOADON= " + maHoaDon);
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

	//// In hóa đơn
	@RequestMapping(value = "bill/{MAHOADON}", params = "linkBill")
	public String bill(ModelMap model, @ModelAttribute("hoaDon") HoaDonEntity hoadon,
			@PathVariable("MAHOADON") Integer MAHOADON, HttpServletRequest request) {

		Date datetemp = getHoaDon(MAHOADON).getNGAYLAP();

		int THANG = datetemp.getMonth() + 1;
		int NAM = datetemp.getYear() + 1900;
		System.out.print(THANG + "/t" + NAM);
		model.addAttribute("NAM", NAM);
		model.addAttribute("THANG", THANG);
		model.addAttribute("hoadon", getDsHoaDon());
		model.addAttribute("dsNhaTro", getDSNhaTro());
		model.addAttribute("inhoadon", getHoaDon(MAHOADON));
		List<CTDichVuEntity> ctdv = this.getCTDVTheoTG_MAHD(THANG, NAM,
				getHoaDon(MAHOADON).getHopDong().getMAHOPDONG());
		model.addAttribute("dichvu", ctdv);
		return "calculator/bill";
	}

	public List<CTDichVuEntity> getCTDVTheoTG_MAHD(int THANG, int NAM, int MAHOPDONG) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTDichVuEntity WHERE thoiGian.THANG= :THANG and thoiGian.NAM= :NAM "
				+ "and hopDong.DAHUY = 0 and hopDong.MAHOPDONG= :MAHOPDONG";
		Query query = session.createQuery(hql);
		query.setParameter("THANG", THANG);
		query.setParameter("NAM", NAM);
		query.setParameter("MAHOPDONG", MAHOPDONG);
		List<CTDichVuEntity> dsDichVu = query.list();
		return dsDichVu;
	}
}
