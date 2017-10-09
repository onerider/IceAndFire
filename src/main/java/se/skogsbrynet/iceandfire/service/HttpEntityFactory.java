package se.skogsbrynet.iceandfire.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

class HttpEntityFactory {
    private static HttpEntity httpEntity;

    static HttpEntity getHttpEntity(HttpHeaders headers) {
        return new HttpEntity<>("parameters", headers);
    }

    static HttpEntity getDefaultHttpEntity() {
        if (httpEntity == null) {
            HttpHeaders headers = new HttpHeaders();
            //noinspection SpellCheckingInspection
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            httpEntity = new HttpEntity<>("parameters", headers);
        }
        return httpEntity;
    }
}
