package com.formu.interceptor;


import com.formu.Utils.JsonUtil;
import com.formu.Utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {


    @Autowired
    StringRedisTemplate redis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Token");
        if (token != null) {
            String json = redis.opsForValue().get(token);
            if (json != null) {
//                redis.expire(token, 30, TimeUnit.MINUTES);
                return true;
            } else {
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(JsonUtil.obj2String(Msg.createByErrorMessage("登录过期!")));
                return false;
            }
        }
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JsonUtil.obj2String(Msg.createByErrorMessage("您未登录!")));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

