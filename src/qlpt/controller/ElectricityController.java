package qlpt.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.servlet.ServletException;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import qlpt.entity.CTDichVuEntity;
import qlpt.entity.DichVuEntity;
import qlpt.entity.HopDongEntity;
import qlpt.entity.KhachThueEntity;
import qlpt.entity.NhaTroEntity;
import qlpt.entity.PhongEntity;
import qlpt.entity.QuyDinhEntity;
import qlpt.entity.ThoiGianEntity;
import qlpt.entity.TrangThaiEntity;

@Transactional
@Controller
@RequestMapping("electricity/")
public class ElectricityController {
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
		ktThoiGian(NAM);
		ThoiGianEntity t = getThoiGianTheoThangNam(THANG, NAM);

		List<CTDichVuEntity> dsCTDV = getDSCTDV_ChuaTonTai(THANG, NAM);

		// this.themDSCTDV(dsCTDV);
		String dateStr = "";
		if (THANG < 10) {
			dateStr += "0" + THANG + "/" + NAM;
		} else {
			dateStr = THANG + "/" + NAM;
		}
		// List<CTDichVuEntity> dsCTDV1 = getCTDVTheoTG(THANG, NAM);
		List<CTDichVuEntity> ctdv = getCTDVTheoTG(t.getTHANG(), t.getNAM());

		for (CTDichVuEntity c : dsCTDV) {
			ctdv.add(c);
		}
		
		model.addAttribute("date", dateStr);
		model.addAttribute("dsCTDichVu", ctdv);
		model.addAttribute("dsTrangThai", getDsTrangThai());
		model.addAttribute("dsNhaTro", getDSNhaTro());
		model.addAttribute("nhaTro", new NhaTroEntity());

