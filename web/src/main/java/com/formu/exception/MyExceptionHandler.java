//package com.formu.exception;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.formu.Utils.JsonUtil;
//import com.formu.Utils.Msg;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.ModelAndView;
//
//
///**
// * Created by weiqiang
// */
//@Slf4j
//@ControllerAdvice
//public class MyExceptionHandler {
//    public static final String IMOOC_ERROR_VIEW = "error";
//
//    @ExceptionHandler(value = Exception.class)
//    public void errorHandler(HttpServletRequest reqest,
//                               HttpServletResponse response, Exception e) throws Exception {
//
//       log.error(e.getMessage());
//       e.printStackTrace();
//        response.setContentType("application/json;charset=utf-8");
//        response.getWriter().write(JsonUtil.obj2String(Msg.createByErrorMessage("出现错误!")));
//
//    }
//
//
//    public static boolean isAjax(HttpServletRequest httpRequest){
//        return  (httpRequest.getHeader("X-Requested-With") != null
//                && "XMLHttpRequest"
//                .equals( httpRequest.getHeader("X-Requested-With").toString()) );
//    }
//
//}
