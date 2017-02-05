package com.shw.netdisk.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.shw.netdisk.service.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public List<String> listByName(String fileName) {
		List<String> mockList = Lists.newArrayList();
		String ext = fileName.substring(fileName.lastIndexOf("."));
		String name = fileName.substring(0, fileName.lastIndexOf(".")-1);
		mockList.add(fileName);
		for (int i = 0; i < 3; i++) {
			mockList.add(name+"("+i+")"+ext);
		}
//		mockList.add(name+"(0)"+ext);
//		mockList.add(name+"(1)"+ext);
//		mockList.add(fileName+"(2)");
		return mockList;
	}
	
}
