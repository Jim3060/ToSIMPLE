package dao;

import java.io.InputStream;

public interface FileDao {
    String saveFile(InputStream fileContent);

    InputStream getFile(String id);
}
