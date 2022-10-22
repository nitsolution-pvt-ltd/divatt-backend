package com.divatt.user.entity.measurement;

public class MeasurementSize {

	private int cm;
	private int inch;

	public MeasurementSize() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MeasurementSize(int cm, int inch) {
		super();
		this.cm = cm;
		this.inch = inch;
	}

	public int getCm() {
		return cm;
	}

	public void setCm(int cm) {
		this.cm = cm;
	}

	public int getInch() {
		return inch;
	}

	public void setInch(int inch) {
		this.inch = inch;
	}

	@Override
	public String toString() {
		return "MeasurementSize [cm=" + cm + ", inch=" + inch + "]";
	}

}
