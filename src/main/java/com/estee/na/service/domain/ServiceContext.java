package com.estee.na.service.domain;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// TODO: Call Log
// TODO: Get messages from DB/Cache
// TODO: Session

@Data
public class ServiceContext {

    public static String EXECUTION_ID_KEY = "executionId";

    private static final Logger Log = LoggerFactory.getLogger(ServiceContext.class);

    private static ThreadLocal<ServiceContext> threadLocal = new ThreadLocal<ServiceContext>();
    
    
    private String executionId;
    
    private long startTime;
    private long endTime;
    
    private HttpStatus httpStatus;
    
    private List<ServiceMessage> messages = new ArrayList<ServiceMessage>();


    /**
     * Checks if ServiceContext exists in running thread.
     *
     * @return boolean indicating if running thread has a ServiceContext
     */
    public static boolean contextExists(){
        return threadLocal.get() != null;
    }


    /**
     * Overloaded helper getContext method, not requiring executionId.
     *
     * Passes null to ServiceContext.getContext(executionId).
     *
     * @return thread ServiceContext
     */
    public static ServiceContext getContext() {
        // TODO: Should it be synchronized also?
        return ServiceContext.getContext(null);
    }

    /**
     * Lazy loads thread's ServiceContext.
     *
     * A executionId value can be passed as argument, but it will only be used
     * for new ServiceContext instantiations.
     *
     * @param executionId Optional executionId, if null, one will be created.
     * @return thread Service Context
     */
    public synchronized static ServiceContext getContext(String executionId) {

        ServiceContext ctx = threadLocal.get();

        if (ctx == null) {
            return newContext(executionId);
        }

        return ctx;
    }
    
    /**
     * Creates a new service execution context and puts it in Thread scope for later retrieval.
     *
     * @param executionId the executionId
     * @return newly created ServiceContext
     */
    protected static ServiceContext newContext(String executionId) {
        ServiceContext ctx = new ServiceContext(executionId);
        threadLocal.set(ctx);
        return ctx;
    }

    /**
     * Clears the thread's ServiceContext.
     *
     * Obs: Should always be called to avoid memory leak/cross execution data leakage.
     */
    public static void clearContext() {
        threadLocal.remove();
        MDC.remove("executionId");
    }

    /**
     * Adds a ServiceMessage to ServiceContext message list.
     *
     * If message has HttpStatus, will set as ServiceContext status.
     */
    public void addMessage(ServiceMessage message) {
        if(message.getHttpStatus() != null) {
            this.httpStatus = message.getHttpStatus();
        }
        messages.add(message);
    }
    
    /**
     * Adds an exception message to the ServiceContext
     *
     * @param e the exception (Throwable)
     */
    public void addExceptionToContext(Throwable e) {
        this.addExceptionToContext(e, null);
    }
    
    /**
     * Adds an exception message to the ServiceContext
     *
     * @param status the HttpStatus
     * @param e the exception (Throwable)
     */
    public void addExceptionToContext(Throwable e, HttpStatus status) {
        
        ServiceMessage message = new ServiceMessage();
        
        message.setType(ServiceMessage.Type.System);
        message.setCd("EXP-001");
        message.setLevel(ServiceMessage.Level.Error);
        message.setDescription(e.toString());
        
        if (status != null) {
            message.setHttpStatus(status);
        }
        
        this.addMessage(message);
    }
    
    /**
     * Service context constructor.
     *
     * Accepts a optional executionId value. If not sent, a new one will be
     * created for he instance.
     *
     * @param executionId the execution id value - optional
     */
    private ServiceContext(String executionId) {
        
        String txnId = executionId;
        
        if (executionId == null || executionId.isEmpty()) {
            txnId = UUID.randomUUID().toString();
        }
        
        this.executionId = txnId;
        // Default HttpStatus is Success 200
        this.httpStatus = HttpStatus.OK;
        
        MDC.put(EXECUTION_ID_KEY, txnId);
    }
    
    
    /**
     * Adds a key,value pair to the MDC ?context?
     *
     * @param key the key
     * @param value the value
     */
    public void addValue(String key, String value){
        MDC.put(key,value);
    }

    /**
     * Removes key,value pair from the MDC ?context?
     *
     * @param key the key
     */
    public void removeValue(String key) {
        // TODO: Test if calling without key being present breaks
        MDC.remove(key);
    }

}
