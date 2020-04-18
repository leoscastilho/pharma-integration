package com.estee.na.domain.reference;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.AttributeConverter;
import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum EmailProvider implements Serializable, EnumCodeInterface<EmailProvider> {

    GMAIL("gmail",true,true),
    OUTLOOK("outlook", false,true),
    YAHOO("yahoo", false,false),
    AOL("aol",false,false),
    HOTMAIL("hotmail",false,true),
    OTHERS("others",false,false);

    private String code;
    private boolean acceptDot;
    private boolean acceptPlusSign;

    public static EmailProvider getByCd(String cd) {
        return EnumDBConverter.getByCode(cd, values());
    }

    public static class Converter implements AttributeConverter<EmailProvider, String> {

        @Override
        public String convertToDatabaseColumn(EmailProvider code) {
            if (code == null) {
                return null;
            }
            return code.getCode();
        }

        @Override
        public EmailProvider convertToEntityAttribute(String s) {
            return getByCd(s);
        }
    }

    public boolean acceptsDotsInUsername(){
        return this.acceptDot;
    }

    public boolean acceptsPlusInUsername(){
        return this.acceptPlusSign;
    }
}
