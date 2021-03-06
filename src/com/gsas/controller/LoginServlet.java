package com.gsas.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gsas.exception.AuthenticationException;
import com.gsas.model.CitizenVO;
import com.gsas.service.CitizenService;
import com.gsas.utility.CitizenFactory;
import com.gsas.utility.LayerType;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CitizenService citizenService = (CitizenService) CitizenFactory.getInstance(LayerType.SERVICE);
		RequestDispatcher rd = null;
		try {
			CitizenVO citizenVO = citizenService.Authenticate(request.getParameter("username"), request.getParameter("password"));
			HttpSession session = request.getSession();
			session.setAttribute("citizenVO", citizenVO);
			
			
			rd = request.getRequestDispatcher("viewAllSchemes.jsp");
			rd.forward(request, response);
			
		} catch (AuthenticationException e) {
			rd = request.getRequestDispatcher("loginFailure.jsp");
			request.setAttribute("err", e.getMessage());
			rd.forward(request, response);
		}
	}

}
