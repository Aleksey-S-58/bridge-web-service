package web.service.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ServletRequestWrapper extends HttpServletRequestWrapper {

    public ServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getScheme() {
        return "https";
    }
}
