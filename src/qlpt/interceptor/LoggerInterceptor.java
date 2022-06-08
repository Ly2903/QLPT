package qlpt.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggerInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession ss = request.getSession();
		if(ss.getAttribute("mact") == null)
		{
			response.sendRedirect(request.getContextPath() + "/login/index.htm");
			return false;
		}
		// TODO Auto-generated method stub
		return true;
	}
	
}
