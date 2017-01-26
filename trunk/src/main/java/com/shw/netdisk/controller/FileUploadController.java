package com.shw.netdisk.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shw.netdisk.listener.MyProgressListener;
import com.shw.netdisk.storage.FileUploadStatus;
import com.shw.netdisk.storage.StorageFileNotFoundException;
import com.shw.netdisk.storage.StorageService;

@Controller
public class FileUploadController implements ControllerHelper{

	@Autowired
	private StorageService storageService;

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @PostMapping("/upload")
    public void handleFileUpload(/*@RequestParam("file") MultipartFile file, */RedirectAttributes redirectAttributes,
    		HttpServletRequest request, HttpServletResponse response) {
    	DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(2048*1024);
    	MyProgressListener getBarListener = new MyProgressListener(request);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setProgressListener(getBarListener);
        try {
        	RequestContext requestContext = new ServletRequestContext(request);
			List formList = upload.parseRequest(requestContext);
			Iterator<Object> formItem = formList.iterator();
			// 将进度监听器加载进去
			while (formItem.hasNext()) {
				FileItem item = (FileItem) formItem.next();
				if (item.isFormField()) {
					System.out.println("Field Name:" + item.getFieldName());
				} else {
					String fileName = item.getName().substring(item.getName().lastIndexOf("\\")+1);
					File file = new File("e:\\temp"
							+ "\\" + fileName);
					System.out.println("e:\\temp"
							+ "\\" + fileName);
					OutputStream out = item.getOutputStream();
					InputStream in = item.getInputStream();
					request.getSession().setAttribute("outPutStream", out);
					request.getSession().setAttribute("inPutStream", in);
					item.write(file);
				}
			}
		} catch (FileUploadException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	
    	
      /*  Path targetPath = storageService.store(file);
        
        
        //long fileSize = file.getSize();
        //long hasBeenWritedSize = getByPath(targetPath);
        
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/";*/
    }
    
    @GetMapping("/upload_progress")
    public int progress(HttpServletRequest request, HttpServletResponse response){
    	HttpSession session = request.getSession();
		FileUploadStatus status = (FileUploadStatus) session.getAttribute("status");
		response.reset();
//			response.getWriter().write("{\"pBytesRead\":"
//					+status.getPBytesRead()+",\"pContentLength\":"+status.getPContentLength()+"}");
System.out.println("{\"pBytesRead\":"
			+status.getPBytesRead()+",\"pContentLength\":"+status.getPContentLength()+"}");
		return (int) ((status.getPBytesRead()/status.getPContentLength())*100);
    }

}
