package com.divatt.apigetway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

public class RouteFilter extends ZuulFilter {
	
	private static Logger LOGGER = LoggerFactory.getLogger(RouteFilter.class);
	@Override
	public Object run() throws ZuulException {
		// TODO Auto-generated method stub
		
		LOGGER.info("going through router filter");
		return null;
	}

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "router";
	}
}
