package action;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by tbxsx on 17-7-11.
 */
@Controller
public class IndexAction {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PropertyPlaceholderConfigurer propertyPlaceholderConfigurer;

    @Autowired
    private MongoDbFactory mongoDbFactory;

    @RequestMapping(value = "backup", method = RequestMethod.GET)
    public void backUp(HttpServletResponse response) throws IOException {
        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream("/setting.properties");
        prop.load(in);
        String command = "mongodump -h " + prop.getProperty("spring.data.mongodb.host") + " -p " + prop.getProperty("spring.data.mongodb.port")
                + " -d " + prop.getProperty("spring.data.mongodb.dbname") + " -o \"" + "/home/tbxsx/backup" + "\"";
        Process p = Runtime.getRuntime().exec(command);
    }

    @RequestMapping(value = "restore", method = RequestMethod.GET)
    public void restore() throws IOException {
        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream("/setting.properties");
        prop.load(in);
        String command = "mongorestore -h " + prop.getProperty("spring.data.mongodb.host") + " -p " + prop.getProperty("spring.data.mongodb.port")
                + " -d " + prop.getProperty("spring.data.mongodb.dbname") + "/home/tbxsx/backup" + "\"";
        Process p = Runtime.getRuntime().exec(command);
    }


}
