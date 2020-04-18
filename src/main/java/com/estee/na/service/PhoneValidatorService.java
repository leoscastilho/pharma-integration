package com.estee.na.service;

import com.estee.na.domain.dto.CleansedPhone;
import com.estee.na.domain.reference.PhoneType;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PhoneValidatorService {
    public static CleansedPhone standardize(String rawPhoneNumber) {

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        String normalizedPhone = "";
        CleansedPhone cleansedPhone = new CleansedPhone();

        try {
            // parseAndKeepRawInput(String numberToParse, String defaultRegion)
            // numberToParse: number that we are attempting to parse. This can contain formatting such
            // * as +, ( and -, as well as a phone number extension.
            //
            // defaultRegion:region that we are expecting the number to be from. This is only used if
            // * the number being parsed is not written in international format. The country calling code
            // * for the number in this case would be stored as that of the default region supplied.
            //TODO: Assuming the consumer has an address and this address has a valid countryAlpha2Cd
            final Phonenumber.PhoneNumber phoneProto = phoneUtil.parseAndKeepRawInput(rawPhoneNumber, "BR");
            normalizedPhone = phoneUtil.format(phoneProto, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
            if (phoneProto.getExtension() != null && phoneProto.getExtension().length() > 0) {
                normalizedPhone = normalizedPhone + " x" + phoneProto.getExtension();
            }

            cleansedPhone.setCleansedPhoneNumber(normalizedPhone);
            cleansedPhone.setPhoneType(PhoneType.getByPhoneNumberType(phoneUtil.getNumberType(phoneProto)));
            cleansedPhone.setValidInd(phoneUtil.isValidNumber(phoneProto) && phoneUtil.isPossibleNumber(phoneProto));

        } catch (NumberParseException e) {
            log.error("Could not parse phone numner [{}]", rawPhoneNumber);
        }
        return cleansedPhone;
    }
}
