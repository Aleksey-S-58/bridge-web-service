package web.service.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.DefaultRedirectStrategy;

public class RedirectStrategyWrapper extends DefaultRedirectStrategy {

    private static final Logger logger = LoggerFactory.getLogger(RedirectStrategyWrapper.class);

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        int i = url.lastIndexOf("://");
        if (i > 0) {
            url = "https://" + url.substring(i + 3, url.length());
        } else {
            url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + url;
        }
        logger.warn("-------- REDIRECT URL: {}, requestPath: {}, query: {}", 
                url, request.getPathInfo(), request.getQueryString());
        super.sendRedirect(request, response, url);
    }
}
