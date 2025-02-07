package org.peppermint.socialmedia.controller;

import lombok.RequiredArgsConstructor;
import org.peppermint.socialmedia.service.BaseRedisService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/redis")
@RequiredArgsConstructor
public class RedisController {
    private final BaseRedisService baseRedisService;

    @PostMapping
    public void set() {
        
    }
}
