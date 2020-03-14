package com.estee.na.domain.reference;

import com.estee.na.util.ParserUtil;

/**
 * Generically converts our code enum pattern from String codes to Enum values
 */
class EnumDBConverter {

    static <T extends EnumCodeInterface> T getByCode(String code, T[] values) {
        if (!ParserUtil.nonNullNonEmpty(code)) {
            return null;
        }

        for (T val : values) {
            if (code.equals(val.getCode())) {
                return val;
            }
        }

        return null;
    }

}
