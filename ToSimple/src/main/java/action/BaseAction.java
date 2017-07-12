package action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;


public class BaseAction {

	private static final long serialVersionUID = 1L;

	public HttpServletRequest request() {
		return ServletActionContext.getRequest();
	}

	public HttpSession session() {
		return ServletActionContext.getRequest().getSession();
	}

	public ServletContext application() {
		return ServletActionContext.getServletContext();
	}

	public HttpServletResponse response() {
		return ServletActionContext.getResponse();
	}

}
