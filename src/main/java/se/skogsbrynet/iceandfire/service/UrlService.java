package se.skogsbrynet.iceandfire.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlService {

    static int getNumberOfPages() {
        String link = getHttpHeaderLink();
        String lastLink = getLastLinkFromHeaderLink(link);
        return getRelLastValue(lastLink);
    }

    private static Integer getRelLastValue(String lastRel) {
        Pattern p = Pattern.compile("page=([0-9]+)");
        Matcher m = p.matcher(lastRel);

        if (!m.find()) {
            throw new RuntimeException("Error when parsing header: rel=last not found");
        }
        return new Integer(m.group(1));
    }

    private static String getLastLinkFromHeaderLink(String link) {
        String[] relLinks = link.split(",");
        String lastRel = null;
        for (String rel : relLinks) {
            if (rel.contains("rel=\"last\"")) {
                lastRel = rel;
            }
        }
        if (lastRel == null) {
            throw new RuntimeException("Error when parsing header: rel=last not found");
        }
        return lastRel;
    }

    private static String getHttpHeaderLink() {
        HttpEntity entity = HttpEntityFactory.getDefaultHttpEntity();

        ResponseEntity<Character[]> responseEntity = RestTemplateFactory.getRestTemplate().exchange("https://anapioficeandfire.com/api/characters?page=1&pageSize=50", HttpMethod.HEAD, entity, Character[].class);
        HttpHeaders headerResponse = responseEntity.getHeaders();
        return headerResponse.getFirst("Link");
    }
}
