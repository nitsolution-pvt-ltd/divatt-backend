package com.divatt.user.entity.measurement;

import org.springframework.data.annotation.Id;

public class MeasurementMenandKids {
	@Id
	private Integer id;
	private Integer KidsandMenNeck;
	private Double KidsandMenShoulder;
	private Integer KidsandMenChest;
	private Double KidsandMenBacksideneckpointtoWaist;
	private Double KidsandMenNapetoWaist;
	private Double KidsandMenArmscyeDepth;
	private Double KidsandMenArmhole;
	private Double KidsandMenBiceps;
	private Double KidsandMenElbow;
	private Double KidsandMenWrist;
	private Double KidsandMenSleeveLength;
	private Double KidsandMenCrotchDepth;
	private Double KidsandMenCrotchLength;
	private Double KidsandMenWaist;
	private Double KidsandMenHip;
	private Double KidsandMenLowWaist;
	private Double KidsandMenWaistToHip;
	private Double KidsandMenWaisttoKnee;
	private Double KidsandMenKneetoAnkle;
	private Double KidsandMenThigh;
	private Double KidsandMenKnee;
    private Double KidsandMenCalfCircumferenece;
    private Double KidsandMenHighAnkle;
    private Double KidsandMenAnkle;
    private Double KidsandMenWaistToFloor;
    
    
    
	public MeasurementMenandKids() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String toString() {
		return "MeasurementMenandKids [id=" + id + ", KidsandMenNeck=" + KidsandMenNeck + ", KidsandMenShoulder="
				+ KidsandMenShoulder + ", KidsandMenChest=" + KidsandMenChest + ", KidsandMenBacksideneckpointtoWaist="
				+ KidsandMenBacksideneckpointtoWaist + ", KidsandMenNapetoWaist=" + KidsandMenNapetoWaist
				+ ", KidsandMenArmscyeDepth=" + KidsandMenArmscyeDepth + ", KidsandMenArmhole=" + KidsandMenArmhole
				+ ", KidsandMenBiceps=" + KidsandMenBiceps + ", KidsandMenElbow=" + KidsandMenElbow
				+ ", KidsandMenWrist=" + KidsandMenWrist + ", KidsandMenSleeveLength=" + KidsandMenSleeveLength
				+ ", KidsandMenCrotchDepth=" + KidsandMenCrotchDepth + ", KidsandMenCrotchLength="
				+ KidsandMenCrotchLength + ", KidsandMenWaist=" + KidsandMenWaist + ", KidsandMenHip=" + KidsandMenHip
				+ ", KidsandMenLowWaist=" + KidsandMenLowWaist + ", KidsandMenWaistToHip=" + KidsandMenWaistToHip
				+ ", KidsandMenWaisttoKnee=" + KidsandMenWaisttoKnee + ", KidsandMenKneetoAnkle="
				+ KidsandMenKneetoAnkle + ", KidsandMenThigh=" + KidsandMenThigh + ", KidsandMenKnee=" + KidsandMenKnee
				+ ", KidsandMenCalfCircumferenece=" + KidsandMenCalfCircumferenece + ", KidsandMenHighAnkle="
				+ KidsandMenHighAnkle + ", KidsandMenAnkle=" + KidsandMenAnkle + ", KidsandMenWaistToFloor="
				+ KidsandMenWaistToFloor + "]";
	}


