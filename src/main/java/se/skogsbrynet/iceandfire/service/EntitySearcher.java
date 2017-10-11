package se.skogsbrynet.iceandfire.service;

import java.util.List;

import se.skogsbrynet.iceandfire.model.Entity;


/**
 * Responsible for preparing requests to IceAnFire service
 * and creating threads for each page
 */
@SuppressWarnings("SpellCheckingInspection")
public abstract class EntitySearcher {

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

    abstract List<Entity> search(String nameToFind) throws RuntimeException;

    private static boolean isValid(String nameToFind) {
        return nameToFind != null && nameToFind.length() > 0;
    }

}