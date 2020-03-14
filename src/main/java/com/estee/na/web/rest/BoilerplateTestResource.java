package com.estee.na.web.rest;


import com.estee.na.domain.BoilerplateTest;
import com.estee.na.domain.reference.EnumTest;
import com.estee.na.service.BoilerplateTestService;
import com.estee.na.service.domain.ServiceResponse;
import com.estee.na.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/boilerplate")
public class BoilerplateTestResource extends AbstractBaseResource {

    private final BoilerplateTestService service;

    @Autowired
    public BoilerplateTestResource(BoilerplateTestService service) {
        this.service = service;
    }


    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ServiceResponse<List<BoilerplateTest>> getList() {
        log.info("Received service call /api/boilerplate/");

        List<BoilerplateTest> boilerplateTests;
        try {
            boilerplateTests = this.service.getList();
        } catch (Exception e) {
            log.info("Error while executing service call /api/boilerplate/", e);
            return this.error(e);
        }

        log.info("Executed service call /api/boilerplate/");
        return success(boilerplateTests);
    }

    @RequestMapping(value = "/{pathParameter}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ServiceResponse<BoilerplateTest> getByPathParameter(@PathVariable(value = "pathParameter") final String pathParameter) {
        log.info("Received service call /api/boilerplate/{} ", pathParameter);
        EnumTest enumTest = EnumTest.getByCd(pathParameter);

        if (enumTest == null) {
            return this.error(HttpStatus.BAD_REQUEST);
        }

        BoilerplateTest boilerplateTest;
        try {
            boilerplateTest = this.service.getWithAttributes(enumTest, null);
        } catch (Exception e) {
            log.info("Error while executing service call /api/boilerplate/{} ", pathParameter, e);
            return this.error(e);
        }

        log.info("Executed service call /api/boilerplate/{}", pathParameter);
        return success(boilerplateTest);
    }

    @RequestMapping(value = "/exception", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ServiceResponse testException(@RequestParam(name = "code") final int code) {
        log.info("Received service call /api/boilerplate/exception with param code [{}]", code);
        try {
            this.service.testException(code);
        } catch (Exception e) {
            log.error("Error while executing service call /api/boilerplate/exception with param code [{}]", code, e);
            return this.error(e);
        }

        log.info("Executed service call /api/boilerplate/exception with param code [{}]", code);
        return success(null);
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ServiceResponse<String> testMessages(@RequestParam(name = "qty") final Long qty) {
        log.info("Received service call /api/boilerplate/messages with param qty [{}]", qty);

        String str;
        try {
            str = this.service.testMessages(qty);
        } catch (Exception e) {
            log.error("Error while executing service call /api/boilerplate/messages with param qty [{}]", qty, e);
            return this.error(e);
        }

        log.info("Executed service call /api/boilerplate/messages with param qty [{}]", qty);
        return success(str);
    }

}
