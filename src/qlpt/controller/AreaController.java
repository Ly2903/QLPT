package qlpt.controller;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

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

import qlpt.entity.CTKhachThueEntity;
import qlpt.entity.ChuTroEntity;
import qlpt.entity.NhaTroEntity;
import qlpt.entity.PhongEntity;



@Transactional
@Controller
@RequestMapping("area/")
public class AreaController{
	
	@Autowired
	SessionFactory factory;
	
	public String mact;
	
	@RequestMapping("index")
	public String index(ModelMap model,@ModelAttribute("area") NhaTroEntity area,HttpSession ss )
	{
		this.mact = ss.getAttribute("mact").toString();
		
		List<NhaTroEntity> areas = this.getAreas(mact);
		model.addAttribute("areas",areas);

		model.addAttribute("formHide",null);
		return "area/index";
	}
	@RequestMapping(value="index",params = "linkAdd")
	public String redirectLinkAdd(ModelMap model,@ModelAttribute("area") NhaTroEntity area )
	{
		model.addAttribute("btnStatus","btnAdd");
		model.addAttribute("formHide",1);
		return "area/index";
	}
	// GET AREA
	public List<NhaTroEntity> getAreas() {
		Session session = factory.getCurrentSession();
	

		String hql = "FROM NhaTroEntity WHERE MACT = :mact";
		Query query = session.createQuery(hql);
		query.setParameter("mact", mact);
		List<NhaTroEntity> list = query.list();
		return list;
	}
	public List<NhaTroEntity> getAreas(String mact) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhaTroEntity WHERE MACT = :mact";
		Query query = session.createQuery(hql);
		query.setParameter("mact", mact);
		List<NhaTroEntity> list = query.list();
		return list;
	}
	public ChuTroEntity getCT(String id) {
		System.out.println("checker chutroentity"+id);
		Session session = factory.getCurrentSession();
		String hql = "FROM ChuTroEntity WHERE MACT = :MACT";
		Query query = session.createQuery(hql);
		query.setParameter("MACT",id);
		ChuTroEntity nt = (ChuTroEntity) query.list().get(0);
		return nt;
	}
	
	public NhaTroEntity getArea(String id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhaTroEntity where MANT = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		NhaTroEntity list = (NhaTroEntity) query.list().get(0);
		System.out.println("in o getArea "+list.toString());

		return list;
	}
	
	// FINISH GET AREA
	
	// ADD AREA 
	
	public Integer insertArea(NhaTroEntity area) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(area);
			t.commit();
		} catch (Exception e) {
			System.out.println("Loi area "+e.getMessage());
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
	
	@RequestMapping(value="index",params = "btnAdd")
	public String addArea(ModelMap model,@ModelAttribute("area") NhaTroEntity area)
	{
		System.out.println("check add area "+this.getCT(mact).toString());
		model.addAttribute("areas",this.getAreas());
		String uuid = UUID.randomUUID().toString();
//		area.setMANT("MK"+getAreas().size()+100);
		area.setMANT(uuid);
		area.setChutro(this.getCT(mact));
		Integer temp = this.insertArea(area);
		if(temp != 0)
				model.addAttribute("message", "Thêm thành công");
		else
			model.addAttribute("message", "Thêm thất bại!");
		model.addAttribute("areas",this.getAreas());
		return "area/index";
	}
	// FINISH ADD AREA 
	
	// EDIT NHATRO
	@RequestMapping(value = "index", params = "btnEdit")
	public String edit_NhaTro(ModelMap model,
			@ModelAttribute("area") NhaTroEntity area) {
		
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
//			System.out.println(area.getMANT());
//			String hql = "update NhaTroEntity set TENNT = :tennt, TINH_TP = :tinhtp, QUAN_HUYEN = :quanhuyen, PHUONG_XA = :phuongxa,DIACHI = :diachi,MACT = :mact where MANT = "
//					+ String.valueOf(area.getMANT());
//			Query query = session.createQuery(hql);
//			query.setParameter("tennt",area.getTENNT());
//			query.setParameter("tinhtp",area.getTINH_TP());
//			query.setParameter("quanhuyen",area.getQUAN_HUYEN());
//			query.setParameter("phuongxa", area.getPHUONG_XA());
//			query.setParameter("diachi", area.getDIACHI());
//			query.setParameter("mact", area.getChutro().getMACT());
//			int a = query.executeUpdate();
			area.setChutro(this.getCT(mact));
			session.update(area);
			model.addAttribute("message", "Update thành công !");
			t.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			e.printStackTrace();
			System.out.println("Loi sua nha tro " + e.getMessage());
			model.addAttribute("message", "Update thất bại !");
		}
		finally
		{
			session.close();
		}
		 model.addAttribute("areas", this.getAreas());

		return "area/index";
	}
	@RequestMapping(value="index/{id}",params = "linkEdit")
	public String editNhaTro(@ModelAttribute("area") NhaTroEntity area,ModelMap model,@PathVariable("id") String id)
	{
		model.addAttribute("btnStatus", "btnEdit");
		model.addAttribute("area", this.getArea(id));
//		model.addAttribute("areas", this.getAreas());
		model.addAttribute("formHide",1);
		return "area/index";
	}
	// FINISH EDIT NHATRO
