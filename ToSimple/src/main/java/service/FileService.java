package service;

import java.io.InputStream;
import model.Questionnaire;

public interface FileService {
	public InputStream getFile(String id);
	
	public String saveFile(InputStream fileContent);
}
