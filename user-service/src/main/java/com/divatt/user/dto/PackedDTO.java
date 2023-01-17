package com.divatt.user.dto;

public class PackedDTO {

	private Boolean packedCovered;
	private Boolean packingVideo;

	public PackedDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PackedDTO(Boolean packedCovered, Boolean packingVideo) {
		super();
		this.packedCovered = packedCovered;
		this.packingVideo = packingVideo;
	}

	public Boolean getPackedCovered() {
		return packedCovered;
	}

	public void setPackedCovered(Boolean packedCovered) {
		this.packedCovered = packedCovered;
	}

	public Boolean getPackingVideo() {
		return packingVideo;
	}

	public void setPackingVideo(Boolean packingVideo) {
		this.packingVideo = packingVideo;
	}

	@Override
	public String toString() {
		return "PackedDTO [packedCovered=" + packedCovered + ", packingVideo=" + packingVideo + "]";
	}

}
