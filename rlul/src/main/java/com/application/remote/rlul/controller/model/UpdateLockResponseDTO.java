package com.application.remote.rlul.controller.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateLockResponseDTO {
    private String vin;
    private String command;
    private int statusCode;
    private String statusMsg;
}
