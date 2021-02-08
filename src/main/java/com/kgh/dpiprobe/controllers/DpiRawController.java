/**
 * @author  Hamid, Kevin Gerard
 * @version 1.0
 * @since   07-02-2021
 */
package com.kgh.dpiprobe.controllers;

import com.kgh.dpiprobe.models.Dpisignals;
import com.kgh.dpiprobe.service.restapi.DpiRawDataService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/dpiraw")
public class DpiRawController {

    @Autowired
    DpiRawDataService dpiRawDataService;


    @GetMapping("/device/{deviceid}")
    @ApiOperation(value ="List Dpisignals for a device")
    public ResponseEntity<Dpisignals> getDpiSignals(
            @PathVariable("deviceid") Integer deviceId,
            @RequestParam(required = false) Map<String, String> deviceOptions) {

        try {
            Optional<Dpisignals> dpisignals = dpiRawDataService.getOneDpisignals(deviceId);

            if (dpisignals.isPresent()) {
                return new ResponseEntity<>(dpisignals.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/customers/{customerid}")
    @ApiOperation(value ="List Dpisignals for a particular customer number")
    public ResponseEntity<List<Dpisignals>> getAllDpiSignals(
            @PathVariable("customerid") Integer customerId,
            @RequestParam(required = false) Map<String, String> deviceOptions) {


        try {
            List<Dpisignals> dpisignals;

            dpisignals = dpiRawDataService.getDpisignalsForCustomer(customerId);
            if (dpisignals.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(dpisignals, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/customers/{customerid}/{officeid}")
    @ApiOperation(value ="List Dpisignals for a particular customer number and an office number")
    public ResponseEntity<List<Dpisignals>> getAllDpiSignals(
            @PathVariable("customerid") Integer customerId,
            @PathVariable("officeid") Integer officeId,
            @RequestParam(required = false) Map<String, String> deviceOptions) {

        try {
            List<Dpisignals> dpisignals;

            dpisignals = dpiRawDataService.getDpisignalsForCustomer(customerId, officeId);
            if (dpisignals.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(dpisignals, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}