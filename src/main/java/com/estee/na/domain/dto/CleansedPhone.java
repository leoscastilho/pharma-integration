package com.estee.na.domain.dto;

import com.estee.na.domain.reference.PhoneType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;

@Data
@Slf4j
@Entity
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CleansedPhone {
    private String cleansedPhoneNumber;
    private PhoneType phoneType;
    private boolean validInd;
}
