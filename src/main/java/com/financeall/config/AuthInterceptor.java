package com.financeall.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.financeall.model.User;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
public boolean preHandle(HttpServletRequest request,
        HttpServletResponse response, Object handler) throws Exception {
    String uri = request.getRequestURI();

    // Whitelist public paths
    if (isPublicPath(uri)) return true;

    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
        response.sendRedirect("/login");
        return false;
    }

    // Blokir akses /admin/** untuk non-admin
    if (uri.startsWith("/admin") && !"ADMIN".equals(user.getRole())) {
        response.sendError(403, "Akses ditolak");
        return false;
    }

    // Blokir user yang di-ban
    if (user.isBanned()) {
        request.getSession().invalidate();
        response.sendRedirect("/login?banned=true");
        return false;
    }

    return true;
}

private boolean isPublicPath(String uri) {
    return uri.equals("/")
            || uri.equals("/login")
            || uri.equals("/register")
            || uri.equals("/reset-password")
            || uri.equals("/error")
            || uri.startsWith("/css/")
            || uri.startsWith("/js/")
            || uri.startsWith("/images/");
}
}