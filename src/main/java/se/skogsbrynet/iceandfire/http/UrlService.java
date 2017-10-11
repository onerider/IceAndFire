package se.skogsbrynet.iceandfire.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import se.skogsbrynet.iceandfire.model.Entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Responsible for constructing correct url for REST-call.
 */
public abstract class UrlService {

    /**
     * Number of pages for the entity.
     *
     * @return number of pages
     */
    public int getNumberOfPages() {
        String link = getHttpHeaderLink();
        String lastLink = getLastLinkFromHeaderLink(link);
        return getRelLastValue(lastLink);
    }

    private Integer getRelLastValue(String lastRel) {
        Pattern p = Pattern.compile("page=([0-9]+)");
        Matcher m = p.matcher(lastRel);

        if (!m.find()) {
            throw new RuntimeException("Error when parsing header: rel=last not found");
        }
        return new Integer(m.group(1));
    }

    private String getLastLinkFromHeaderLink(String link) {
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

    private String getHttpHeaderLink() {
        HttpEntity entity = HttpEntityFactory.getDefaultHttpEntity();

        ResponseEntity<Entity[]> responseEntity = RestTemplateFactory.getRestTemplate().exchange(getUrl(), HttpMethod.HEAD, entity, getResponseType());
        HttpHeaders headerResponse = responseEntity.getHeaders();
        return headerResponse.getFirst("Link");
    }

    abstract String getUrl();

    private Class<Entity[]> getResponseType() {
        return Entity[].class;
    }


}
