package com.application.remote.rlul.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Getter
@Setter
@Table(name = "RLUL_DATA")

public class RLULPojo {

    @Id
    @Column(name = "vin", unique = true, nullable = false)
    private String vin;
    @Column(name = "command")
    private String command;
    @Column(name = "status")
    private String statusMsg;
    @Column(name = "timestamp")
    private String timeStamp;

}
