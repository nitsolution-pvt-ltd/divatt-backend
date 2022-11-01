package com.divatt.designer.entity;

import java.util.List;

import org.json.simple.JSONObject;

public class MenandKidsMeasurements {

	private Size xs;
	private Size s;
	private Size m;
	private Size l;
	private Size xl;
	private Size xxl;
	private Size xl3;
	private Size xl4;
	private Size xl5;
	private Size xl6;

	public MenandKidsMeasurements() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MenandKidsMeasurements(Size xs, Size s, Size m, Size l, Size xl, Size xxl, Size xl3, Size xl4, Size xl5, Size xl6) {
		super();
		this.xs = xs;
		this.s = s;
		this.m = m;
		this.l = l;
		this.xl = xl;
		this.xxl = xxl;
		this.xl3 = xl3;
		this.xl4 = xl4;
		this.xl5 = xl5;
		this.xl6 = xl6;
	}

	public Size getXs() {
		return xs;
	}

	public void setXs(Size xs) {
		this.xs = xs;
	}

	public Size getS() {
		return s;
	}

	public void setS(Size s) {
		this.s = s;
	}

	public Size getM() {
		return m;
	}

	public void setM(Size m) {
		this.m = m;
	}

	public Size getL() {
		return l;
	}

	public void setL(Size l) {
		this.l = l;
	}

	public Size getXl() {
		return xl;
	}

	public void setXl(Size xl) {
		this.xl = xl;
	}

	public Size getXxl() {
		return xxl;
	}

	public void setXxl(Size xxl) {
		this.xxl = xxl;
	}

	public Size getXl3() {
		return xl3;
	}

	public void setXl3(Size xl3) {
		this.xl3 = xl3;
	}

	public Size getXl4() {
		return xl4;
	}

	public void setXl4(Size xl4) {
		this.xl4 = xl4;
	}

	public Size getXl5() {
		return xl5;
	}

	public void setXl5(Size xl5) {
		this.xl5 = xl5;
	}

	public Size getXl6() {
		return xl6;
	}

	public void setXl6(Size xl6) {
		this.xl6 = xl6;
	}

	@Override
	public String toString() {
		return "Measurements [xs=" + xs + ", s=" + s + ", m=" + m + ", l=" + l + ", xl=" + xl + ", xxl=" + xxl
				+ ", xl3=" + xl3 + ", xl4=" + xl4 + ", xl5=" + xl5 + ", xl6=" + xl6 + "]";
	}

}
