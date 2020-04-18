package com.estee.na.config;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
@Configuration
@ConfigurationProperties(prefix = "sanitization")
@Slf4j
public class SanitizationConfiguration implements Serializable {

    private List<String> prefixes;
    private List<String> suffixes;
    private List<String> whitelistDomains;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<String> originalSuffixes;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<String> originalWhitelistDomains;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<String> originalPrefixes;

    @PostConstruct
    public void lowerCaseDomains() {
        originalWhitelistDomains = whitelistDomains;
        whitelistDomains = whitelistDomains.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public String getOriginalCaseSuffix(String suffix) {
        try {
            return this.originalSuffixes.get(suffixes.indexOf(suffix));
        } catch (Exception e) {
            log.warn("Exception trying to get original suffix for suffix [{}]", suffix);
            return suffix;
        }
    }

    public String getOriginalCasePrefix(String prefix) {
        return this.originalPrefixes.get(prefixes.indexOf(prefix));
    }
}
