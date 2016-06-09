package cn.shandian.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {
	protected FilterConfig filterConfig = null;
	private String rediectURL = null;
	private Set<String> notCheckURLList = new HashSet<String>();
	private String sessionKey = null;

	@Override
	public void destroy() {
		notCheckURLList.clear();
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse reponse = (HttpServletResponse)servletResponse;
		HttpSession session = request.getSession();
		if(session==null){
			System.out.println("session == null");
			filterChain.doFilter(request, reponse);
			return;
		}
		if((!checkRequestURIIntNotFilterList(request))&&session.getAttribute(sessionKey)==null){
			System.out.println("----"+request.getContextPath()+rediectURL);
			reponse.sendRedirect(request.getContextPath()+rediectURL);
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
		

	}

	private boolean checkRequestURIIntNotFilterList(HttpServletRequest request) {
		String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());
		System.out.println("request.getServletPath():"+request.getServletPath());
		System.out.println("request.getPathInfo():"+request.getPathInfo());
		System.out.println("uri:"+uri);
		String temp = request.getRequestURI();
		System.out.println("request.getRequestURI()"+request.getRequestURI());
		temp = temp.substring(request.getContextPath().length()+1);
		return notCheckURLList.contains(uri);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		rediectURL = filterConfig.getInitParameter("redirectURL");
		sessionKey = filterConfig.getInitParameter("checkSessionKey");
		System.out.println("rediectURL:"+rediectURL);
		System.out.println("sessionKey:"+sessionKey);
		
		String notCheckURLListStr = filterConfig.getInitParameter("notCheckURLList");
		System.out.println("notCheckURLListStr£º"+notCheckURLListStr);
		if (notCheckURLListStr != null) {
			String[] params = notCheckURLListStr.split(",");
			for (int i = 0; i < params.length; i++) {
				notCheckURLList.add(params[i].trim());
			}
		}

	}

}
