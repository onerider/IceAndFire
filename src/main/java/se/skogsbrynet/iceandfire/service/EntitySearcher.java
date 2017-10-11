package se.skogsbrynet.iceandfire.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import se.skogsbrynet.iceandfire.http.CharacterUrlService;
import se.skogsbrynet.iceandfire.model.Entity;


/**
 * Responsible for preparing requests to IceAnFire service
 * and creating threads for each page
 */
@SuppressWarnings("SpellCheckingInspection")
public class EntitySearcher {

    /**
     * @param nameToFind The name to be searched for
     * @return List with matching entities
     */
    public List<Entity> performSearch(String nameToFind) {

        if (!isValid(nameToFind)) {
            throw new RuntimeException("Invalid name");
        }

        return search(nameToFind);
    }

    private List<Entity> search(String nameToFind) throws RuntimeException {

        List<Entity> entityResult = Collections.synchronizedList(new ArrayList<Entity>());

        CharacterUrlService urlService = new CharacterUrlService();
        int numberOfPages = urlService.getNumberOfPages();

        ExecutorService executor = Executors.newFixedThreadPool(numberOfPages);

        List<SearchTask> tasks = new ArrayList<>();
        for (int i = 1; i <= numberOfPages; i++) {
            SearchTask searchTask = new CharacterSearchTask(i, nameToFind);
            tasks.add(searchTask);

        }
        try {
            List<Future<List<Entity>>> futures = executor.invokeAll(tasks);

            for (Future<List<Entity>> f: futures
                 ) {
                entityResult.addAll(f.get());
            }

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error when searching", e);
        }


        return entityResult;

    }

    private static boolean isValid(String nameToFind) {
        return nameToFind != null && nameToFind.length() > 0;
    }

}