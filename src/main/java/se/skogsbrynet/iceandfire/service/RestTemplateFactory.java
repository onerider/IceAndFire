package se.skogsbrynet.iceandfire.service;

import org.springframework.web.client.RestTemplate;

class RestTemplateFactory {

    private static final RestTemplate restTemplate = new RestTemplate();


    static RestTemplate getRestTemplate() {
        return restTemplate;
    }

}
