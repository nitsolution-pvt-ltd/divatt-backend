package com.divatt.apigetway.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PreFilter extends ZuulFilter {
	
	private static Logger LOGGER = LoggerFactory.getLogger(PreFilter.class);

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		// TODO Auto-generated method stub
		LOGGER.info("going through Pre filter");
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest httpServletRequest = context.getRequest();
		LOGGER.info(String.format("%s request to %s", httpServletRequest.getMethod(), httpServletRequest.getRequestURL().toString()));
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

}
