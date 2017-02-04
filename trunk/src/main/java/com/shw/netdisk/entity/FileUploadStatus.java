package com.shw.netdisk.entity;

public class FileUploadStatus {
	
	/**
	 * 到目前为止读取文件的比特数
	 */
	private long pBytesRead = 0L;
	
	
	/**
	 * 文件总大小
	 */
	private long pContentLength = 0L;
	
	/**
	 * 目前正在读取第几个文件
	 */
	private int pItems;

	public FileUploadStatus(){
		pBytesRead = 0L;
		pContentLength = 0L;
	}

	public long getPBytesRead() {
		return pBytesRead;
	}

	public void setPBytesRead(long bytesRead) {
		pBytesRead = bytesRead;
	}

	public long getPContentLength() {
		return pContentLength;
	}

	public void setPContentLength(long contentLength) {
		pContentLength = contentLength;
	}

	public int getPItems() {
		return pItems;
	}

	public void setPItems(int items) {
		pItems = items;
	}
}
