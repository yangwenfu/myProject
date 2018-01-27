package com.xinyunlian.jinfu;

import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jl062 on 2017/3/6.
 */
@Controller
public class SecurityFileController {

    @RequestMapping("/security")
    public void security(HttpServletRequest request, HttpServletResponse response) {
        if (StaticResourceSecurity.authURI(request.getParameter("path"), request.getParameter("auth_key"))) {
            response.setHeader("X-Accel-Redirect",request.getParameter("base")+request.getParameter("path"));
            return;
        }
        response.setHeader("X-Accel-Redirect", "/403");
    }

}
