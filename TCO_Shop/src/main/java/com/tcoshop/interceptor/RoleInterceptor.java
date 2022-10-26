package com.tcoshop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tcoshop.service.RoleService;
@Component
public class RoleInterceptor implements HandlerInterceptor{
    @Autowired
    RoleService roleService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndview) throws Exception {
		request.setAttribute("roles", roleService.getRoles());
	}
}
