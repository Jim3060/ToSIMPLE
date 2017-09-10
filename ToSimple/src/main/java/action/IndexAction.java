package action;

import model.User;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
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

    @RequestMapping(value = "backupMongodb", method = RequestMethod.GET)
    public void backupMongodb(HttpServletResponse response, @RequestParam(value = "file", defaultValue = "") String file, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        JSONObject jsonObject = new JSONObject();
        if (user == null || user.getRole() != 1) {
            jsonObject.put("valid", 2);
            response.getWriter().print(jsonObject);
            return;
        }
        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream("/setting.properties");
        prop.load(in);
        String fileDir = file;
        if (fileDir.equals("")) {
            fileDir = prop.getProperty("spring.data.mongodb.backUpDir") + (new Date()).toString();
        } else {
            fileDir = prop.getProperty("spring.data.mongodb.backUpDir") + fileDir;
        }
        String command = "mongodump -h " + prop.getProperty("spring.data.mongodb.host")
                + " -p " + prop.getProperty("spring.data.mongodb.port")
                + " -d " + prop.getProperty("spring.data.mongodb.dbname")
                + " -o " + fileDir;
        System.out.print(command);
        try {
            Process p = Runtime.getRuntime().exec(command);

            if (p.waitFor() == 0) {
                //normally terminated, a way to read the output
                InputStream inputStream = p.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);
            } else {
                // abnormally terminated, there was some problem
                //a way to read the error during the execution of the command
                InputStream errorStream = p.getErrorStream();
                byte[] buffer = new byte[errorStream.available()];
                errorStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);

            }
        } catch (Exception e) {
            jsonObject.put("valid", 0);
            response.getWriter().print(jsonObject);
            return;
        }
        jsonObject.put("valid", 1);
        response.getWriter().print(jsonObject);
    }

    @RequestMapping(value = "backupMysql", method = RequestMethod.GET)
    public void backupMysql(HttpSession session, HttpServletResponse response, @RequestParam(value = "file", defaultValue = "") String file) throws IOException {
        User user = (User) session.getAttribute("user");
        JSONObject jsonObject = new JSONObject();
        if (user == null || user.getRole() != 1) {
            jsonObject.put("valid", 2);
            response.getWriter().print(jsonObject);
            return;
        }
        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream("/setting.properties");
        prop.load(in);
        String fileDir = file;
        if (fileDir.equals("")) {
            fileDir = prop.getProperty("data.mysql.backUpDir") + (new Date()).toString();
        } else {
            fileDir = prop.getProperty("data.mysql.backUpDir") + fileDir;
        }
        String command = "mysqldump -h " + prop.getProperty("data.mysql.host") + " -P "
                + prop.getProperty("data.mysql.port")
                + " -u" + prop.getProperty("data.mysql.username")
                + " -p" + prop.getProperty("data.mysql.password")
                + " " + prop.getProperty("data.mysql.dbname") + " User Report --result-file=" + fileDir;
        System.out.print(command);
        try {
            Process p = Runtime.getRuntime().exec(command);
            if (p.waitFor() == 0) {
                //normally terminated, a way to read the output
                InputStream inputStream = p.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);
            } else {
                // abnormally terminated, there was some problem
                //a way to read the error during the execution of the command
                InputStream errorStream = p.getErrorStream();
                byte[] buffer = new byte[errorStream.available()];
                errorStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);

            }

        } catch (Exception e) {
            jsonObject.put("valid", 0);
            response.getWriter().print(jsonObject);
            return;
        }
        jsonObject.put("valid", 1);
        response.getWriter().print(jsonObject);
    }

    @RequestMapping(value = "restoreMongodb", method = RequestMethod.GET)
    public void restoreMongodb(HttpSession session, HttpServletResponse response, @RequestParam(value = "file", defaultValue = "") String file) throws IOException {
        User user = (User) session.getAttribute("user");
        JSONObject jsonObject = new JSONObject();
        if (user == null || user.getRole() != 1) {
            jsonObject.put("valid", 2);
            response.getWriter().print(jsonObject);
            return;
        }

        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream("/setting.properties");
        prop.load(in);
        String fileDir = file;
        if (fileDir.equals("")) {
            jsonObject.put("valid", 0);
            response.getWriter().print(jsonObject);
            return;
        } else {
            fileDir = prop.getProperty("spring.data.mongodb.backUpDir") + fileDir;
        }
        String command = "mongorestore -h " + prop.getProperty("spring.data.mongodb.host") + " -p " + prop.getProperty("spring.data.mongodb.port")
                + " -d " + prop.getProperty("spring.data.mongodb.dbname") + " " + fileDir;

        System.out.print(command);
        try {
            Process p = Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            jsonObject.put("valid", 0);
            response.getWriter().print(jsonObject);
            return;
        }
        jsonObject.put("valid", 1);
        response.getWriter().print(jsonObject);
    }

    @RequestMapping(value = "restoreMysql", method = RequestMethod.GET)
    public void restoreMysql(HttpSession session, HttpServletResponse response, @RequestParam(value = "file", defaultValue = "") String file) throws IOException {

        User user = (User) session.getAttribute("user");
        JSONObject jsonObject = new JSONObject();
        if (user == null || user.getRole() != 1) {
            jsonObject.put("valid", 2);
            response.getWriter().print(jsonObject);
            return;
        }

        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream("/setting.properties");
        prop.load(in);
        String fileDir = file;
        if (fileDir.equals("")) {
            jsonObject.put("valid", 0);
            response.getWriter().print(jsonObject);
            return;
        } else {
            fileDir = prop.getProperty("data.mysql.backUpDir") + fileDir + ".sql";
        }
        String command = "mysql -h " + prop.getProperty("data.mysql.host") + " -P " + prop.getProperty("data.mysql.port")
                + " " + prop.getProperty("data.mysql.dbname") + " < " + fileDir;

        System.out.print(command);
        try {
            Process p = Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            jsonObject.put("valid", 0);
            response.getWriter().print(jsonObject);
            return;
        }
        jsonObject.put("valid", 1);
        response.getWriter().print(jsonObject);
    }


}