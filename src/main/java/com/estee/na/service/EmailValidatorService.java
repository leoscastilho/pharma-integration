package com.estee.na.service;

import com.estee.na.domain.reference.EmailProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailValidatorService {
    @Autowired
    private CacheService cacheService;

    public static String getCleansedEmail(String inputEmail) {
        inputEmail = inputEmail.toLowerCase().trim();

        String cleansedEmail = inputEmail;
        if (!cleansedEmail.contains("@") || !cleansedEmail.contains(".") || cleansedEmail.lastIndexOf('.') < cleansedEmail.indexOf('@')) {
            return cleansedEmail;
        }
        if (cleansedEmail.indexOf("(") < cleansedEmail.indexOf("@") && cleansedEmail.indexOf("@") < cleansedEmail.indexOf(")")) {
            cleansedEmail = cleansedEmail.substring(cleansedEmail.indexOf("(") + 1, cleansedEmail.lastIndexOf(")"));
        }

        if (cleansedEmail.lastIndexOf("(") == cleansedEmail.indexOf("(") && cleansedEmail.indexOf("(") == 0 && cleansedEmail.indexOf(")") == cleansedEmail.length() - 1 && cleansedEmail.lastIndexOf(")") == cleansedEmail.indexOf(")")) {
            cleansedEmail = cleansedEmail.replace("(", "");
            cleansedEmail = cleansedEmail.replace(")", "");
        }

        if (cleansedEmail.contains("(") && cleansedEmail.contains(")") &&
                ((cleansedEmail.indexOf("(") == 0 && cleansedEmail.indexOf(")") != cleansedEmail.length() - 1) || (cleansedEmail.indexOf("(") != 0 && cleansedEmail.indexOf(")") == cleansedEmail.length() - 1)) || ((cleansedEmail.indexOf("(") != 0 && cleansedEmail.indexOf(")") != cleansedEmail.length() - 1))) {
            cleansedEmail = cleansedEmail.replaceAll("\\(.*?\\)", ""); //Removes comments from the email. (sir)user@domain.com -> user@domain.com
        }

        cleansedEmail = cleansedEmail.trim();
        String username = cleansedEmail.substring(0, cleansedEmail.indexOf('@'));
        String domain = cleansedEmail.substring(cleansedEmail.indexOf('@'));
        EmailProvider emailProvider = EmailProvider.getByCd(domain.substring(1, domain.indexOf('.')));
        if (emailProvider == null) {
            emailProvider = EmailProvider.OTHERS;
        }
        if (emailProvider.acceptsPlusInUsername()) {
            username = username.replaceAll("\\+.*$", "");
        }
        if (emailProvider.acceptsDotsInUsername()) {
            username = username.replace(".", "");
        }

        cleansedEmail = username.concat(domain);

        if (!cleansedEmail.equals(inputEmail)) {
            log.info("Finished cleansing the email address. [{}] cleansed to [{}]", inputEmail, cleansedEmail);
        }

        return cleansedEmail;
    }

    public boolean getEmailValidInd(String email) {
        return EmailValidator.getInstance().isValid(email) && cacheService.getEmailDomainIsValid(email);
    }

}
