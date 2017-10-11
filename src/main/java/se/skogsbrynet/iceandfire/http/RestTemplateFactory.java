package se.skogsbrynet.iceandfire.http;

import org.springframework.web.client.RestTemplate;

public class RestTemplateFactory {

    private static final RestTemplate restTemplate = new RestTemplate();


    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }

}
