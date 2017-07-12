package action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;
import service.FileService;

@RestController
public class FileAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    @Autowired
    private FileService fileService;

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
    
    @RequestMapping(value = "file/{fileId}", method = RequestMethod.GET)  
    public void getPicture(@PathVariable("fileId")String fileId,HttpServletRequest request,HttpServletResponse response){  
    	System.out.println(fileId);
    	response.setContentType("application/octet-stream;charset=UTF-8");
    	InputStream in=fileService.getFile(fileId);
    	try {  
            OutputStream outputStream=response.getOutputStream();  
            int len=0;  
            byte[]buf=new byte[1024];  
            while((len=in.read(buf,0,1024))!=-1){  
                outputStream.write(buf, 0, len);
            }  
            outputStream.close();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }   
    }  
    
    @RequestMapping(value = "file", method = RequestMethod.POST)  
    public void savePicture(@RequestParam("file")MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException{  
    	System.out.println("here");
    	System.out.println(file);
    	InputStream fileContent =file.getInputStream();
    	String id=fileService.saveFile(fileContent);
    	
    	JSONObject result = new JSONObject();
        result.put("fileId", id);
        response.getWriter().print(result);
        return;
    }  

}
