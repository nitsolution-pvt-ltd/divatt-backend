package com.divatt.admin.utility;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommonUtility {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtility.class);

	public static double duoble(float f) {

		DecimalFormat df = new DecimalFormat("0.00");
		Double valueOf = Double.valueOf(df.format(f));
		return valueOf;
 
	}

}
