package com.application.remote.rlul.service.impl;

import com.application.remote.rlul.controller.model.UpdateLockResponseDTO;
import com.application.remote.rlul.data.entity.RLULPojo;
import com.application.remote.rlul.data.repository.UpdateLockStatusRepository;
import com.application.remote.rlul.service.UpdateLockService;
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
import java.sql.Date;

@Service
@Slf4j
public class UpdateLockServiceImpl implements UpdateLockService {

    @Autowired
    private UpdateLockStatusRepository updateLockStatusRepository;

    @Override
    public String updateLockStatus(String vin, String command) {

        log.info("entered update lock status method");
        String statusMessage = null;
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
                statusMessage = null != updateResponse ? updateResponse.getStatusMsg() : null;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //inserting value to the database

        RLULPojo rlulPojo = new RLULPojo();
        rlulPojo.setVin(vin);
        rlulPojo.setCommand(command);
        rlulPojo.setStatusMsg(statusMessage);
        rlulPojo.setTimeStamp(new Date(System.currentTimeMillis()).toString());

        updateLockStatusRepository.save(rlulPojo);

        return statusMessage;
    }
}
