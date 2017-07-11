package action;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tbxsx on 17-7-11.
 */
@Controller
public class IndexAction {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoDbFactory mongoDbFactory;

    @RequestMapping(value = "backup",method = RequestMethod.GET)
    public void backUp(HttpServletResponse response) throws IOException {
////        mongoTemplate;
//        String command = "mongodump -h " + host + " -p " + port
//                + " -d " + dbname + " -o \"" + mongoDbExportFolder + "\"";
//        Process p = Runtime.getRuntime().exec(command);
        mongoTemplate.getConverter();
    }

    @RequestMapping(value = "restore",method = RequestMethod.GET)
    public void restore(){

    }



}
