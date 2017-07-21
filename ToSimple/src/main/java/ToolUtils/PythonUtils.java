package ToolUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

public class PythonUtils {
	public static void main(String[] args) throws IOException   {
//		Properties props = new Properties();  
//        props.put("python.console.encoding", "UTF-8");  
//        props.put("python.security.respectJavaAccessibility", "false");  
//        props.put("python.import.site", "false");  
//        props.put("python.home", "/Library/Frameworks/Python.framework/Versions/2.7/lib/python2.7");
//        //props.put("python.home", "/Library/Frameworks/Python.framework/Versions/2.7");
//        Properties preprops = System.getProperties();  
//          
//        PythonInterpreter.initialize(preprops, props, new String[0]); 
//		PythonInterpreter interpreter = new PythonInterpreter();  
//		PySystemState sys = Py.getSystemState();
//		sys.path.add("/Library/Frameworks/Python.framework/Versions/2.7/lib/python2.7");
//		sys.path.add("/Users/JimLiu/Desktop");
//		sys.path.add("/Library/Frameworks/Python.framework/Versions/2.7/lib/python27.zip");
//		sys.path.add("/Library/Frameworks/Python.framework/Versions/2.7/lib/python2.7/plat-darwin");
//		sys.path.add("/Library/Frameworks/Python.framework/Versions/2.7/lib/python2.7/plat-mac");
//		sys.path.add("/Library/Frameworks/Python.framework/Versions/2.7/lib/python2.7/plat-mac/lib-scriptpackages");
//		sys.path.add("/Library/Frameworks/Python.framework/Versions/2.7/lib/python2.7/lib-tk");
//		sys.path.add("/Library/Frameworks/Python.framework/Versions/2.7/lib/python2.7/lib-old");
//		sys.path.add("/Library/Frameworks/Python.framework/Versions/2.7/lib/python2.7/lib-dynload");
//		sys.path.add("/Library/Frameworks/Python.framework/Versions/2.7/lib/python2.7/site-packages");
//		
//		sys.path.add("/Library/Python/2.7/site-packages");
//		sys.path.add("/System/Library/Frameworks/Python.framework/Versions/2.7/Extras/lib/python");
//		sys.path.add("/System/Library/Frameworks/Python.framework/Versions/2.7/Extras/lib/python/PyObjC");
//		
////		sys.path.add("/Library/Python/2.7/site-packages");
//		interpreter.exec("import os");
//		interpreter.exec("import bs4");
//		///Users/JimLiu/Desktop
//		interpreter.execfile("/Users/JimLiu/Desktop/questionnaire.py");  
//        PyFunction func = (PyFunction) interpreter.get("loadQuestionnaireFromSojump", PyFunction.class);   
//        PyObject pyobj = func.__call__();  
//        System.out.println("anwser = " + pyobj.toString());  
//        System.out.println("hi");
		
		Process pr=null;
		try {
			pr = Runtime.getRuntime().exec("python /Users/JimLiu/Desktop/questionnaire.py ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		 BufferedReader in = new BufferedReader(new    
		         InputStreamReader(pr.getInputStream()));    
		 String line;    
		 while ((line = in.readLine()) != null) {    
		     System.out.println(line);    
		 }    
		 in.close();    
		 try {
			pr.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		 System.out.println("end");    

	}
}
