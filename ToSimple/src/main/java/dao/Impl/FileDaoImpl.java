package dao.Impl;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import dao.FileDao;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.InputStream;

public class FileDaoImpl implements FileDao {
    protected MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return this.mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate template) {
        this.mongoTemplate = template;
    }

    @Override
    public String saveFile(InputStream fileContent) {
        DB db = mongoTemplate.getDb();
        GridFS gfsPhoto = new GridFS(db, "questionnaireFiles");
        GridFSInputFile gfsFile = gfsPhoto.createFile(fileContent);
        gfsFile.save();
        return gfsFile.getId().toString();
        //return 0;
    }

    @Override
    public InputStream getFile(String id) {
        System.out.println(id);
        DB db = mongoTemplate.getDb();
        GridFS gfsPhoto = new GridFS(db, "questionnaireFiles");
        GridFSDBFile imageForOutput = gfsPhoto.find(new ObjectId(id));
        return imageForOutput.getInputStream();
    }

}
