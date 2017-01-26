package com.shw.netdisk.controller;

import java.nio.file.Path;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.shw.netdisk.storage.StorageService;

@Controller
public class IndexController {

	@Autowired
	private StorageService storageService;
	
	@GetMapping("/")
    public String index(Model model) {
		model.addAttribute("files", storageService
                .loadAll()
                .map(path -> getSavePath(path))
                .collect(Collectors.toList()));
        return "index";
    }
	
	public String getSavePath(Path path){
    	return MvcUriComponentsBuilder
        .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
        .build().toString();
    }
	
}
