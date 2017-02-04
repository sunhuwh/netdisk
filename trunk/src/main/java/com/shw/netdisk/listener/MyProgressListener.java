package com.shw.netdisk.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

import com.shw.netdisk.entity.FileUploadStatus;

@WebListener
public class MyProgressListener implements ProgressListener{	
	private HttpSession session;

    public MyProgressListener(HttpServletRequest req) {
        session=req.getSession();
        FileUploadStatus status = new FileUploadStatus();
        session.setAttribute("status", status);
    }

     /*pBytesRead  到目前为止读取文件的比特数
     * pContentLength 文件总大小
     * pItems 目前正在读取第几个文件
     * 只要在session中实时保存文件上传的状态（这里我用fileUploadStatus类来封装）
     */
	@Override
	public void update(long pBytesRead, long pContentLength, int pItems) {
		FileUploadStatus status = (FileUploadStatus) session.getAttribute("status");
        status.setPBytesRead(pBytesRead);
        status.setPContentLength(pContentLength);
        status.setPItems(pItems);
        System.out.println("pBytesRead:"+status.getPBytesRead()+"--pContentLength:"+status.getPContentLength()+"--pItems:"+status.getPItems());
	}

}
