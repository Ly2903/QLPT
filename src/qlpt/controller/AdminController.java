package qlpt.controller;


import java.util.List;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import qlpt.entity.ChuTroEntity;


@Transactional
@Controller
@Service("mailer")
@RequestMapping("login/")
public class AdminController {
	@Autowired
	SessionFactory factory;
	public Integer code;
	
	@Autowired JavaMailSender mailer;
	@RequestMapping("index")
	public String Login()
	{
//		System.out.println(this.checkAccount("trandat86", "1234567"));
		return "login/index";
	}
	@RequestMapping(value="index",method = RequestMethod.POST)
	public String handlerLogin(HttpServletRequest request,ModelMap model,HttpServletResponse response, HttpSession session)
	{
		String MACT  = checkAccount(request.getParameter("TAIKHOAN"),request.getParameter("PASSWORD"));
		if(request.getParameter("TAIKHOAN").trim().length() == 0 ||request.getParameter("PASSWORD").trim().length() == 0 )
		{
			model.addAttribute("message", "Tên đăng nhập hoặc mật khẩu không được bỏ trống !");
			return "login/index";
		}
		if(MACT.equalsIgnoreCase("xyz") == false)
		{
			String hoTen =  this.getHoTen(MACT);
			try {
				session = request.getSession();
				session.setAttribute("mact", MACT);
				session.setAttribute("hoten",hoTen);
				String url = "/home/"+"index.htm";
	            response.sendRedirect(request.getContextPath() + url);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		model.addAttribute("message", "Sai tên đăng nhập hoặc mật khẩu !");
		return "login/index";
		
	}
	public String getHoTen(String mact)
	{
		Session session = factory.getCurrentSession();
		String hql = "FROM ChuTroEntity WHERE MACT = :mact";
		Query query = session.createQuery(hql);
		query.setParameter("mact", mact);
		List<ChuTroEntity> list = query.list();
		String hoTen = list.get(0).getHO()+ " "+list.get(0).getTEN();
		return hoTen;
	}
	public String checkAccount(String username,String password)
	{
		Session session = factory.getCurrentSession();
		String hql ="SELECT ct.MACT FROM ChuTroEntity ct WHERE (ct.TAIKHOAN= :username) AND (ct.PASSWORD = :pw) ";
		Query query = session.createQuery(hql);
		
		query.setParameter("username", username);
		query.setParameter("pw", password);
		List<String> results = (List<String>)query.list();
		String MACT;
		if(results.size() != 0)
		{
			MACT = results.get(0);
			return MACT;
		}
		return "xyz";
	}
	
	@RequestMapping("logout")
    public String Logout(HttpSession session,HttpServletRequest request) {
		System.out.println("log out");
		session.removeAttribute("mact");
    	return "redirect:/login/index.htm";
    }
	
	@RequestMapping(value="register",method=RequestMethod.GET)
	public String register(@ModelAttribute("user") ChuTroEntity user,ModelMap model)
	{
		return "login/register";
	}
	
	// ADD Chu tro
	public Integer insertChuTro(ChuTroEntity ct) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(ct);
			t.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
	public int checkEmail(String email)
	{
		Session session = factory.getCurrentSession();
		String hql = "FROM ChuTroEntity WHERE EMAIL = :email";
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		List<ChuTroEntity> list = query.list();
		if(list.size() != 0) return 0;
		return 1;
	}
	@RequestMapping(value="register",method = RequestMethod.POST)
	public String registerHandler(@ModelAttribute("user") ChuTroEntity user,ModelMap model,RedirectAttributes redirectAttributes,HttpServletRequest req)
	{
		if(this.checkEmail(user.getEMAIL()) != 1)
		{
			model.addAttribute("messError","Gmail này đã được sử dụng !");
			return "login/register";
		}
		String uuid = UUID.randomUUID().toString();
		String confirmPass = req.getParameter("pwcf");
		if(confirmPass.equals(user.getPASSWORD()) && !confirmPass.isEmpty())
		{
			user.setMACT(uuid);
			user.setTAIKHOAN(user.getEMAIL());
			Integer temp = this.insertChuTro(user);
			if(temp != 0)
			{	
				model.addAttribute("message","Đăng ký tài khoản thành công");
				return "login/success";
			}
		}else
		{
			model.addAttribute("messError","Mật khẩu xác nhận không khớp hoặc bỏ trống !");
		}
		return "login/register";
	}
	
	public void sendMail(String toMail,String sjMail,String ThongBao,String code)
	{
//		int code = (int) Math.floor(((Math.random() * 899999) + 100000));
		 try {
	            MimeMessage mail = mailer.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(mail, true, "utf-8");
	            helper.setFrom("NhaTroDTL","NhaTroDTL");
	            helper.setTo(toMail);
	            helper.setReplyTo("NhaTroDTL","NhaTroDTL");
	            helper.setSubject(sjMail);
	            
	            String maxacthuc = "Chào "+ "Bạn "+". Mật khẩu của bạn là : "+code+" Lần sau đừng để quên nhé !"+". Chúc bạn một ngày vui vẻ <3";
	            helper.setText(ThongBao, true);
	            mailer.send(mail);
	        }
	        catch (Exception ex){
	        	System.out.println(ex.getMessage());
	            throw new RuntimeException(ex);
	        }
		
	}
	
	// Authentication
	@RequestMapping("forget")
	public String authenticator()
	{		
		return "login/forget";
	
	}
	
	@RequestMapping(value="forget",method=RequestMethod.POST)
	public RedirectView  authenticatorHandler(RedirectAttributes redirectAttributes,HttpServletRequest request,ModelMap model)
	{		
		String gmail = request.getParameter("gmail");
		System.out.println(gmail);
		Session session = factory.getCurrentSession();
		String hql = "FROM ChuTroEntity u WHERE u.EMAIL = :gmail";
		Query query =  session.createQuery(hql);
		query.setParameter("gmail", gmail);
		List<ChuTroEntity> ct = query.list();
		if(ct.size() != 0)
		{
			String thongbao="Chào "+ct.get(0).getHO()+" "+ct.get(0).getTEN()+ ". Mật khẩu của bạn là : "+ct.get(0).getPASSWORD()+" Lần sau đừng để quên nhé !"+". Chúc bạn một ngày vui vẻ <3";
			sendMail(gmail,"Xác nhận mật khẩu", thongbao, ct.get(0).getPASSWORD());
			System.out.println(ct.get(0).getPASSWORD());
			redirectAttributes.addFlashAttribute("mess","Đã gửi mail chứa mật khẩu !");
			
		}else
		{
			redirectAttributes.addFlashAttribute("messError","Email bạn nhập không tồn tại !");
		}
	    RedirectView rv = new RedirectView("/login/forget.htm", true);
		return rv;
	}
	
//	@RequestMapping("authenticator")
//	public void authenticator(@ModelAttribute("user") ChuTroEntity user)
//	{
//		
//		
//		System.out.println("check register");
//		
//		Integer temp = this.insertChuTro(user);
//		if(temp != 0)
//		{	
//			return "redirect:/login/authenticator.htm";
//		}
//		return "login/register";
//	
//	}
//	@RequestMapping(value="authenticator",method=RequestMethod.POST)
//	public String authenticatorHandler(@ModelAttribute("user") ChuTroEntity user,HttpServletRequest request ,ModelMap model)
//	{
//		
//		String codeXt = request.getParameter("codeXt");
//		System.out.println("check authen posst"+ codeXt+"  "+code);
//		System.out.println("test authentication : "+user.getEMAIL());
//		if(code == Integer.parseInt(codeXt))
//		{
//			System.out.println("OKE");
//			Integer temp = this.insertChuTro(user);
//			if(temp != 0)
//			{	
//				model.addAttribute("message","Đăng kí thành công !");
//				return "redirect:/login/index.htm";
//			}
//		}
//		return "login/authenticator";
//	}
	
	//end Authentication
}
