package service;

import java.io.InputStream;

public interface FileService {
    InputStream getFile(String id);

    String saveFile(InputStream fileContent);
}
