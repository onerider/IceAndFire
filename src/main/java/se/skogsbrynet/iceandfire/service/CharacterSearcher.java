package se.skogsbrynet.iceandfire.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.skogsbrynet.iceandfire.model.Character;


/**
 * Responsible for preparing requests to IceAnFire service
 * and creating threads for each page
 */
@SuppressWarnings("SpellCheckingInspection")
public class CharacterSearcher {

    /**
     * @param nameToFind The name to be searched for
     * @return List with matching characters
     */
    public List<Character> performSearch(String nameToFind) {

        if (!isValid(nameToFind)) {
            throw new RuntimeException("Invalid name");
        }

        return search(nameToFind);
    }

    private List<Character> search(String nameToFind) throws RuntimeException {

        List<Character> charactersResult = Collections.synchronizedList(new ArrayList<Character>());

        int numberOfPages = getNumberOfPages();

        ExecutorService executor = Executors.newFixedThreadPool(numberOfPages);

        List<CharacterTask> tasks = new ArrayList<>();
        for (int i = 1; i <= numberOfPages; i++) {
            CharacterTask characterTask = new CharacterTask(i, nameToFind);
            tasks.add(characterTask);

        }
        try {
            List<Future<List<Character>>> futures = executor.invokeAll(tasks);

            for (Future f: futures
                 ) {
                charactersResult.addAll((Collection<? extends Character>) f.get());
            }
            charactersResult.addAll(futures.get(0).get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error when searching", e);
        }


        return charactersResult;

    }

    private static int getNumberOfPages() {
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
        HttpEntity<String> entity = HttpEntityFactory.getDefaultHttpEntity();

        ResponseEntity<java.lang.Character[]> responseEntity = RestTemplateFactory.getRestTemplate().exchange("https://anapioficeandfire.com/api/characters?page=1&pageSize=50", HttpMethod.HEAD, entity, java.lang.Character[].class);
        HttpHeaders headerResponse = responseEntity.getHeaders();
        return headerResponse.getFirst("Link");
    }

    private static boolean isValid(String nameToFind) {
        return nameToFind != null && nameToFind.length() > 0;
    }

}