		return "electricity/index";
	}

	@RequestMapping(value = "index", params = "btnXem")
	public String index1(ModelMap model, HttpServletRequest request,
			@ModelAttribute("CTDichVu") CTDichVuEntity ctDIchVu) {
		List<CTDichVuEntity> dsCTDichVu = new ArrayList<CTDichVuEntity>();
		String maNT = request.getParameter("nhaTro");
		String maTTStr = request.getParameter("trangThai");
		int maTTInt = -1;
		String dateStr = request.getParameter("date");// 05/2022
		int THANG = 0;
		int NAM = 0;

		try {
			THANG = Integer.parseInt(dateStr.substring(0, dateStr.indexOf("/")));
			NAM = Integer.parseInt(dateStr.substring(dateStr.indexOf("/") + 1));

		} catch (Exception e) {
			model.addAttribute("dsCTDichVu", dsCTDichVu);
			model.addAttribute("date", dateStr);
			model.addAttribute("nhaTro", maNT);
			model.addAttribute("trangThai", maTTInt);
			model.addAttribute("dsTrangThai", getDsTrangThai());
			model.addAttribute("dsNhaTro", getDSNhaTro());
			model.addAttribute("message", "Thời gian không hợp lệ!");
			return "electricity/index";
		}

		ktThoiGian(NAM);
		ThoiGianEntity t = getThoiGianTheoThangNam(THANG, NAM);

		List<CTDichVuEntity> dsCTDVChuaTonTai = new ArrayList<CTDichVuEntity>();
		// this.themDSCTDV(dsCTDV);

		if (maNT.equals("null") && maTTStr.equals("null")) {
			dsCTDichVu = getCTDVTheoTG(THANG, NAM);
			dsCTDVChuaTonTai=getDSCTDV_ChuaTonTai(THANG, NAM);

		} else if (!maNT.equals("null") && maTTStr.equals("null")) {
			dsCTDichVu = getCTDVTheoTG_MANT(THANG, NAM, maNT);
			dsCTDVChuaTonTai=getDSCTDV_ChuaTonTai_theoMANT(THANG, NAM, maNT);
		} else if (maNT.equals("null") && !maTTStr.equals("null")) {
			maTTInt = Integer.parseInt(maTTStr);
			dsCTDichVu = getCTDVTheoTG_MATT(THANG, NAM, maTTInt);
			dsCTDVChuaTonTai=getDSCTDV_ChuaTonTai_theoMaTT(THANG, NAM, maTTInt);
		} else {
			maTTInt = Integer.parseInt(maTTStr);
			dsCTDichVu = getCTDVTheoTG_MANT_MATT(THANG, NAM, maNT, maTTInt);
			dsCTDVChuaTonTai=getDSCTDV_ChuaTonTai_theoMANT_MaTT(THANG, NAM, maNT, maTTInt);
		}

		for (CTDichVuEntity c : dsCTDVChuaTonTai) {
			dsCTDichVu.add(c);
		}

		model.addAttribute("dsCTDichVu", dsCTDichVu);
		model.addAttribute("date", dateStr);
		model.addAttribute("nhaTro", maNT);
		model.addAttribute("trangThai", maTTInt);
		model.addAttribute("dsTrangThai", getDsTrangThai());
		model.addAttribute("dsNhaTro", getDSNhaTro());
		return "electricity/index";
	}

	public List<TrangThaiEntity> getDsTrangThai() {
		Session session = factory.getCurrentSession();
		String hql = "FROM TrangThaiEntity";
		Query query = session.createQuery(hql);
		List<TrangThaiEntity> trangThai = query.list();
		return trangThai;
	}

	public List<HopDongEntity> getDsHopDong() {
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDongEntity where DAHUY = 0 and phong.nhatro.chuTro.MACT= :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("MACT", mact);
		List<HopDongEntity> ds = query.list();
		return ds;
	}

	public List<CTDichVuEntity> getCTDichVu() {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTDichVuEntity WHERE dichVu.TENDV= :TENDV AND hopDong.phong.nhatro.chuTro.MACT= :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("TENDV", "ĐIỆN");
		query.setParameter("MACT", mact);
		List<CTDichVuEntity> dsDichVu = query.list();
		return dsDichVu;
	}

	public List<CTDichVuEntity> getCTDVTheoTG(int THANG, int NAM) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTDichVuEntity WHERE dichVu.TENDV= :TENDV and thoiGian.THANG= :THANG and thoiGian.NAM= :NAM "
				+ "and hopDong.DAHUY = 0 and hopDong.phong.nhatro.chuTro.MACT= :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("TENDV", "ĐIỆN");
		query.setParameter("THANG", THANG);
		query.setParameter("NAM", NAM);
		query.setParameter("MACT", mact);
		List<CTDichVuEntity> dsDichVu = query.list();
		return dsDichVu;
	}

	public List<CTDichVuEntity> getCTDVTheoTG_MANT(int THANG, int NAM, String MANT) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTDichVuEntity WHERE dichVu.TENDV= :TENDV and thoiGian.THANG= :THANG and thoiGian.NAM= :NAM "
				+ "and hopDong.DAHUY = 0 and hopDong.phong.nhatro.MANT= :MANT"
				+ " and hopDong.phong.nhatro.chuTro.MACT= :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("TENDV", "ĐIỆN");
		query.setParameter("THANG", THANG);
		query.setParameter("NAM", NAM);
		query.setParameter("MANT", MANT);
		query.setParameter("MACT", mact);
		List<CTDichVuEntity> dsDichVu = query.list();
		return dsDichVu;
	}

	public List<CTDichVuEntity> getCTDVTheoTG_MATT(int THANG, int NAM, int MATT) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTDichVuEntity WHERE dichVu.TENDV= :TENDV and thoiGian.THANG= :THANG and thoiGian.NAM= :NAM "
				+ "and hopDong.DAHUY = 0 and hopDong.phong.trangThai.MATT= :MATT"
				+ " and hopDong.phong.nhatro.chuTro.MACT= :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("TENDV", "ĐIỆN");
		query.setParameter("THANG", THANG);
		query.setParameter("NAM", NAM);
		query.setParameter("MATT", MATT);
		query.setParameter("MACT", mact);
		List<CTDichVuEntity> dsDichVu = query.list();
		return dsDichVu;
	}

	public List<CTDichVuEntity> getCTDVTheoTG_MANT_MATT(int THANG, int NAM, String MANT, int MATT) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTDichVuEntity WHERE dichVu.TENDV= :TENDV and thoiGian.THANG= :THANG and thoiGian.NAM= :NAM "
				+ "and hopDong.DAHUY = 0 and hopDong.phong.nhatro.MANT= :MANT "
				+ "and hopDong.phong.trangThai.MATT= :MATT" + " and hopDong.phong.nhatro.chuTro.MACT= :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("TENDV", "ĐIỆN");
		query.setParameter("THANG", THANG);
		query.setParameter("NAM", NAM);
		query.setParameter("MANT", MANT);
		query.setParameter("MATT", MATT);
		query.setParameter("MACT", mact);
		List<CTDichVuEntity> dsDichVu = query.list();
		return dsDichVu;
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
		String hql = "FROM NhaTroEntity where chuTro.MACT= :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("MACT", mact);
		List<NhaTroEntity> dsNhaTro = query.list();
		return dsNhaTro;
	}

	@RequestMapping(value = "save")
	public String addListService(ModelMap model, HttpServletRequest request) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			/* session.save(dsCTDichVu); */
			t.commit();
			model.addAttribute("message", "Thêm thành công!");
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Thêm thất bại!");
		} finally {
			session.close();
		}
		/* model.addAttribute("dsCTDichVu", getCTDichVu()); */
		return "electricity/index";
	}

	// ==================================================
	@RequestMapping(value = "saveCTDV")
	public String addService(ModelMap model, HttpServletRequest request) {
		int maHd = Integer.parseInt(request.getParameter("hopDong1"));
		int maDv = Integer.parseInt(request.getParameter("dichVu1"));
		int maTg = Integer.parseInt(request.getParameter("thoiGian1"));
		int csc = Integer.parseInt(request.getParameter("CHISOCU1"));
		int csm = Integer.parseInt(request.getParameter("CHISOMOI1"));
		ThoiGianEntity t = getThoiGianTheoMa(maTg);
		int THANG = t.getTHANG();
		int NAM = t.getNAM();
		if (csc > csm) {
			model.addAttribute("lbThongBaoThemDV", "Chỉ số cũ không được lớn hơn chỉ số mới");
		}else if(csc<0 || csm<0) {
			model.addAttribute("lbThongBaoThemDV", "Chỉ số phải lớn hơn hoặc bằng 0");
		}
		else {
			CTDichVuEntity ctdv = getCTDVTheoMa(maHd, maDv, maTg);

			if (ctdv == null) {
				CTDichVuEntity ctdv2 = new CTDichVuEntity(getService(maDv), getHopDongTheoMa(maHd), t, csc, csm);
				Integer i = this.themCTDV(ctdv2);

				if (i != 0) {
					model.addAttribute("lbThongBaoThemDV", "1");
				} else {
					model.addAttribute("lbThongBaoThemDV", "Lưu thất bại!");
				}
			} else {
				System.out.println("Luu chi tiet dich vu thát bai 1: " + ctdv.getDichVu().getMADV() + "  "
						+ ctdv.getThoiGian().getMATG() + " " + ctdv.getHopDong().getMAHOPDONG());
				Integer i = this.updateCTDV(ctdv, csc, csm);
				if (i != 0) {
					model.addAttribute("lbThongBaoThemDV", "1");
				} else {
					model.addAttribute("lbThongBaoThemDV", "Lưu thất bại!");
				}
			}
			int thangTmp = THANG;
			int namTmp = NAM;
			if (THANG == 12) {
				thangTmp = 0;
				namTmp = namTmp + 1;
			}
			ktThoiGian(NAM);
			ThoiGianEntity t1 = getThoiGianTheoThangNam(thangTmp + 1, namTmp);
			CTDichVuEntity ctdv1 = new CTDichVuEntity();

			ctdv1 = getCTDVTheoMa(maHd, maDv, t1.getMATG());

			if (ctdv1 == null) {
				CTDichVuEntity ctdv2 = new CTDichVuEntity(getService(maDv), getHopDongTheoMa(maHd),
						getThoiGianTheoThangNam(thangTmp + 1, namTmp), csm, 0);
				this.themCTDV(ctdv2);
			}
		}
		List<CTDichVuEntity> dsCTDichVu = new ArrayList<CTDichVuEntity>();
		List<CTDichVuEntity> dsCTDichVuChuaTonTai = new ArrayList<CTDichVuEntity>();
		String maNT = request.getParameter("nhaTro");
		String maTTStr = request.getParameter("trangThai");
		int maTTInt = -1;
		if (maNT == null && maTTStr == null) {
			dsCTDichVu = getCTDVTheoTG(THANG, NAM);
			dsCTDichVuChuaTonTai = getDSCTDV_ChuaTonTai(THANG, NAM);
		} else if (maNT != null && maTTStr == null) {
			dsCTDichVu = getCTDVTheoTG_MANT(THANG, NAM, maNT);
			dsCTDichVuChuaTonTai = getDSCTDV_ChuaTonTai_theoMANT(THANG, NAM, maNT);
		} else if (maNT == null && maTTStr != null) {
			maTTInt = Integer.parseInt(maTTStr);
			dsCTDichVu = getCTDVTheoTG_MATT(THANG, NAM, maTTInt);
			dsCTDichVuChuaTonTai = getDSCTDV_ChuaTonTai_theoMaTT(THANG, NAM, maTTInt);
		} else {
			maTTInt = Integer.parseInt(maTTStr);
			dsCTDichVu = getCTDVTheoTG_MANT_MATT(THANG, NAM, maNT, maTTInt);
			dsCTDichVuChuaTonTai = getDSCTDV_ChuaTonTai_theoMANT_MaTT(THANG, NAM, maNT, maTTInt);
		}
		for (CTDichVuEntity c : dsCTDichVuChuaTonTai) {
			dsCTDichVu.add(c);
		}
		model.addAttribute("dsCTDichVu", dsCTDichVu);
		model.addAttribute("date", THANG + "/" + NAM);
		model.addAttribute("nhaTro", maNT);
		model.addAttribute("trangThai", maTTInt);
		model.addAttribute("dsTrangThai", getDsTrangThai());
		model.addAttribute("dsNhaTro", getDSNhaTro());
		return "electricity/index";
	}

	// ==================================
	public DichVuEntity getService(int maDV) {
		Session session = factory.getCurrentSession();
		String hql = "FROM DichVuEntity where MADV = :MADV";
		Query query = session.createQuery(hql);
		query.setParameter("MADV", maDV);
		DichVuEntity dv = new DichVuEntity();
		if (query.list().size() == 0) {
			return null;
		} else {
			dv = (DichVuEntity) query.list().get(0);
		}
		return dv;
	}

	public int getMaDVDien() {
		Session session = factory.getCurrentSession();
		String hql = "FROM DichVuEntity where TENDV = :TENDV";
		Query query = session.createQuery(hql);
		query.setParameter("TENDV", "ĐIỆN");
		DichVuEntity dv = new DichVuEntity();
		if (query.list().size() == 0) {
			return -1;
		} else {
			dv = (DichVuEntity) query.list().get(0);
		}
		return dv.getMADV();
	}

	public ThoiGianEntity getThoiGianTheoMa(int MATG) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ThoiGianEntity where MATG = :MATG";
		Query query = session.createQuery(hql);
		query.setParameter("MATG", MATG);
		ThoiGianEntity dv = (ThoiGianEntity) query.list().get(0);
		return dv;
	}

	public List<QuyDinhEntity> getDsQuyDinh() {
		Session session = factory.getCurrentSession();
		String hql = "FROM QuyDinhEntity";
		Query query = session.createQuery(hql);
		List<QuyDinhEntity> dv = query.list();
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

	public List<CTDichVuEntity> getDSCTDV_ChuaTonTai(int THANG, int NAM) {
		List<HopDongEntity> dsHD = getDsHopDong();
		List<CTDichVuEntity> dsCTDV = getCTDichVu();
		List<CTDichVuEntity> ds = new ArrayList<CTDichVuEntity>();

		Date d = new java.sql.Date(NAM - 1900, THANG, 1);
		int maDVDien = getMaDVDien();
		ThoiGianEntity tg = getThoiGianTheoThangNam(THANG, NAM);
		boolean kt;
		for (int i = 0; i < dsHD.size(); i++) {
			boolean kt1 = false;
			List<QuyDinhEntity> dsQDTmp = (List<QuyDinhEntity>) dsHD.get(i).getPhong().getNhatro().getDsQuyDinh();
			for (QuyDinhEntity q : dsQDTmp) {
				if (q.getDichVu().getMADV() == maDVDien) {
					kt1 = true;
					break;
				}
			}
			if (!kt1) {
				dsHD.remove(i);
				i--;
			}
		}
		for (HopDongEntity h : dsHD) {
			if (d.compareTo(h.getNGAYKY()) >= 0) {
				kt = true;
				for (CTDichVuEntity c : dsCTDV) {
					if (h.getMAHOPDONG() == c.getHopDong().getMAHOPDONG() && c.getThoiGian().getTHANG() == THANG
							&& c.getThoiGian().getNAM() == NAM) {
						kt = false;
						break;
					}
				}
				if (kt) {
					ds.add(new CTDichVuEntity(getService(getMaDVDien()), h, tg, 0, 0));
				}
			}
		}
		return ds;
	}

	public List<CTDichVuEntity> getDSCTDV_ChuaTonTai_theoMANT(int THANG, int NAM, String MANT) {
		List<CTDichVuEntity> list = getDSCTDV_ChuaTonTai(THANG, NAM);
		List<CTDichVuEntity> tmp = new ArrayList<CTDichVuEntity>();
		for (CTDichVuEntity c : list) {
			if (c.getHopDong().getPhong().getNhatro().getMANT().equals(MANT)) {
				tmp.add(c);
			}
		}
		return tmp;
	}

	public List<CTDichVuEntity> getDSCTDV_ChuaTonTai_theoMaTT(int THANG, int NAM, int MATT) {
		List<CTDichVuEntity> list = getDSCTDV_ChuaTonTai(THANG, NAM);
		List<CTDichVuEntity> tmp = new ArrayList<CTDichVuEntity>();
		for (CTDichVuEntity c : list) {
			if (c.getHopDong().getPhong().getTrangThai().getMATT() == MATT) {
				tmp.add(c);
			}
		}
		return tmp;
	}

	public List<CTDichVuEntity> getDSCTDV_ChuaTonTai_theoMANT_MaTT(int THANG, int NAM, String MANT, int MATT) {
		List<CTDichVuEntity> list = getDSCTDV_ChuaTonTai(THANG, NAM);
		List<CTDichVuEntity> tmp = new ArrayList<CTDichVuEntity>();
		for (CTDichVuEntity c : list) {
			if (c.getHopDong().getPhong().getNhatro().getMANT().equals(MANT)
					&& c.getHopDong().getPhong().getTrangThai().getMATT() == MATT) {
				tmp.add(c);
			}
		}
		return tmp;
	}

	/*
	 * public Integer themThoiGian(int THANG, int NAM) { Session session =
	 * factory.openSession(); Transaction t = session.beginTransaction();
	 * ThoiGianEntity tg = new ThoiGianEntity(THANG, NAM); try { session.save(tg);
	 * t.commit(); } catch (Exception e) { t.rollback(); return 0; } finally {
	 * session.close(); } return 1; }
	 */

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

	public Integer updateCTDVTheoCSC(CTDichVuEntity ctdv, int csc) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery("update CTDichVuEntity " + "set CHISOCU= " + csc
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

	/*
	 * public Integer themDSCTDV(List<CTDichVuEntity> dsCt) { Session session =
	 * factory.openSession(); Transaction t = session.beginTransaction();
	 * 
	 * try { for (CTDichVuEntity c : dsCt) { session.save(c); } t.commit(); } catch
	 * (Exception e) { t.rollback(); return 0; } finally { session.close(); } return
	 * 1; }
	 */

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

//Them thoi gian
	public List<ThoiGianEntity> getDsThoiGian(int nam) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ThoiGianEntity where NAM= :NAM";
		Query query = session.createQuery(hql);
		query.setParameter("NAM", nam);
		List<ThoiGianEntity> list = query.list();
		return list;
	}

	public Integer themThoiGian(List<ThoiGianEntity> list) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			for (ThoiGianEntity tg : list) {
				session.save(tg);
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

	public void ktThoiGian(int NAM) {
		List<ThoiGianEntity> list = getDsThoiGian(NAM);
		List<ThoiGianEntity> tmp = new ArrayList<ThoiGianEntity>();
		if (list.size() < 12) {
			Boolean kt = false;
			for (int i = 1; i <= 12; i++) {
				kt = false;
				for (ThoiGianEntity t : list) {
					if (t.getTHANG() == i) {
						kt = true;
						break;
					}
				}
				if (!kt) {
					tmp.add(new ThoiGianEntity(i, NAM));
				}
			}
		}
		this.themThoiGian(tmp);
	}

//	Táº¡o mÃ£ hÃ³a Ä‘Æ¡n ngáº«u nhiÃªn
	private static final String alpha = "abcdefghijklmnopqrstuvwxyz";
	private static final String alphaUpperCase = alpha.toUpperCase();
	private static final String digits = "0123456789";
	private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
	private static Random generator = new Random();

	public String randomAlphaNumeric(int numberOfCharactor) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numberOfCharactor; i++) {
			int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
			char ch = ALPHA_NUMERIC.charAt(number);
			sb.append(ch);
		}
		return sb.toString();
	}

	public static int randomNumber(int min, int max) {
		return generator.nextInt((max - min) + 1) + min;
	}
}
