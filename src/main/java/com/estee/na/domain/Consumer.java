package com.estee.na.domain;

import com.estee.na.domain.auditable.Auditable;
import com.estee.na.domain.auditable.AuditableFields;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@AllArgsConstructor
@Table(name = "consumer")
@EntityListeners(AuditableFields.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Consumer implements Auditable {

    @Id
    @Column(name = "consumer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consumerId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "raw_email_address")
    private String rawEmailAddress;
    @Column(name = "cleansed_email_address")
    private String cleansed_email_address;
    @Column(name = "email_valid_ind")
    private Boolean emailValidInd;
    @Column(name = "raw_phone_number")
    private String rawPhoneNumber;
    @Column(name = "cleansed_phone_number")
    private String cleansedPhoneNumber;
    @Column(name = "phone_valid_ind")
    private Boolean phoneValidInd;

    @Column(name = "create_user")
    String createUser;
    @Column(name = "create_dt")
    Date createDt;
    @Column(name = "update_user")
    String updateUser;
    @Column(name = "update_dt")
    Date updateDt;

}
