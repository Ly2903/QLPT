package qlpt.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
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

import javassist.expr.NewArray;
import qlpt.entity.BaoTriEntity;
import qlpt.entity.CTDichVuEntity;
import qlpt.entity.DichVuEntity;
import qlpt.entity.HoaDonEntity;
import qlpt.entity.HopDongEntity;
import qlpt.entity.KhachThueEntity;
import qlpt.entity.NhaTroEntity;
import qlpt.entity.PhongEntity;
import qlpt.entity.TrangThaiEntity;

@Transactional
@Controller
@RequestMapping("incurred/")
public class IncurredController {

	@Autowired
	SessionFactory factory;
	private String mact;
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(ModelMap model, HttpSession ss) {
		mact=ss.getAttribute("mact").toString();
		model.addAttribute("dsNhaTro", getDSNhaTro());
		model.addAttribute("dsPhatSinh", getDsPhatSinh());
		model.addAttribute("nhatro", new NhaTroEntity());
		return "incurred/index";
	}

	@RequestMapping(value = "index", method = RequestMethod.POST)
	public String index1(ModelMap model, HttpServletRequest request, @ModelAttribute("nhatro") NhaTroEntity nhaTro)
			throws ParseException {

		model.addAttribute("dsNhaTro", getDSNhaTro());
		model.addAttribute("nhatroselect", nhaTro.getMANT());
		String dateStr = request.getParameter("date");// 05/2022
		String maNT = request.getParameter("nhaTro");
		if (dateStr.isEmpty()) {
			model.addAttribute("message", "Bạn chưa chọn khung thời gian");
			return "incurred/index";
		} else {
			int THANG = Integer.parseInt(dateStr.substring(0, dateStr.indexOf("/")));
			int NAM = Integer.parseInt(dateStr.substring(dateStr.indexOf("/") + 1));

			model.addAttribute("dsPhatSinh", getDsPhatSinhTheoThang(THANG, NAM));
			model.addAttribute("date", dateStr);

			return "incurred/index";
		}

	}

	@RequestMapping(value = "index", params = "btnXem")
	public String index1(ModelMap model, HttpServletRequest request, @ModelAttribute("BaoTri") BaoTriEntity baotri)
			throws ParseException {

		String dateStr = request.getParameter("date");// 05/2022
		String maNT = request.getParameter("nhaTro");
		int THANG = Integer.parseInt(dateStr.substring(0, dateStr.indexOf("/")));
		int NAM = Integer.parseInt(dateStr.substring(dateStr.indexOf("/") + 1));

		String s = "05/" + dateStr;

		LocalDateTime now = LocalDateTime.now();
		Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
		Date dates = Date.from(instant);
		SimpleDateFormat ngay = new SimpleDateFormat("dd/MM/yyyy");
		Date ngayxuat = ngay.parse(s);

		model.addAttribute("nhatroselect", maNT);
		model.addAttribute("dsNhaTro", getDSNhaTro());
		model.addAttribute("dsPhatSinh", getDsPhatSinhTheoThang(THANG, NAM));
		model.addAttribute("date", dateStr);
		return "incurreds/index";
	}

	public List<PhongEntity> getDsPhong() {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhongEntity where nhatro.chuTro.MACT =:MACT";
		Query query = session.createQuery(hql);
		query.setParameter("MACT", mact);
		List<PhongEntity> dsPhong = query.list();
		return dsPhong;
	}

	public List<NhaTroEntity> getDSNhaTro() {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhaTroEntity WHERE chuTro.MACT= :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("MACT", mact);
		List<NhaTroEntity> dsNhaTro = query.list();
		return dsNhaTro;
	}

