package com.application.remote.rlul.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/command")
public class RemoteController {

    @RequestMapping(value = "/{VIN}/{status}", method = RequestMethod.PUT)


}
