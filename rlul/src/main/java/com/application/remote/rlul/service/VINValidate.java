package com.application.remote.rlul.service;

import lombok.extern.slf4j.Slf4j;
import com.application.remote.rlul.controller.model.VINValidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@Slf4j
public class VINValidate {
    @Autowired
    private TcuValidation tcuservice;
    public String isvalidVIN(String vin, String status) {
        log.info("Entered VINValidation");
        String output=null;
        Boolean isValidVIN=false;
        String update;
        try {
            String validateVinUrl= "http://localhost:9101/api/v1/isValidVehicle/"+vin;
            URL url = new URL(validateVinUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            log.info("Converting JSON to POJO \n");
            while ((output = br.readLine()) != null){
                ObjectMapper mapper = new ObjectMapper();
                VINValidateResponseDTO vinValidateResponseDTO = mapper.readValue(output, VINValidateResponseDTO.class);
                isValidVIN=vinValidateResponseDTO.getValid();
            }
            log.info("Disconnecting from the URL");
            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        if(isValidVIN) {
            log.info("VIN validated. Calling TCU Validation");
            update= tcuservice.validate(vin,status);
        }else {
            log.info("VIN is invalid, throwing error");
            throw new RuntimeException("VIN Invalid");
        }
        log.info("Returning from isValidFeature function");
        return update;
    }
}
