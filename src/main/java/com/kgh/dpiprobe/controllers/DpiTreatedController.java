/**
 * @author  Hamid, Kevin Gerard
 * @version 1.0
 * @since   07-02-2021
 */
package com.kgh.dpiprobe.controllers;

import com.kgh.dpiprobe.models.Dpitreatedsignals;
import com.kgh.dpiprobe.service.restapi.DpiTreatedDataService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/dpitreated")
public class DpiTreatedController {

    @Autowired
    DpiTreatedDataService dpiTreatedDataService;

    @CrossOrigin
    @GetMapping("/howto")
    @ApiOperation(value ="Displays a howto on howto do what you want to do")
    public ResponseEntity<String> getHowto() {

        try {
            StringBuffer howto = new StringBuffer("{title:How To Use This API,");

            howto.append("deviceoptions: ?filter=LTE&val1=10,");
            howto.append("deviceoptions: ?filter=GTE&val1=1,");
            howto.append("deviceoptions: ?filter=BETWEEN&val1=2&val2=8,");

            return new ResponseEntity<String>(howto.toString(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @GetMapping("/device/{deviceid}")
    @ApiOperation(value ="List Dpitreatedsignals for a device")
    public ResponseEntity<Dpitreatedsignals> getDpiSignals(
            @PathVariable("deviceid") Integer deviceId) {

        try {
            Optional<Dpitreatedsignals> dpitreatedsignals = dpiTreatedDataService.getOneDpitreatedsignals(deviceId);

            if (dpitreatedsignals.isPresent()) {
                return new ResponseEntity<>(dpitreatedsignals.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin
    @GetMapping("/clients/{clientid}")
    @ApiOperation(value ="List Dpitreatedsignals for a particular client number")
    public ResponseEntity<List<Dpitreatedsignals>> getAllDpiSignals(
            @PathVariable("clientid") Integer clientId,
            @RequestParam (required = false, name="filter") String filter,
            @RequestParam (required = false, name="val1") Integer val1,
            @RequestParam (required = false, name="val2") Integer val2
            ) {


        try {
            List<Dpitreatedsignals> dpitreatedsignals;

            Map<String,Object> options = new HashMap<String,Object>();
            if(filter == null || filter.isEmpty()) {

                dpitreatedsignals = dpiTreatedDataService.getDpitreatedsignalsForClient(clientId);
                if (dpitreatedsignals.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            } else {
                options.put("filter", filter);
                options.put("val1", val1);
                options.put("val2", val2);
                dpitreatedsignals = dpiTreatedDataService.getDpitreatedsignalsForClient(clientId, options);
                if (dpitreatedsignals.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
            return new ResponseEntity<>(dpitreatedsignals, HttpStatus.OK);



        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin
    @GetMapping("/clients/{clientid}/{officeid}")
    @ApiOperation(value ="List Dpitreatedsignals for a particular client number and an office number")
    public ResponseEntity<List<Dpitreatedsignals>> getAllDpiSignals(
            @PathVariable("clientid") Integer clientId,
            @PathVariable("officeid") Integer officeId,
            @RequestParam (required = false, name="filter") String filter,
            @RequestParam (required = false, name="val1") Integer val1,
            @RequestParam (required = false, name="val2") Integer val2
            ) {


        try {
            List<Dpitreatedsignals> dpitreatedsignals;

            Map<String,Object> options = new HashMap<String,Object>();
            if(filter == null || filter.isEmpty()) {

                dpitreatedsignals = dpiTreatedDataService.getDpitreatedsignalsForClient(clientId, officeId);
                if (dpitreatedsignals.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            } else {
                options.put("filter", filter);
                options.put("val1", val1);
                options.put("val2", val2);
                dpitreatedsignals = dpiTreatedDataService.getDpitreatedsignalsForClient(clientId, officeId, options);
                if (dpitreatedsignals.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
            return new ResponseEntity<>(dpitreatedsignals, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}