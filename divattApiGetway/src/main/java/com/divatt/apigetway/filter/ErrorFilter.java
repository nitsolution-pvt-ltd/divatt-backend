package com.divatt.apigetway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ErrorFilter extends ZuulFilter {
	private static Logger LOGGER = LoggerFactory.getLogger(ErrorFilter.class);
	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		// TODO Auto-generated method stub
		LOGGER.info("going through error filter");
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "error";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

}
