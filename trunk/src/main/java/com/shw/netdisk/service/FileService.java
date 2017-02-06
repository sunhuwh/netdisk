package com.shw.netdisk.service;

import java.util.List;

public interface FileService {
	
	/**
	 * 得到这样的一组文件：xxx.jpg或者xxx(1)。jpg或者xxx或者xxx(1)
	 * @param fileName
	 * @return
	 */
	List<String> listByName(String fileName);
	
	
	
}
