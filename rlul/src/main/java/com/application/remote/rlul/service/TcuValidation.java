package com.application.remote.rlul.service;

import com.application.remote.rlul.service.impl.UpdateLockServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.sql.Types.NULL;
@Service
@Slf4j

public class TcuValidation {
    @Autowired
    UpdateLockServiceImpl localservice;
    public String validate(String vin,String status){
        log.info("entered TcuValidation ");
        String output=null;
        String response=null;
        try {

            URL url = new URL("http://localhost:9101/api/v1/isTCUEnabled/"+vin );
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            log.info("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                log.info(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        if (output != null && output.equalsIgnoreCase("y"))
        {
            log.info("calling rlul");
            response=localservice.updateLockStatus(vin, status);

        }
        else
        {
            throw new RuntimeException("Failed: rlul cant be called");
        }
        return response;




    }





}
