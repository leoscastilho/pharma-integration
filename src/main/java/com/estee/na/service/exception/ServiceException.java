package com.estee.na.service.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * BOException
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceException extends RuntimeException {
    
    public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    
    private HttpStatus httpStatus;
    
    public ServiceException() {
        super(DEFAULT_HTTP_STATUS.getReasonPhrase());
        this.httpStatus = DEFAULT_HTTP_STATUS;
    }
    
    public ServiceException(HttpStatus status) {
        super(status.getReasonPhrase());
        this.httpStatus = status;
    }
    
    public ServiceException(Exception nested) {
        super(DEFAULT_HTTP_STATUS.getReasonPhrase(), nested);
        this.httpStatus = DEFAULT_HTTP_STATUS;
    }
    
    public ServiceException(HttpStatus status, Exception nested) {
        super(status.getReasonPhrase(), nested);
        this.httpStatus = status;
    }
    
}
