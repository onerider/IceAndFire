package se.skogsbrynet.iceandfire;

import se.skogsbrynet.iceandfire.model.Entity;
import se.skogsbrynet.iceandfire.service.CharacterEntitySearcher;
import se.skogsbrynet.iceandfire.service.EntitySearcher;

import java.util.List;

/**
 * Starting-point for IceAndFire
 */
class IceAndFireMain {
    public static void main(String[] args) {
        System.out.println("Hello, World -> IceAndFire!");

        EntitySearcher searcher = new CharacterEntitySearcher();
        List<Entity> result = searcher.performSearch(args[0]);
        System.out.println(result);
    }
}
