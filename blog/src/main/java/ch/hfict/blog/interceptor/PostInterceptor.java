package ch.hfict.blog.interceptor;

import ch.hfict.blog.WebMvcConfig;
import ch.hfict.blog.utils.Crypto;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        String method = request.getMethod();

        if (path.equals("/users") && method.equals(HttpMethod.POST.name())) {
            return true;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null) {
            response.getWriter().println("Authorization header not found");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        String[] jwtTokenArray = authHeader.split(" ");
        String jwtToken = jwtTokenArray.length == 2 ? jwtTokenArray[1] : null;

        if (jwtToken == null) {
            response.getWriter().println("Missing token");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        DecodedJWT jwt = Crypto.verifyToken(jwtToken);

        if (jwt == null) {
            response.getWriter().println("Invalid token");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        else {
            Long userId = jwt.getClaim("userId").asLong();
            request.setAttribute("userId", userId);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
