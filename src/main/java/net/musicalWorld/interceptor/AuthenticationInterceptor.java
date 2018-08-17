package net.musicalWorld.interceptor;

import net.musicalWorld.config.security.UserDetailsImpl;
import net.musicalWorld.model.User;
import net.musicalWorld.model.enums.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if(authentication.getPrincipal() instanceof UserDetailsImpl){
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            user = userDetails.getUser();
        }else {
            user = User.builder()
                    .role(UserRole.ROLE_ANONYMOUS)
                    .build();
        }
        request.setAttribute("user",user);
        LOGGER.debug("user (id : {},name : {},role : {}) set to request attribute",user.getId(),user.getName(),user.getRole());
        return true;
    }
}
