package com.microserviced.currency_exchange;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    @Retry(name="sampleApi",fallbackMethod = "fallBackMethod")
    @GetMapping("/sample-api")
    public String sampleApi(){
        ResponseEntity<String> entity = new RestTemplate().getForEntity("https://localhost:8000/if",String.class);
        return entity.getBody();
    }

    public String fallBackMethod(Exception ex){
        return "fallback-method";
    }
}
