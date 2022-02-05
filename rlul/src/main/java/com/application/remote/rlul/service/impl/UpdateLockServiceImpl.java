package com.application.remote.rlul.service.impl;

import com.application.remote.rlul.controller.model.UpdateLockResponseDTO;
import com.application.remote.rlul.data.entity.RLULPojo;
import com.application.remote.rlul.service.UpdateLockService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Timestamp;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class UpdateLockServiceImpl implements UpdateLockService {

    @Override
    public String updateLockStatus(String vin, String command) {

        log.info("entered update lock status method");
        String update = null;
        UpdateLockResponseDTO updateResponse = null;

        String updateLockStatusUrl = "http://localhost:9101/api/v1/updateLockStatus/" + vin + "/" + command;

        try {
            URL url = new URL(updateLockStatusUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                ObjectMapper mapper = new ObjectMapper();
                updateResponse = mapper.readValue(output, UpdateLockResponseDTO.class);
                update = null != updateResponse ? updateResponse.getStatusMsg() : null;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //inserting value to the database

        return update;
    }
}
