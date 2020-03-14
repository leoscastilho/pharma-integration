package com.estee.na.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ServiceResponse<T> {
    
    private ServiceResponseHeader header = new ServiceResponseHeader();
    private T payload;
    
    @JsonIgnore
    @SuppressWarnings("unused")
    private String exception = null;
    @JsonIgnore
    private String exceptionMessage = null;
    @JsonIgnore
    private List<String> stackLines = null;
    
    public static <T> ServiceResponse<T> buildResponse(ServiceContext ctx, T payload) {
        
        ctx.setEndTime(System.currentTimeMillis());
        
        ServiceResponse<T> serviceResponse = new ServiceResponse<>();
        
        serviceResponse.getHeader().setExecutionId(ctx.getExecutionId());
        serviceResponse.getHeader().setMessages(ctx.getMessages());
        serviceResponse.getHeader().setStartTime(ctx.getStartTime());
        serviceResponse.getHeader().setEndTime(ctx.getEndTime());
        
        serviceResponse.setPayload(payload);
        
        return serviceResponse;
    }
    
    public void setException(Exception e) {
        
        if (e == null) {
            return;
        }
        
        exception = e.toString();
        
        if (e.getCause() != null) {
            this.exceptionMessage = e.getCause().getMessage();
        } else {
            this.exceptionMessage = e.getMessage();
        }
        
        stackLines = new ArrayList<>();
        
        // Get the first line in the stack that is ours...
        StackTraceElement[] stacks = e.getStackTrace();
        
        for (StackTraceElement stack : stacks) {
            // TODO: Get dynamically instead of hardcoded string
            if (stack.getClassName().startsWith("com.estee.na")) {
                stackLines.add(stack.toString());
            }
        }
    }
    
    protected String getMessageForLogging() {
        StringBuilder logMessage = new StringBuilder(exceptionMessage == null ? "" : exceptionMessage);
        
        for (ServiceMessage msg : getHeader().getMessages()) {
            logMessage.append(msg.getDescription());
        }
        
        return logMessage.toString();
    }
    
}