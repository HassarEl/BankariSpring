package ma.bankatispring.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse res,
                             Object handler) throws Exception {
        HttpSession session = req.getSession(false);
        // Autoriser login, logout, ressources statiques
        String uri = req.getRequestURI();
        if (uri.startsWith("/login") || uri.startsWith("/logout")
                || uri.startsWith("/webjars/") || uri.startsWith("/css/")
                || uri.startsWith("/js/")) {
            return true;
        }
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
