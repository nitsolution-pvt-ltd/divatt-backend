package com.divatt.admin.entity;

public class FileUploadResponse {
	
	private String path;
	
	private String fileName;

	public FileUploadResponse(String path, String fileName) {
		super();
		this.path = path;
		this.fileName = fileName;
	}

	public FileUploadResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
