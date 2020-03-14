package com.estee.na.domain;

import com.estee.na.domain.auditable.Auditable;
import com.estee.na.domain.auditable.AuditableFields;
import com.estee.na.domain.reference.EnumTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;

@Data
@Slf4j
@Entity
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Table(name = "table_name")
@EntityListeners(AuditableFields.class)
public class BoilerplateTest implements Auditable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Convert(converter = EnumTest.Converter.class)
    @Column(name = "category_cd")
    private EnumTest categoryCd;
    @Column(name = "value")
    private String value;

    @Column(name = "create_user")
    String createUser;
    @Column(name = "create_dt")
    Date createDt;
    @Column(name = "update_user")
    String updateUser;
    @Column(name = "update_dt")
    Date updateDt;

    public BoilerplateTest(EnumTest enumTest, String value) {
        this.categoryCd = enumTest;
        this.value = value;
    }
}
