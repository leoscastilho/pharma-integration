package com.estee.na.service.interceptor;

import com.estee.na.service.domain.ServiceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
@Component
public class ServiceContextInterceptor implements HandlerInterceptor {

    @Autowired
    public ServiceContextInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {


        String executionId = request.getHeader(ServiceContext.EXECUTION_ID_KEY);

        ServiceContext.clearContext();
        ServiceContext context = ServiceContext.getContext(executionId);
        context.setStartTime(System.currentTimeMillis());

        // Setting execution id response header
        response.setHeader(ServiceContext.EXECUTION_ID_KEY, context.getExecutionId());

        log.debug("Starting execution of [{}]. Start time: [{}]", request.getRequestURI(), new Date(context.getStartTime()));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {

        ServiceContext context = ServiceContext.getContext();

        log.debug("Finishing execution of [{}]. End time: [{}]", request.getRequestURI(), new Date(context.getEndTime()));

        ServiceContext.clearContext();
    }
}
