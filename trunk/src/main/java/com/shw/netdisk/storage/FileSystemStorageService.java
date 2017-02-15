package com.shw.netdisk.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.shw.netdisk.service.FileService;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    
    @Autowired
    private FileService fileService;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public Path store(MultipartFile file) {
    	Path targetPath = this.rootLocation.resolve(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            if(this.existed(targetPath)){
            	String newFileName = refactorFileName(file);
            	System.out.println("newFileName---:"+newFileName);
            	Files.copy(file.getInputStream(), this.rootLocation.resolve(newFileName));
            }else{
            	Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
            }
        } catch (FileAlreadyExistsException e) {
			System.out.println("file is existed");
		}catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
        return targetPath;
    }
    
    private String refactorFileName(MultipartFile file) {
    	String fileName = file.getOriginalFilename();
    	List<String> likeFileNames = fileService.listByName(fileName);
    	String lastFileName = likeFileNames.get(likeFileNames.size()-1);
    	if(hasExt(fileName)){
    		String ext = lastFileName.substring(lastFileName.lastIndexOf(".")+1);
    		String name = lastFileName.substring(0, lastFileName.lastIndexOf("."));
    		int num = getNumber(name, likeFileNames);
    		return name.substring(0, name.lastIndexOf("("))+"("+num+")."+ext;
    	}else{
    		int num = getNumber(lastFileName, likeFileNames);
    		return lastFileName.substring(0, lastFileName.lastIndexOf("("))+"("+num+")";
    	}
	}

	private int getNumber(String name, List<String> likeFileNames) {
		if(likeFileNames.size()==1){
			return 1;
		}
		String numstr = name.substring(name.lastIndexOf("(")+1, name.lastIndexOf(")"));
		int num = Integer.valueOf(numstr)+1;
		return num;
	}

	private boolean hasExt(String fileName) {
		return fileName.indexOf(".")!=-1;
	}

	@Override
	public boolean existed(Path path) {
    	return Files.exists(path);
	}

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

	
}
