package com.application.remote.rlul.controller.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@ToString
public class RLULFeatureValidateResponseDTO {
    //adsfsd
    private String vin;
    private String test;
    private Boolean valid;
    private String featureCode;
}
