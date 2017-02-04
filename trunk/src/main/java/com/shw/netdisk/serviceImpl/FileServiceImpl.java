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
		mockList.add(fileName+"(0)");
		mockList.add(fileName+"(1)");
		mockList.add(fileName+"(2)");
		return mockList;
	}
	
	
	
	
}