	public MeasurementMenandKids(Integer id, Integer kidsandMenNeck, Double kidsandMenShoulder, Integer kidsandMenChest,
			Double kidsandMenBacksideneckpointtoWaist, Double kidsandMenNapetoWaist, Double kidsandMenArmscyeDepth,
			Double kidsandMenArmhole, Double kidsandMenBiceps, Double kidsandMenElbow, Double kidsandMenWrist,
			Double kidsandMenSleeveLength, Double kidsandMenCrotchDepth, Double kidsandMenCrotchLength,
			Double kidsandMenWaist, Double kidsandMenHip, Double kidsandMenLowWaist, Double kidsandMenWaistToHip,
			Double kidsandMenWaisttoKnee, Double kidsandMenKneetoAnkle, Double kidsandMenThigh, Double kidsandMenKnee,
			Double kidsandMenCalfCircumferenece, Double kidsandMenHighAnkle, Double kidsandMenAnkle,
			Double kidsandMenWaistToFloor) {
		super();
		this.id = id;
		KidsandMenNeck = kidsandMenNeck;
		KidsandMenShoulder = kidsandMenShoulder;
		KidsandMenChest = kidsandMenChest;
		KidsandMenBacksideneckpointtoWaist = kidsandMenBacksideneckpointtoWaist;
		KidsandMenNapetoWaist = kidsandMenNapetoWaist;
		KidsandMenArmscyeDepth = kidsandMenArmscyeDepth;
		KidsandMenArmhole = kidsandMenArmhole;
		KidsandMenBiceps = kidsandMenBiceps;
		KidsandMenElbow = kidsandMenElbow;
		KidsandMenWrist = kidsandMenWrist;
		KidsandMenSleeveLength = kidsandMenSleeveLength;
		KidsandMenCrotchDepth = kidsandMenCrotchDepth;
		KidsandMenCrotchLength = kidsandMenCrotchLength;
		KidsandMenWaist = kidsandMenWaist;
		KidsandMenHip = kidsandMenHip;
		KidsandMenLowWaist = kidsandMenLowWaist;
		KidsandMenWaistToHip = kidsandMenWaistToHip;
		KidsandMenWaisttoKnee = kidsandMenWaisttoKnee;
		KidsandMenKneetoAnkle = kidsandMenKneetoAnkle;
		KidsandMenThigh = kidsandMenThigh;
		KidsandMenKnee = kidsandMenKnee;
		KidsandMenCalfCircumferenece = kidsandMenCalfCircumferenece;
		KidsandMenHighAnkle = kidsandMenHighAnkle;
		KidsandMenAnkle = kidsandMenAnkle;
		KidsandMenWaistToFloor = kidsandMenWaistToFloor;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getKidsandMenNeck() {
		return KidsandMenNeck;
	}
	public void setKidsandMenNeck(Integer kidsandMenNeck) {
		KidsandMenNeck = kidsandMenNeck;
	}
	public Double getKidsandMenShoulder() {
		return KidsandMenShoulder;
	}
	public void setKidsandMenShoulder(Double kidsandMenShoulder) {
		KidsandMenShoulder = kidsandMenShoulder;
	}
	public Integer getKidsandMenChest() {
		return KidsandMenChest;
	}
	public void setKidsandMenChest(Integer kidsandMenChest) {
		KidsandMenChest = kidsandMenChest;
	}
	public Double getKidsandMenBacksideneckpointtoWaist() {
		return KidsandMenBacksideneckpointtoWaist;
	}
	public void setKidsandMenBacksideneckpointtoWaist(Double kidsandMenBacksideneckpointtoWaist) {
		KidsandMenBacksideneckpointtoWaist = kidsandMenBacksideneckpointtoWaist;
	}
	public Double getKidsandMenNapetoWaist() {
		return KidsandMenNapetoWaist;
	}
	public void setKidsandMenNapetoWaist(Double kidsandMenNapetoWaist) {
		KidsandMenNapetoWaist = kidsandMenNapetoWaist;
	}
	public Double getKidsandMenArmscyeDepth() {
		return KidsandMenArmscyeDepth;
	}
	public void setKidsandMenArmscyeDepth(Double kidsandMenArmscyeDepth) {
		KidsandMenArmscyeDepth = kidsandMenArmscyeDepth;
	}
	public Double getKidsandMenArmhole() {
		return KidsandMenArmhole;
	}
	public void setKidsandMenArmhole(Double kidsandMenArmhole) {
		KidsandMenArmhole = kidsandMenArmhole;
	}
	public Double getKidsandMenBiceps() {
		return KidsandMenBiceps;
	}
	public void setKidsandMenBiceps(Double kidsandMenBiceps) {
		KidsandMenBiceps = kidsandMenBiceps;
	}
	public Double getKidsandMenElbow() {
		return KidsandMenElbow;
	}
	public void setKidsandMenElbow(Double kidsandMenElbow) {
		KidsandMenElbow = kidsandMenElbow;
	}
	public Double getKidsandMenWrist() {
		return KidsandMenWrist;
	}
	public void setKidsandMenWrist(Double kidsandMenWrist) {
		KidsandMenWrist = kidsandMenWrist;
	}
	public Double getKidsandMenSleeveLength() {
		return KidsandMenSleeveLength;
	}
	public void setKidsandMenSleeveLength(Double kidsandMenSleeveLength) {
		KidsandMenSleeveLength = kidsandMenSleeveLength;
	}
	public Double getKidsandMenCrotchDepth() {
		return KidsandMenCrotchDepth;
	}
	public void setKidsandMenCrotchDepth(Double kidsandMenCrotchDepth) {
		KidsandMenCrotchDepth = kidsandMenCrotchDepth;
	}
	public Double getKidsandMenCrotchLength() {
		return KidsandMenCrotchLength;
	}
	public void setKidsandMenCrotchLength(Double kidsandMenCrotchLength) {
		KidsandMenCrotchLength = kidsandMenCrotchLength;
	}
	public Double getKidsandMenWaist() {
		return KidsandMenWaist;
	}
	public void setKidsandMenWaist(Double kidsandMenWaist) {
		KidsandMenWaist = kidsandMenWaist;
	}
	public Double getKidsandMenHip() {
		return KidsandMenHip;
	}
	public void setKidsandMenHip(Double kidsandMenHip) {
		KidsandMenHip = kidsandMenHip;
	}
	public Double getKidsandMenLowWaist() {
		return KidsandMenLowWaist;
	}
	public void setKidsandMenLowWaist(Double kidsandMenLowWaist) {
		KidsandMenLowWaist = kidsandMenLowWaist;
	}
	public Double getKidsandMenWaistToHip() {
		return KidsandMenWaistToHip;
	}
	public void setKidsandMenWaistToHip(Double kidsandMenWaistToHip) {
		KidsandMenWaistToHip = kidsandMenWaistToHip;
	}
	public Double getKidsandMenWaisttoKnee() {
		return KidsandMenWaisttoKnee;
	}
	public void setKidsandMenWaisttoKnee(Double kidsandMenWaisttoKnee) {
		KidsandMenWaisttoKnee = kidsandMenWaisttoKnee;
	}
	public Double getKidsandMenKneetoAnkle() {
		return KidsandMenKneetoAnkle;
	}
	public void setKidsandMenKneetoAnkle(Double kidsandMenKneetoAnkle) {
		KidsandMenKneetoAnkle = kidsandMenKneetoAnkle;
	}
	public Double getKidsandMenThigh() {
		return KidsandMenThigh;
	}
	public void setKidsandMenThigh(Double kidsandMenThigh) {
		KidsandMenThigh = kidsandMenThigh;
	}
	public Double getKidsandMenKnee() {
		return KidsandMenKnee;
	}
	public void setKidsandMenKnee(Double kidsandMenKnee) {
		KidsandMenKnee = kidsandMenKnee;
	}
	public Double getKidsandMenCalfCircumferenece() {
		return KidsandMenCalfCircumferenece;
	}
	public void setKidsandMenCalfCircumferenece(Double kidsandMenCalfCircumferenece) {
		KidsandMenCalfCircumferenece = kidsandMenCalfCircumferenece;
	}
	public Double getKidsandMenHighAnkle() {
		return KidsandMenHighAnkle;
	}
	public void setKidsandMenHighAnkle(Double kidsandMenHighAnkle) {
		KidsandMenHighAnkle = kidsandMenHighAnkle;
	}
	public Double getKidsandMenAnkle() {
		return KidsandMenAnkle;
	}
	public void setKidsandMenAnkle(Double kidsandMenAnkle) {
		KidsandMenAnkle = kidsandMenAnkle;
	}
	public Double getKidsandMenWaistToFloor() {
		return KidsandMenWaistToFloor;
	}
	public void setKidsandMenWaistToFloor(Double kidsandMenWaistToFloor) {
		KidsandMenWaistToFloor = kidsandMenWaistToFloor;
	}
}
