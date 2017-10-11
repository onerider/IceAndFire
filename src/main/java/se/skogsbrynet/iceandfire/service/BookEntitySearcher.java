package se.skogsbrynet.iceandfire.service;

import se.skogsbrynet.iceandfire.http.BookUrlService;
import se.skogsbrynet.iceandfire.model.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BookEntitySearcher extends EntitySearcher {

    @Override
    List<Entity> search(String nameToFind) throws RuntimeException {

        List<Entity> entityResult = Collections.synchronizedList(new ArrayList<Entity>());

        BookUrlService urlService = new BookUrlService();
        int numberOfPages = urlService.getNumberOfPages();

        ExecutorService executor = Executors.newFixedThreadPool(numberOfPages);

        List<EntitySearchTask> tasks = new ArrayList<>();
        for (int i = 1; i <= numberOfPages; i++) {
            EntitySearchTask entitySearchTask = new BookEntitySearchTask(i, nameToFind);
            tasks.add(entitySearchTask);

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
}
