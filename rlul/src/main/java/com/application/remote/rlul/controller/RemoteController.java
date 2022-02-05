package com.application.remote.rlul.controller;


import com.application.remote.rlul.service.RLULFeatureValidateService;
import com.application.remote.rlul.service.VINValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@RestController
@Slf4j
@RequestMapping("/api/command")
public class RemoteController {

    @Autowired
    private VINValidate vinValidate;

    @RequestMapping(value = "/{VIN}/{Status}", method = RequestMethod.PUT)
    public ResponseEntity<Object> vehicleAPI(@PathVariable("VIN") String vin, @PathVariable("Status") String status) {
        String rlulResponse =  vinValidate.isvalidVIN(vin, status);
        return ResponseEntity.status(HttpStatus.OK).body(rlulResponse);
    }

    @RequestMapping(value="/{VIN}", method = RequestMethod.GET)
    public ResponseEntity<Object> getVehicleHistory(@PathVariable("VIN") String vin) {
        return ResponseEntity.status(HttpStatus.OK).body("test string");
    }

}
