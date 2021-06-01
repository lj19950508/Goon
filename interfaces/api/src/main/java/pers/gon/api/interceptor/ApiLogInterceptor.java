package pers.gon.api.interceptor;


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
public class ApiLogInterceptor implements HandlerInterceptor {

	@Autowired
	ObjectMapper objectMapper;

	private static final ThreadLocal<Long> startTimeThreadLocal =
			new NamedThreadLocal<Long>("ThreadLocal StartTime");

	@SneakyThrows
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler)  {
		if (log.isInfoEnabled()){
			long beginTime = System.currentTimeMillis();//1、开始时间
	        startTimeThreadLocal.set(beginTime);		//线程绑定变量（该数据只有当前请求的线程可见）
			String reqParam = objectMapper.writeValueAsString(request.getParameterMap());
			log.info("计时开始:{}|URI:{}|入参:{}|请求方法:{}|ip:{}", DateUtil.date(beginTime), request.getRequestURI(),reqParam,request.getMethod(), IpUtils.getRemoteIP(request));
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView)  {
		if (modelAndView != null){
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
			long beginTime = startTimeThreadLocal.get();
			long endTime = System.currentTimeMillis();
	        log.info("计时结束:{}|耗时:{}ms",
					DateUtil.date(endTime),endTime-beginTime,endTime-beginTime);

		}
        // 保存日志

	}

}
