package com.smart_padel.spvending_management_api.security.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class BeagleVerificationController {

    @GetMapping("/_b2uhdu2wd2rv2b8qtoup8mv3ooyqj8lm")
    public Map<String, String> verifyBeagleDomain() {
        return Map.of("signature", "_tefcw9cvnit990sdvge65bcyeegejfz5");
    }
}
