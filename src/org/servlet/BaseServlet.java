package org.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		Class<? extends BaseServlet> clazz = this.getClass();
		try {
			Method declaredMethod = clazz.getDeclaredMethod(method, HttpServletRequest.class, HttpServletResponse.class);
			try {
				declaredMethod.invoke(this, request, response);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
