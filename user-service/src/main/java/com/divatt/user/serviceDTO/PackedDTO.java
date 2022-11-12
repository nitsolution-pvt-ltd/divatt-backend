package com.divatt.user.serviceDTO;

public class PackedDTO {

	private Boolean packedInDivattCover;
	private Boolean recordedPackageVideo;

	public PackedDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PackedDTO(Boolean packedInDivattCover, Boolean recordedPackageVideo) {
		super();
		this.packedInDivattCover = packedInDivattCover;
		this.recordedPackageVideo = recordedPackageVideo;
	}

	public Boolean getPackedInDivattCover() {
		return packedInDivattCover;
	}

	public void setPackedInDivattCover(Boolean packedInDivattCover) {
		this.packedInDivattCover = packedInDivattCover;
	}

	public Boolean getRecordedPackageVideo() {
		return recordedPackageVideo;
	}

	public void setRecordedPackageVideo(Boolean recordedPackageVideo) {
		this.recordedPackageVideo = recordedPackageVideo;
	}

	@Override
	public String toString() {
		return "PackedDTO [packedInDivattCover=" + packedInDivattCover + ", recordedPackageVideo="
				+ recordedPackageVideo + "]";
	}

}
