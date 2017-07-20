package action;

import model.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by tbxsx on 17-7-18.
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    /**
     * The url only the user logged in can visit. regex str.
     */
    static final String[] userUrls = new String[]{
            "^setQuestionnaireStatus",
    };
    static final String[] userDeleteUrls = new String[]{
            "^questionnaire/"
    };
    /**
     * The url only the admin can visit. regex str.
     */
    static final String[] adminSearchUrls = new String[]{
            "^allUsers", "^allUnhandledReports", "^allReports", "^allUnhandledReports/",
            "^reportHandled/"
    };
    static final String[] adminPostUrls = new String[]{
            "^user/role"
    };
    /**
     * regex string.
     */
    static final String[] adminDeletePutUrls = new String[]{
            "^user/*", "^report/*",
    };

    /**
     * is the user logged in.
     *
     * @param request
     * @return
     */
    private boolean isLogined(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user != null;
    }

    /**
     * is the user logged and is admin?
     *
     * @param request
     * @return
     */

    private boolean isAdmin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return (user != null) && (user.getRole() == 1);
    }

    private boolean match(List<String> list, String pathname) {
        for (String str : list) {
            if (Pattern.matches(str, pathname)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 2 -- admin ; 1 -- login ; 0 -- no need.
     *
     * @param pathname
     * @param method
     * @return
     */
    public Integer needRole(String pathname, String method) {
        method = method.toLowerCase();
        if (method.equals("delete")) {
            if (match(Arrays.asList(SecurityInterceptor.adminDeletePutUrls), pathname)) {
                return 2;
            }
            if (match((Arrays.asList(SecurityInterceptor.userDeleteUrls)), pathname)) {
                return 1;
            }
            return 0;
        }
        if (method.equals("post")) {
            if (match(Arrays.asList(SecurityInterceptor.adminPostUrls), pathname)) {
                return 2;
            }
            return 0;
        }
        if (method.equals("get")) {
            if (match(Arrays.asList(SecurityInterceptor.adminSearchUrls), pathname)) {
                return 2;
            } else if (match(Arrays.asList(SecurityInterceptor.userUrls), pathname)) {
                return 1;
            }
            return 0;
        }
        return 0;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String pathname = request.getServletPath();
        String method = request.getMethod();
        Integer integer = needRole(pathname, method);
        if (integer == 2) {
            if (!isAdmin(request)) {
                return false;
            }
        } else if (integer == 1) {
            if (!isLogined(request)) {
                return false;
            }
        }
        System.out.print("TEST INTECEPTOR!");
        return true;
    }
}
