package com.estee.na.domain.reference;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.AttributeConverter;
import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum PhoneType implements Serializable, EnumCodeInterface<PhoneType> {

    Mobile("M"),
    LandLine("L"),
    Unknown("U");

    private String code;

    public static PhoneType getByCd(String cd) {
        return EnumDBConverter.getByCode(cd, values());
    }

    public static class Converter implements AttributeConverter<PhoneType, String> {

        @Override
        public String convertToDatabaseColumn(PhoneType code) {
            if (code == null) {
                return null;
            }
            return code.getCode();
        }

        @Override
        public PhoneType convertToEntityAttribute(String s) {
            return getByCd(s);
        }
    }

    public static PhoneType getByPhoneNumberType(PhoneNumberUtil.PhoneNumberType numberType) {
        switch (numberType) {
            case FIXED_LINE:
                return LandLine;
            case MOBILE:
            case FIXED_LINE_OR_MOBILE:
                return Mobile;
            default:
                return Unknown;
        }

    }

}
