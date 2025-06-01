package com.smart_padel.spvending_management_api.security.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class BeagleVerificationController {

    @GetMapping("/_e3asta1yvhxfbheen12qw246pt70uuha")
    public Map<String, String> verifyBeagleDomain() {
        return Map.of("signature", "_pf1y54ppaqc872nvw736bofoqlxkraf4");
    }
}
