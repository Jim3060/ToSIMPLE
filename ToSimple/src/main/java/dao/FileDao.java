package dao;

import java.io.InputStream;

public interface FileDao {
	public String saveFile(InputStream fileContent);
	
	public InputStream getFile(String id);
}