//	// DELETE AREA
	@RequestMapping(value="index/{id}.htm",params = "linkDelete")
	public String deleteArea(ModelMap model,@ModelAttribute("area") NhaTroEntity area,@PathVariable("id") String id)
	{
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		if(this.checkXoaNhaTro(id)==1)
		{
			  String hql = "FROM NhaTroEntity where MANT = :id";	
				try {
					Query query = session.createQuery(hql);
					query.setParameter("id", id);
					NhaTroEntity nt = (NhaTroEntity) query.list().get(0);
					String tenTro = nt.getTENNT();
					session.delete(nt);
					t.commit();
					String success = "Xoá thành công nhà trọ "+tenTro+" !";
					model.addAttribute("message", success);
				} catch (Exception e) {
					System.out.println(e.toString());
					e.printStackTrace();
					t.rollback();					
				} finally {
					session.close();
				}

		}else
		{
			String failure = "Không thể xoá vì nhà trọ này đã có phòng ! ";
			model.addAttribute("message", failure);
		}
		model.addAttribute("areas", this.getAreas());
		return "area/index";
	}
//	// FINISH DELETE AREA
	// UPDATE CHỦ TRỌ
	@ModelAttribute("gender")
	public String[] getGender() {
		String[] genders = { "Nam", "Nữ"};
		return genders;
	}

	@RequestMapping("chinhthongtin/{mact}.htm")
	public String chinhThongTinGet(@PathVariable("mact") String maCT,@ModelAttribute("chutro") ChuTroEntity chutro,ModelMap model)
	{
		ChuTroEntity ct = this.getCT(maCT);
		model.addAttribute("chutro", ct);
		return "area/chinhthongtin";
	}
	private int checkPassword(String pass)
	{
		Session session = factory.getCurrentSession();
		String hql = "FROM ChuTroEntity WHERE MACT = :mact AND PASSWORD = :pass";
		Query query = session.createQuery(hql);
		query.setParameter("pass",pass);
		query.setParameter("mact",this.mact);
		List<ChuTroEntity> list = query.list();
		if(list.size() == 0) return 0; 
		return 1;
	}
	public int checkCCCD(String cccd)
	{
		Session session = factory.getCurrentSession();
		String hql = "FROM ChuTroEntity WHERE CCCD = :cccd AND MACT <> :mact";
		Query query = session.createQuery(hql);
		query.setParameter("cccd",cccd);
		query.setParameter("mact",this.mact);
		List<ChuTroEntity> list = query.list();
		if(list.size() == 0) return 1; 
		return 0;
	}
	@RequestMapping(value="chinhthongtin/{mact}.htm",method = RequestMethod.POST)
	public String chinhThongTinPost(@ModelAttribute("chutro") ChuTroEntity chutro,ModelMap model,HttpServletRequest req,HttpSession ss)
	{
		this.mact = ss.getAttribute("mact").toString();
		String passCurrent = req.getParameter("passht");
		if(!passCurrent.isEmpty())
		{
			int x = this.checkPassword(passCurrent);
			if(x == 1) 
			{
				chutro.setPASSWORD(req.getParameter("pass"));
			}else {
				model.addAttribute("mess","Mật khẩu hiện tại không chính xác !");
				return "area/chinhthongtin";
			}
		}
		if(this.checkCCCD(chutro.getCCCD())==0)
		{
			model.addAttribute("mess","Căn cước công dân đã trùng !");
			return "area/chinhthongtin";
		}
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(chutro);
			t.commit();
			model.addAttribute("mess","Cập nhật thông tin thành công !");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			model.addAttribute("mess","Cập nhật thất bại");
		}finally {
			session.close();
		}
		return "area/chinhthongtin";
	}
	// END UPDATE
	// check nhatro
	public int checkXoaNhaTro(String mant)
	{
		Session session = factory.getCurrentSession();
		String hql = "FROM PhongEntity WHERE MANT = :mant";
		Query query = session.createQuery(hql);
		query.setParameter("mant",mant);
		List<PhongEntity> list = query.list();
		if(list.size() == 0) return 1; 
		return 0;
	}
	// end check nha tro
}
