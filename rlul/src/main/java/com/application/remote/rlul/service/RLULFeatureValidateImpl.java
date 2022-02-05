package com.application.remote.rlul.service;

import com.application.remote.rlul.controller.model.RLULFeatureValidateResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@Slf4j
public class RLULFeatureValidateImpl implements RLULFeatureValidateService{

    @Autowired
    private UpdateLockService updateLockService;

    @Override
    public String isValidFeature(String vin, String status){
        log.info("entered isValidFeature method");
        String update;
        String validateURL = "http://localhost:9101/api/v1/isValidFeature/"+vin+"/RLUL";
        String output=null;
        Boolean isValidRLUL = false;
        try {
            URL url = new URL(validateURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                log.error("failed to get response from the server");
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            log.info("Converting JSON output from the server to a POJO class");
            while ((output = br.readLine()) != null) {
                //System.out.println(output);
                ObjectMapper mapper = new ObjectMapper();
                RLULFeatureValidateResponseDTO rlulFeatureValidateResponseDTO = mapper.readValue(output, RLULFeatureValidateResponseDTO.class);
                isValidRLUL =  rlulFeatureValidateResponseDTO.getValid();
            }
            log.info("Disconnecting from the URL");
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(isValidRLUL) {
            log.info("RLUL validated. Calling updateDatabase function");
            update = updateLockService.updateLockStatus(vin, status);
        }else {
            log.info("RLUL feature absent, throwing error");
            throw new RuntimeException("RLUL feature absent, invalidate");
        }
        log.info("Returning from isValidFeature function");

        return update;
    }
}
