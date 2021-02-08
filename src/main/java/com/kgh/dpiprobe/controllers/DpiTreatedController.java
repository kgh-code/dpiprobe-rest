/**
 * <h1>xcelvie product order controller</h1>
 * <p>
 * REST controller to display, update, delete, add products
 * and orders for xcelvie.
 * </p>
 * @TODO View validation is not implemented, assume input is valid from the requesting client (IE all fields input as a minimum)
 * @TODO No security is implemented, assume upstream authenitication has been obtained
 * @TODO No logging is implemmented, e.printstacktrace is on for all exceptions
 * @TODO Implement a View Template mapping strategy before sending to service layer
 *
 * @author  Hamid, Kevin Gerard
 * @version 1.0
 * @since   14-09-2020
 */
package com.kgh.dpiprobe.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dpiprobe")
public class DpiTreatedController {

}