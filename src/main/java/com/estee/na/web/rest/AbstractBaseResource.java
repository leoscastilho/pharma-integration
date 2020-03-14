package com.estee.na.web.rest;

import com.estee.na.service.domain.ServiceContext;
import com.estee.na.service.domain.ServiceMessage;
import com.estee.na.service.domain.ServiceResponse;
import com.estee.na.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@Slf4j
public abstract class AbstractBaseResource {
    
    protected ServiceResponse success() {
        
        ServiceContext ctx = ServiceContext.getContext();
        ctx.setHttpStatus(HttpStatus.NO_CONTENT);
        
        return success(null);
    }
    
    <T> ServiceResponse<T> success(T response) {
        
        ServiceContext.getContext().setEndTime(System.currentTimeMillis());
        
        ServiceContext ctx = ServiceContext.getContext();
        
        ServiceResponse<T> serviceResponse = ServiceResponse.buildResponse(ctx, null);
        serviceResponse.getHeader().setStatus(ctx.getHttpStatus() != null ? ctx.getHttpStatus().value() : HttpStatus.OK.value());
        serviceResponse.setPayload(response);
        
        return serviceResponse;
    }
    
    <T> ServiceResponse<T> error(Exception e) {

        ServiceContext ctx = ServiceContext.getContext();
        ctx.addMessage(new ServiceMessage(
                        null,
                        ServiceMessage.Type.System,
                        ServiceMessage.Level.Error,
                        e.getMessage()));

        ctx.setEndTime(System.currentTimeMillis());

        ServiceResponse<T> serviceResponse = ServiceResponse.buildResponse(ctx, null);
        serviceResponse.getHeader().setStatus(e instanceof ServiceException ? ((ServiceException) e).getHttpStatus().value() : HttpStatus.INTERNAL_SERVER_ERROR.value());
        serviceResponse.setException(e);
        serviceResponse.setExceptionMessage(e.getMessage());

        return serviceResponse;
    }

    <T> ServiceResponse<T> error(HttpStatus httpStatus) {

        ServiceContext ctx = ServiceContext.getContext();
        ctx.setEndTime(System.currentTimeMillis());

        ServiceResponse<T> serviceResponse = ServiceResponse.buildResponse(ctx, null);
        serviceResponse.getHeader().setStatus(httpStatus != null ? httpStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR.value());

        return serviceResponse;
    }
}
