package com.estee.na.service;

import com.estee.na.domain.BoilerplateTest;
import com.estee.na.domain.reference.EnumTest;
import com.estee.na.service.domain.ServiceContext;
import com.estee.na.service.domain.ServiceMessage;
import com.estee.na.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class BoilerplateTestService {

    public List<BoilerplateTest> getList() {
        List<BoilerplateTest> l = Arrays.asList(
            new BoilerplateTest(EnumTest.Example, "value1"),
            new BoilerplateTest(EnumTest.Example, "value2")
        );
        return l;
    }

    public BoilerplateTest getWithAttributes(EnumTest attributeOne, String attributeTwo) {
        return new BoilerplateTest(attributeOne, attributeTwo);
    }

    public BoilerplateTest create(BoilerplateTest boilerplateTest) {
        ServiceContext.getContext().addMessage(new ServiceMessage(
                "CD-TEST-01",
                ServiceMessage.Type.User,
                ServiceMessage.Level.Info,
                "BoilerplateTest create service successfully called"));
        return boilerplateTest;
    }

    public BoilerplateTest update(BoilerplateTest boilerplateTest) {
        ServiceContext.getContext().addMessage(new ServiceMessage(
                "CD-TEST-02",
                ServiceMessage.Type.System,
                ServiceMessage.Level.Info,
                "BoilerplateTest update service successfully called"));
        return boilerplateTest;
    }

    public void delete(String id) {
        ServiceContext.getContext().addMessage(new ServiceMessage(
                "CD-TEST-03",
                ServiceMessage.Type.Notification,
                ServiceMessage.Level.Warn,
                "BoilerplateTest delete service successfully called"));
    }

    public void testException(int exceptionCode) {

        if (exceptionCode == 500) {
            int i = 10/0;
        } else if (exceptionCode == 999) {
            throw new RuntimeException("Simulating internal server error");
        }
        
        throw new ServiceException(HttpStatus.valueOf(exceptionCode));
    }

    public String testMessages(Long qty) {
        // TODO: Add qty messages to context
        
        for (int i = 0; i < qty; i++) {
            ServiceContext.getContext().addMessage(new ServiceMessage(
                "CD-TEST-05",
                ServiceMessage.Type.Notification,
                ServiceMessage.Level.Info,
                "Boilerplate testMessage message #" + (i+1)));
        }
        return "Added " + qty + " messages to context";
    }

}
