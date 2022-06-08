package qlpt.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.property.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import qlpt.entity.HoaDonEntity;
import qlpt.entity.HopDongEntity;
import qlpt.entity.NhaTroEntity;
import qlpt.entity.PhongEntity;
import qlpt.entity.ThoiGianEntity;

@Transactional
@Controller
@RequestMapping("home/")
public class HomeController {
	@Autowired
	SessionFactory factory;

	public String mact;
	public int slPhongDaThue = 0;
	public int slPhongTrong = 0;

	@RequestMapping("/index")
	public String index(ModelMap model, HttpSession ss) {
		mact = ss.getAttribute("mact").toString();
		ktThoiGian();
		List<PhongEntity> dsPhong = getDSPhong();
		List<PhongEntity> dsPhongTrong = getDSPhongTrong();
		List<HoaDonEntity> dsHoaDon = getDSHoaDonChuaThuTien();
		List<HopDongEntity> dsHopDongSapHetHan = getDSHopDongSapHetHan();
		dsHopDongSapHetHan.forEach(t -> {
			System.out.println(t.getMAHOPDONG());
		});
		slPhongDaThue = 0;
		slPhongTrong = 0;
		thongKeSLPhongTheoTrangThai(dsPhong);
		model.addAttribute("slPhongDaThue", slPhongDaThue);
		model.addAttribute("slPhongTrong", slPhongTrong);
		model.addAttribute("dsPhongTrong", dsPhongTrong);
		model.addAttribute("dsHoaDon", dsHoaDon);
		model.addAttribute("dsHopDongSapHetHan", dsHopDongSapHetHan);
		return "home/index";
	}

	public List<NhaTroEntity> getDSNhaTro() {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhaTroEntity WHERE MACT = :mact";
		Query query = session.createQuery(hql);
		query.setParameter("mact", mact);
		List<NhaTroEntity> list = query.list();
		return list;
	}

	public List<PhongEntity> getDSPhong() {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhongEntity where nhatro.chuTro.MACT= :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("MACT", mact);
		List<PhongEntity> list = query.list();
		return list;
	}

	public List<PhongEntity> getDSPhongTrong() {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhongEntity where nhatro.chuTro.MACT= :MACT and trangThai.MATT = 1";
		Query query = session.createQuery(hql);
		query.setParameter("MACT", mact);
		List<PhongEntity> list = query.list();
		return list;
	}

	public List<HoaDonEntity> getDSHoaDonChuaThuTien() {
		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity where TRANGTHAI=0 and hopDong.phong.nhatro.chuTro.MACT= :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("MACT", mact);
		List<HoaDonEntity> list = query.list();
		return list;
	}

	public List<HopDongEntity> getDSHopDongSapHetHan() {
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDongEntity where DAHUY=0 and phong.nhatro.chuTro.MACT= :mact  and DATEDIFF(day,THOIHAN,getdate()) between -30 and 0";
		Query query = session.createQuery(hql);
		query.setParameter("mact", mact);
		List<HopDongEntity> list = query.list();
		return list;
	}

	public void thongKeSLPhongTheoTrangThai(List<PhongEntity> dsPhong) {
		for (PhongEntity p : dsPhong) {
			if (p.getTrangThai().getMATT() == 1) {
				slPhongTrong += 1;
			} else {
				slPhongDaThue += 1;
			}
		}
	}

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

	public void ktThoiGian() {
		LocalDate now = LocalDate.now();
		int NAM = now.getYear();
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

}
