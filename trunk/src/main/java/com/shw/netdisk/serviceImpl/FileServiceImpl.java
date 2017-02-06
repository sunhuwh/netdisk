package com.shw.netdisk.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.shw.netdisk.service.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public List<String> listByName(String fileName) {
		return get3(fileName);
		//return get1(fileName);
	}
	
	public List<String> get1(String fileName){
		List<String> mockList = Lists.newArrayList();
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		String name = fileName.substring(0, fileName.lastIndexOf("."));
		mockList.add(fileName);
		for (int i = 0; i < 3; i++) {
			mockList.add(name+"("+i+")."+ext);
		}
		return mockList;
	}
	
	public List<String> get2(String fileName){
		List<String> mockList = Lists.newArrayList();
		mockList.add(fileName);
		return mockList;
	}
	
	public List<String> get3(String fileName){
		List<String> mockList = Lists.newArrayList();
		mockList.add(fileName);
		for (int i = 0; i < 3; i++) {
			mockList.add(fileName+"("+i+")");
		}
		return mockList;
	}
	
}
