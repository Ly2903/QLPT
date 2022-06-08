package qlpt.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import qlpt.controller.RoomController;

public class RoomInterceptor  extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession ss = request.getSession();
		RoomController rc = new RoomController();
		if(rc.getMANT().isEmpty())
		{
			response.sendRedirect(request.getContextPath() + "/room/index.htm");
		}
		return super.preHandle(request, response, handler);
	}
}