	public NhaTroEntity getNhaTro(String MANT) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhaTroEntity where MANT = :MANT and AND chuTro.MACT= :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("MANT", MANT);
		query.setParameter("MACT", mact);
		NhaTroEntity nt = (NhaTroEntity) query.list().get(0);
		return nt;
	}

	/* Get Incurred */
	public List<BaoTriEntity> getDsPhatSinh() {
		Session session = factory.getCurrentSession();
		String hql = "FROM BaoTriEntity";
		Query query = session.createQuery(hql);
		List<BaoTriEntity> incurreds = query.list();
		return incurreds;
	}

	public List<BaoTriEntity> getDsPhatSinhTheoMaNT(String MANT) {
		Session session = factory.getCurrentSession();
		String hql = "FROM BaoTriEntity";
		Query query = session.createQuery(hql);
		List<BaoTriEntity> incurreds = query.list();
		return incurreds;
	}

	public List<BaoTriEntity> getDsPhatSinhtheoTG(Date NGAY) {
		Session session = factory.getCurrentSession();
		String hql = "FROM BaoTriEntity WHERE NGAY= :NGAY ";
		Query query = session.createQuery(hql);
		query.setParameter("NGAY", NGAY);
		List<BaoTriEntity> dsBaoTri = query.list();
		return dsBaoTri;
	}

	public List<BaoTriEntity> getDsPhatSinhTheoThang(int THANG, int NAM) {
		Session session = factory.getCurrentSession();
		String hql = "FROM BaoTriEntity WHERE MONTH(NGAY)= :THANG AND YEAR(NGAY) = :NAM ";
		Query query = session.createQuery(hql);
		query.setParameter("THANG", THANG);
		query.setParameter("NAM", NAM);
		List<BaoTriEntity> dsBaoTri = query.list();
		return dsBaoTri;
	}

	public BaoTriEntity getIncurred(int MABAOTRI) {
		Session session = factory.getCurrentSession();
		String hql = "FROM BaoTriEntity where MABAOTRI = :MABAOTRI";
		Query query = session.createQuery(hql);
		query.setParameter("MABAOTRI", MABAOTRI);
		BaoTriEntity bt = (BaoTriEntity) query.list().get(0);
		return bt;
	}

	// add Incurred
	@RequestMapping("/create")
	public String create(ModelMap model, @ModelAttribute("incurred") BaoTriEntity incurred, HttpSession ss) {
		mact = ss.getAttribute("mact").toString();
		model.addAttribute("dsNhaTro", getDSNhaTro());
		model.addAttribute("dsPhong", getDsPhong());
		model.addAttribute("dsPhatSinh", getDsPhatSinh());
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("title", "Them phat sinh");
		return "incurred/create1";
	}

	// Save Incurred

	@RequestMapping(value = "create", params = "btnAdd")
	public String addIncurred(@ModelAttribute("incurred") BaoTriEntity incurred, ModelMap model, HttpServletRequest request) {
		String dateStr=request.getParameter("NGAY");
		String chiphiStr=request.getParameter("CHIPHI");
		String motaString=request.getParameter("MOTA");
		if(dateStr.isEmpty())
		{
			model.addAttribute("message", "Chưa nhập thời gian");
			model.addAttribute("dsNhaTro", getDSNhaTro());
			model.addAttribute("dsPhong", getDsPhong());
			return "incurred/create1";
		}
		if(chiphiStr.isEmpty())
		{
			model.addAttribute("message", "Chưa nhập chi phí");
			model.addAttribute("dsNhaTro", getDSNhaTro());
			model.addAttribute("dsPhong", getDsPhong());
			return "incurred/create1";
		}
		if(motaString.isEmpty())
		{
			model.addAttribute("message", "Chưa nhập mô tả");
			model.addAttribute("dsNhaTro", getDSNhaTro());
			model.addAttribute("dsPhong", getDsPhong());
			return "incurred/create1";
		}
		else
		{
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {
				session.save(incurred);
				t.commit();
				model.addAttribute("message", "Thêm thành công");
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message", "Thêm thất bại");
			} finally {
				session.close();
			}
		}
		
		model.addAttribute("dsNhaTro", getDSNhaTro());
		model.addAttribute("dsPhatSinh", getDsPhatSinh());
		return "incurred/index";
	}

	// Update Incurred
	@RequestMapping(value = "create/{MABAOTRI}", params = "linkEdit")
	public String editIncurred(ModelMap model, @ModelAttribute("incurred") BaoTriEntity incurred,
			@PathVariable("MABAOTRI") Integer MABAOTRI) {
		BaoTriEntity b=getIncurred(MABAOTRI);
		model.addAttribute("TENNHATRO", b.getPhong().getNhatro().getTENNT());
		model.addAttribute("TENPHONG", b.getPhong().getTENPHONG());
		model.addAttribute("incurred", b);
		model.addAttribute("btnStatus", "btnUpdate");
		model.addAttribute("title", "sua phat sinh");
		return "incurred/create1";
	}

	@RequestMapping(value = "create", params = "btnUpdate")
	public String updateIncurred(ModelMap model,
			@ModelAttribute("incurred") BaoTriEntity incurred, HttpServletRequest request) throws ParseException {
			
		Double chiphi=Double.parseDouble(request.getParameter("CHIPHI"))  ;
		SimpleDateFormat a = new SimpleDateFormat("dd/MM/yyyy");
		Date ngay=a.parse(request.getParameter("NGAY"));
		String mota=request.getParameter("MOTA");
		 int mabaotri=Integer.parseInt(request.getParameter("MABAOTRI")); 
		int t=updateBaoTri(mabaotri, ngay, chiphi, mota);
		if (t != 0) {
			model.addAttribute("message", "Sửa thành công !");
			model.addAttribute("dsNhaTro", getDSNhaTro());
			model.addAttribute("dsPhatSinh", getDsPhatSinh());
			return "incurred/index";
		} else {
			model.addAttribute("message", "Sửa thất bại !");
			model.addAttribute("dsNhaTro", getDSNhaTro());
			model.addAttribute("dsPhatSinh", getDsPhatSinh());
			return "incurred/index";
			
		}
		
	}

	public int update(BaoTriEntity bt) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(bt);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
	public Integer updateBaoTri(int mabaotri,Date ngay, Double chiphi,  String mota )
	{

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
		String hql = "UPDATE BaoTriEntity SET MOTA = :mota, NGAY = :ngay, CHIPHI = :chiphi WHERE MABAOTRI = :mabaotri";
			Query query = session.createQuery(hql);
			query.setParameter("mabaotri",mabaotri);
			query.setParameter("ngay",ngay);
			query.setParameter("chiphi",chiphi);
			query.setParameter("mota",mota);
			int a = query.executeUpdate();
			t.commit();
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			e.printStackTrace();
			return 0;
		}
		finally
		{
			session.close();
		}
	}
	// Edit Incurred

	// Delete Incurred

	@RequestMapping(value = "index/{MABAOTRI}", params = "linkDelete")
	public String deleteIncurred(ModelMap model, @PathVariable("MABAOTRI") Integer MABAOTRI) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(getIncurred(MABAOTRI));
			t.commit();
			model.addAttribute("message", "Xóa thành công !");
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Xóa thất bại !");
		} finally {
			session.close();
		}
		model.addAttribute("dsNhaTro", getDSNhaTro());
		model.addAttribute("dsPhatSinh", getDsPhatSinh());
		return "incurred/index";

	}
}
