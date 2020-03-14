package com.estee.na.service.exception;

import com.estee.na.service.domain.ServiceContext;
import com.estee.na.service.domain.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceControllerAdvice {
    
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ServiceResponse> serviceException(ServiceException e) {
        
        // TODO: Log exceptins
        
        ServiceContext ctx = ServiceContext.getContext();
        ctx.addExceptionToContext(e, e.getHttpStatus());
        
        return new ResponseEntity<>(ServiceResponse.buildResponse(ctx, null), ctx.getHttpStatus());
    }
    
    @ExceptionHandler
    public ResponseEntity<ServiceResponse> unhandledException(Exception e) {
        
        // TODO: Log all unhandled exceptions
    
        ServiceContext ctx = ServiceContext.getContext();
        ctx.addExceptionToContext(e, HttpStatus.INTERNAL_SERVER_ERROR);
        
        return new ResponseEntity<>(ServiceResponse.buildResponse(ctx, null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
