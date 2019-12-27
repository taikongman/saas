package com.saas.api.common.config;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.util.StringUtils;
import com.saas.api.common.config.CorsConfig;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*", filterName = "corsFilter")
public class CorsFilter implements Filter{

	@Autowired
	private CorsConfig corsConfig;

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest)req;
		response.setHeader("Access-Control-Allow-Origin", corsConfig.getAllowedOrigins());
		
		String orgin = request.getHeader("Origin");
		if(!StringUtils.isEmpty(orgin)) {
			if(corsConfig.getAllowedOrigins().indexOf(orgin) >= 0) {
				response.setHeader("Access-Control-Allow-Origin", orgin);
			}
		}
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", corsConfig.getAllowedMethods());
		response.setHeader("Access-Control-Allow-Headers", corsConfig.getAllowedHeaders());
		
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
}
