package se.skogsbrynet.iceandfire.tmp;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class SpringRestFactory {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static HttpEntity httpEntity;

    public static HttpEntity getHttpEntity() {
        if(httpEntity == null) {
            httpEntity = getDefaultHttpEntity();
        }
        return httpEntity;
    }
    private static HttpEntity<String> getDefaultHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        //noinspection SpellCheckingInspection
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        return new HttpEntity<>("parameters", headers);
    }

    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }

}
