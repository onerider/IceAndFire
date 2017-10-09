package se.skogsbrynet.iceandfire.tmp;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class RestTemplateFactory {

    private static final RestTemplate restTemplate = new RestTemplate();


    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }

}
