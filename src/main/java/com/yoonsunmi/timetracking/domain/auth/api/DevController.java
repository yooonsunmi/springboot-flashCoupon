package com.yoonsunmi.timetracking.domain.auth.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Profile("local")
@RestController
@RequestMapping("/dev")
@RequiredArgsConstructor
public class DevController {
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/pw")
    public String pw(@RequestParam String raw) {
        return passwordEncoder.encode(raw);
    }
}
