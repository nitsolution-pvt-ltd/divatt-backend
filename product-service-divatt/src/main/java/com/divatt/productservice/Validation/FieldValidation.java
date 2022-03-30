package com.divatt.productservice.Validation;

import java.util.Date;
import java.util.regex.Pattern;

public class FieldValidation {

	public boolean isEmpty(String string) {
		if (string == null) {

			return false;
		} else {
			if (string.isEmpty() | string.equals(null)) {
				return false;
			} else {
				return true;
			}
		}
	}

	public boolean isEmpty(Date string) {
		if (string == null | string.equals("")) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isEmpty(int val) {
		if (val <= 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isEmpty(float val) {
		if (val <= 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isEmpty(Long val) {
		if (val <= 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isEmail(String email) {
		if (isEmpty(email)) {
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
					+ "A-Z]{2,7}$";

			Pattern pat = Pattern.compile(emailRegex);
			if (email == null)
				return false;
			return pat.matcher(email).matches();
		} else {
			return false;
		}

	}

	public boolean isNumber(String number) {
		if (isEmpty(number)) {
			try {
				Double num = Double.parseDouble(number);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}

	}

	public boolean isMobile(String number) {
		if (isEmpty(number)) {
			if (isNumber(number)) {
				String regexStr = "^[0-9]{10}$";
				if (number.matches(regexStr))
					return true;
				else
					return false;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	public boolean isPin(String number) {
		if (isNumber(number)) {
			String regexStr = "^[0-9]{6}$";
			if (number.matches(regexStr))
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}
