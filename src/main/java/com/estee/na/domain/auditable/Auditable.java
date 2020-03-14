package com.estee.na.domain.auditable;

import java.util.Date;

public interface Auditable {

    String getCreateUser();

    Date getCreateDt();

    String getUpdateUser();

    Date getUpdateDt();

    void setCreateUser(String user);

    void setCreateDt(Date date);

    void setUpdateUser(String user);

    void setUpdateDt(Date date);
}
