package com.application.remote.rlul.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/command")
public class RemoteController {

    @RequestMapping(value = "{VIN}/{status}", method = RequestMethod.PUT)
    public ResponseEntity<Object> vehicleAPI(@PathVariable("VIN") String VIN, @PathVariable("status") String status) {
        return ResponseEntity.status(HttpStatus.OK).body("Check API request");
    }

}
