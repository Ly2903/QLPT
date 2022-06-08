package qlpt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String showLogin()
	{
		return "login/index";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(HttpServletRequest req)
	{
		String user = req.getParameter("user");
		String pass = req.getParameter("password");
		
		if(user.equals("trandat68") && pass.equals("123"))
		{
			/* return "redirect:../"; */
			return "home/index";
		}
		return "login/index";
	}
}
