package pers.gon.manage.interceptor;


import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.gon.infrastructure.common.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志拦截器
 * @author Gon
 */
@Component
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

	@Autowired
	ObjectMapper objectMapper;

	private static final ThreadLocal<Long> COUNT_TIME_THREAD_LOCAL =
			new NamedThreadLocal<Long>("COUNT_TIME_THREAD_LOCAL");

	@SneakyThrows
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler)  {
		if (log.isInfoEnabled()){
			long beginTime = System.currentTimeMillis();
			COUNT_TIME_THREAD_LOCAL.set(beginTime);
			String reqParam = objectMapper.writeValueAsString(request.getParameterMap());
			log.info("计时开始:{}|URI:{}|入参:{}|请求方法:{}|ip:{}", DateUtil.date(beginTime), request.getRequestURI(),reqParam,request.getMethod(), IpUtils.getRemoteIP(request));
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView)  {
		if (null != modelAndView){
			log.info("ViewName: " + modelAndView.getViewName());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex)  {
		if(ex!=null){
			log.error("系统出错",ex);
		}
		if (log.isInfoEnabled()){
			//入参出参
			long beginTime = COUNT_TIME_THREAD_LOCAL.get();
			COUNT_TIME_THREAD_LOCAL.remove();
			long endTime = System.currentTimeMillis();
	        log.info("计时结束:{}|耗时:{}ms", DateUtil.date(endTime),endTime-beginTime);
		}
        // 保存日志

	}

}
