package com.divatt.designer.entity;

public class WomenSizeMeasurements {
	private SizeWomen xs;
	private SizeWomen s;
	private SizeWomen m;
	private SizeWomen l;
	private SizeWomen xl;
	private SizeWomen xxl;
	private SizeWomen xl3;
	private SizeWomen xl4;
	private SizeWomen xl5;
	private SizeWomen xl6;

	public WomenSizeMeasurements() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WomenSizeMeasurements(SizeWomen xs, SizeWomen s, SizeWomen m, SizeWomen l,
			SizeWomen xl, SizeWomen xxl, SizeWomen xl3, SizeWomen xl4, SizeWomen xl5,
			SizeWomen xl6) {
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

	public SizeWomen getXs() {
		return xs;
	}

	public void setXs(SizeWomen xs) {
		this.xs = xs;
	}

	public SizeWomen getS() {
		return s;
	}

	public void setS(SizeWomen s) {
		this.s = s;
	}

	public SizeWomen getM() {
		return m;
	}

	public void setM(SizeWomen m) {
		this.m = m;
	}

	public SizeWomen getL() {
		return l;
	}

	public void setL(SizeWomen l) {
		this.l = l;
	}

	public SizeWomen getXl() {
		return xl;
	}

	public void setXl(SizeWomen xl) {
		this.xl = xl;
	}

	public SizeWomen getXxl() {
		return xxl;
	}

	public void setXxl(SizeWomen xxl) {
		this.xxl = xxl;
	}

	public SizeWomen getXl3() {
		return xl3;
	}

	public void setXl3(SizeWomen xl3) {
		this.xl3 = xl3;
	}

	public SizeWomen getXl4() {
		return xl4;
	}

	public void setXl4(SizeWomen xl4) {
		this.xl4 = xl4;
	}

	public SizeWomen getXl5() {
		return xl5;
	}

	public void setXl5(SizeWomen xl5) {
		this.xl5 = xl5;
	}

	public SizeWomen getXl6() {
		return xl6;
	}

	public void setXl6(SizeWomen xl6) {
		this.xl6 = xl6;
	}

	@Override
	public String toString() {
		return "WomenSizeMeasurements [xs=" + xs + ", s=" + s + ", m=" + m + ", l=" + l + ", xl=" + xl + ", xxl=" + xxl
				+ ", xl3=" + xl3 + ", xl4=" + xl4 + ", xl5=" + xl5 + ", xl6=" + xl6 + "]";
	}

}
