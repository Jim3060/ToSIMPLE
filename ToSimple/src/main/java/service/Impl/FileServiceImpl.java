package service.Impl;

import dao.FileDao;
import service.FileService;

import java.io.InputStream;

public class FileServiceImpl implements FileService {
    private FileDao fileDao;

    public void setFileDao(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    public InputStream getFile(String id) {

        // TODO Auto-generated method stub
        return fileDao.getFile(id);
    }

    @Override
    public String saveFile(InputStream fileContent) {
        // TODO Auto-generated method stub
        String fileId = fileDao.saveFile(fileContent);

        return fileId;
    }


}
