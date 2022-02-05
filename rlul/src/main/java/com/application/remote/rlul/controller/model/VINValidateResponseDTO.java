package com.application.remote.rlul.controller.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VINValidateResponseDTO {
    private String vin;
    private Boolean valid;
}
