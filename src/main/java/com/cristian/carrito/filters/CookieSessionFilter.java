package com.cristian.carrito.filters;

import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.net.http.HttpRequest;

import com.cristian.carrito.controllers.ClienteDao;
import com.cristian.carrito.models.Cliente;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(filterName = "CookieSessionFilter", urlPatterns = {"/*"})
public class CookieSessionFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) res;

        HttpSession session = httpRequest.getSession(true);
        session.setMaxInactiveInterval(120);

        Cookie[] cookies = httpRequest.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userEmail")) {          	
                	ClienteDao clienteDao = new ClienteDao();
                	Cliente cliente = clienteDao.getClientByEmail(cookie.getValue());
                    if (cookie.getValue().equals(cliente.getEmail())) {
                    	session.setAttribute("login", "true");
                    }
                }
            }
        }
        chain.doFilter(req, res);
	}

}
