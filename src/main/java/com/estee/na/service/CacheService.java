package com.estee.na.service;

import com.estee.na.config.SanitizationConfiguration;
import com.estee.na.util.ParserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.DomainValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
@Slf4j
public class CacheService {

    @Autowired
    SanitizationConfiguration sanitizationConfiguration;

    @CacheEvict(cacheNames = {"emailValidityCache"
    }, allEntries = true)
    public void evictAllCaches() {
    }

    @CacheEvict(cacheNames = {"emailValidityCache"
    }, cacheManager = "inMemoryCache", allEntries = true)
    public void evictAllCachesInternal() {
    }

    @Cacheable(value = "emailValidityCache", cacheManager = "inMemoryCache")
    public boolean getEmailDomainIsValid(String emailAddr) {
        String domain = null;
        try {
            domain = emailAddr.substring(emailAddr.indexOf("@") + 1);

            if (ParserUtil.nonNullNonEmpty(domain) && sanitizationConfiguration.getWhitelistDomains().contains(domain.toLowerCase())) { // don't need to lkup if whitelisted
                return true;
            }

            log.debug("Checking validity of domain [{}]", domain);
            InetAddress.getByName(domain);
            return true;
        } catch (UnknownHostException e) {
            log.info("Domain [{}] does not appear valid", domain);
            return false;
        } catch (Exception e) {
            log.error("Domain lookup Exception for domain [{}]", domain, e);
            return false;
        }
    }

    public static void main(String args[]) {
        log.info("VALIDATOR {}", DomainValidator.getInstance().isValid("google.con"));
        log.info("VALIDATOR {}", DomainValidator.getInstance().isValid("googl.com"));
        log.info("VALIDATOR {}", DomainValidator.getInstance().isValid("yaho.con"));
        log.info("VALIDATOR {}", DomainValidator.getInstance().isValid("consultfrom.freeserve.co.uk"));
        log.info("VALIDATOR {}", DomainValidator.getInstance().isValid("google.com"));

    }

}
