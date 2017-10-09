package se.skogsbrynet.iceandfire.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

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

        CharacterUrlService urlService = new CharacterUrlService();
        int numberOfPages = urlService.getNumberOfPages();

        ExecutorService executor = Executors.newFixedThreadPool(numberOfPages);

        List<CharacterTask> tasks = new ArrayList<>();
        for (int i = 1; i <= numberOfPages; i++) {
            CharacterTask characterTask = new CharacterTask(i, nameToFind);
            tasks.add(characterTask);

        }
        try {
            List<Future<List<Character>>> futures = executor.invokeAll(tasks);

            for (Future<List<Character>> f: futures
                 ) {
                charactersResult.addAll(f.get());
            }

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error when searching", e);
        }


        return charactersResult;

    }

    private static boolean isValid(String nameToFind) {
        return nameToFind != null && nameToFind.length() > 0;
    }

}