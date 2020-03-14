package com.estee.na.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Message object for storing passing messages in the system.
 *
 * Messages have a message code which makes them unique.
 * Standard message code format is:
 *  CD-NUM
 * for example:
 *  SYS-001
 *
 * The code specifies the subsystem, and the message number is simply a unique number.
 * This is not enforced, but is recommended.
 *
 * Messages have a level:
 *  I   Info
 *  E   Error
 *  W   Warning
 *
 * Message level drives how messages are displayed in the UI.
 *
 * Messages have a type:
 *  U   User
 *  S   System
 *
 * System messages will be returned in JSON payloads but may or may not be displayed on UI screen based on
 * configuration.
 *
 * Messages support substitution parameters with syntax similar to SLF4J.  For example:
 *  The message:  "917 is not a valid phone number."
 * Would be configured like this:
 *  "{} is not a valid phone number."
 *
 * Data to be substituted into message would be passed at runtime.
 *
 */
// TODO: DB and Cache
@Data
public class ServiceMessage {

    public static final String GENERAL_EXCEPTION_CODE = "SYS-001";

    private Type type;
    private String typeCd;
    private Level level;
    private String levelCd;
    private String cd;
    private String description;

    @JsonIgnore
    private HttpStatus httpStatus;

    @JsonIgnore
    private int httpStatusNum;

    public ServiceMessage() {
    }

    public ServiceMessage(String cd) {
        this.cd = cd;
    }

    public ServiceMessage(String cd, String description) {
        this.cd = cd;
        this.description = description;
    }

    public ServiceMessage(String cd, Type type, Level lvl, String text) {
        this.cd = cd;
        this.type = type;
        this.description = text;
        this.level = lvl;
    }

    public void setHttpStatusNum(int httpStatusNum) {
        this.httpStatusNum = httpStatusNum;
        this.setHttpStatus(HttpStatus.valueOf(httpStatusNum));
    }

    public String getLevelCd() {
        return level == null ? null : level.cd;
    }

    public void setLevelCd(String levelCd) {
        this.levelCd = levelCd;
        this.level = Level.getLevel(levelCd);
    }

    public String getTypeCd() {
        return type == null ? null : type.cd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
        this.type = Type.getType(typeCd);
    }

    public void setDesc(String desc,Object [] parms) {
        this.description = getMessageReplaceText(desc,parms);
    }

    public void setResponseCode(int responseCode) {
        this.httpStatus = HttpStatus.valueOf(responseCode);
    }

    /**
     * Formats message substituting parameters.
     *
     * Message text with {} markers can be replace with dynamic parameters.
     *
     * This method will replace {} markers with the specified parm values.
     *
     * @param text text with substitution markers
     * @param params parameters to be substituted in text.
     *
     * @return formatted message text
     */
    public static String getMessageReplaceText(String text, Object[] params) {

        text = text.replaceAll("\\{\\}", "%s");
        text = String.format(text, params);

        return text;
    }


    // Enums

    public enum Type {
        User("U"),
        System("S"),
        Notification("N");

        private String cd;

        Type(String cd) {
            this.cd = cd;
        }

        public static Type getType(String cd) {
            for (Type type : Type.values()) {
                if (type.cd.equals(cd)) {
                    return type;
                }
            }
            return null;
        }
    }

    public enum Level {
        Info("I"),
        Warn("W"),
        Error("E");

        private String cd;

        Level(String cd) {
            this.cd = cd;
        }

        public static Level getLevel(String cd) {
            for (Level lvl : Level.values()) {
                if (lvl.cd.equals(cd)) {
                    return lvl;
                }
            }
            return null;
        }
    }

    /** make a copy of this instance */
    public ServiceMessage copy() {
        ServiceMessage msg = new ServiceMessage();
        msg.setType(this.type);
        msg.setLevel(this.level);
        msg.setDescription(this.description);
        msg.setHttpStatus(this.httpStatus);
        msg.setCd(this.cd);
        return msg;
    }
}