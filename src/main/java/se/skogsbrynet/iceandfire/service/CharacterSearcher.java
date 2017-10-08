package se.skogsbrynet.iceandfire.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.skogsbrynet.iceandfire.model.Character;
import se.skogsbrynet.iceandfire.tmp.CharacterTask;
import se.skogsbrynet.iceandfire.tmp.IceAndFireThreadFactory;
import se.skogsbrynet.iceandfire.tmp.SpringRestFactory;


/**
 * Responsible for preparing requests to IceAnFire service
 * and creating threads for each page
 */
@SuppressWarnings("SpellCheckingInspection")
public class CharacterSearcher {
    private static final ArrayList<Thread> arrThreads = new ArrayList<>();

    /**
     * @param nameToFind The name to be searched for
     * @return List with matching characters
     */
    public static List<Character> performSearch(String nameToFind) {

        if (!isValid(nameToFind)) {
            throw new RuntimeException("Invalid name");
        }

        int numberOfPages = getNumberOfPages();

        // Start a separate thread for each page
        for (int i = 1; i <= numberOfPages; i++) {
            searchPage(nameToFind, i);
        }

        waitForThreadsToComplete();

        return CharacterTask.getCharactersResult();
    }

    private static void searchPage(String nameToFind, int i) {
        IceAndFireThreadFactory factory = new IceAndFireThreadFactory();
        CharacterTask characterTask = new CharacterTask(i, nameToFind);
        Thread thread = factory.newThread(characterTask);
        thread.start();
        arrThreads.add(thread);
    }

    private static void waitForThreadsToComplete() {
        for (Thread arrThread : arrThreads) {
            try {
                arrThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
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
        HttpEntity<String> entity = SpringRestFactory.getHttpEntity();

        ResponseEntity<java.lang.Character[]> responseEntity = SpringRestFactory.getRestTemplate().exchange("https://anapioficeandfire.com/api/characters?page=1&pageSize=50", HttpMethod.HEAD, entity, java.lang.Character[].class);
        HttpHeaders headerResponse = responseEntity.getHeaders();
        return headerResponse.getFirst("Link");
    }

    private static boolean isValid(String nameToFind) {
        return nameToFind != null && nameToFind.length() > 0;
    }

}