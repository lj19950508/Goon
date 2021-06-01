package pers.gon.infrastructure.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import pers.gon.infrastructure.common.entity.CommonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@Slf4j
@Controller
@ControllerAdvice
public class GlobalExceptionHandler extends AbstractErrorController {

    @Autowired
    ObjectMapper objectMapper;

    public GlobalExceptionHandler() {
        super(new DefaultErrorAttributes());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String processUnauthenticatedException(NativeWebRequest request,
                                                        UnauthorizedException e) {
        return "error/401.html";
    }

    //404异常 500异常使用页面处理
    @ExceptionHandler(RuntimeException.class) //拦截所有运行时的全局异常
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response, Exception e, Model model) throws Exception {
        if(e!=null){
            e.printStackTrace();

        }
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(!isJsonRequest(request)){
            if(statusCode!=null && statusCode == 404){
                return "error/404.html";
            }else  {
                //未知异常
                Throwable error = (Throwable) request.getAttribute("javax.servlet.error.exception");
                if(error!=null){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    PrintStream printStream = new PrintStream(out);
                    error.printStackTrace(printStream);
                    String rs = new String(out.toByteArray());
                    model.addAttribute("msg",rs);
                }else{
                    model.addAttribute("msg",e.getMessage());
                }

                return "error/exception.html";
            }
        }else{
            String msg  = "";
            if(statusCode == 404){
                msg = "404地址错误";
            }else{
                msg = e.getMessage();
            }
            writeJson(response,statusCode,objectMapper.writeValueAsString(CommonResult.fail(msg,statusCode)));
            return null;
        }
    }


    @Override
    public String getErrorPath() {
        return "/error";
    }

    protected void writeJson(HttpServletResponse response, int status,Object error) throws Exception {
        response.reset();
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(error);
    }

    protected boolean isJsonRequest(HttpServletRequest request) {
        String header =request.getHeader("x-requested-with");
        if (header != null && header.equals("XMLHttpRequest")) {
            return true;
        } else {
            //也可以通过获取 HTTP 头，根据 Accept 字段是否包含 JSON 来进一步判断， 比如 II request.getHeader(”Accept”).contains(”application/json”)
            return false;
        }
    }
}