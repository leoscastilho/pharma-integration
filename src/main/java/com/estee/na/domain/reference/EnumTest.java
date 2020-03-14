package com.estee.na.domain.reference;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.AttributeConverter;
import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum EnumTest implements Serializable, EnumCodeInterface<EnumTest> {

    Example("E");

    private String code;

    public static EnumTest getByCd(String cd) {
        return EnumDBConverter.getByCode(cd, values());
    }

    public static class Converter implements AttributeConverter<EnumTest, String> {

        @Override
        public String convertToDatabaseColumn(EnumTest code) {
            if (code == null) {
                return null;
            }
            return code.getCode();
        }

        @Override
        public EnumTest convertToEntityAttribute(String s) {
            return getByCd(s);
        }
    }
}
