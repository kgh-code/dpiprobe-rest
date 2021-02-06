package com.kgh.dpiprobe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Stub controller, does nothing, for testing
 */
@Controller
public class StubController {
    @RequestMapping("/")
    public @ResponseBody
    String greeting() {
        return "Does Nothing";
    }
}
