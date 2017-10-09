package se.skogsbrynet.iceandfire;

import se.skogsbrynet.iceandfire.model.Character;
import se.skogsbrynet.iceandfire.service.CharacterSearcher;
import se.skogsbrynet.iceandfire.tmp.CharacterTask;

import java.util.List;

/**
 * Starting-point for IceAndFire
 */
public class IceAndFireMain {
    public static void main(String[] args) {
        System.out.println("Hello, World -> IceAndFire!");

        CharacterSearcher searcher = new CharacterSearcher();
        List<Character> result = searcher.performSearch(args[0]);
        System.out.println(result);
    }
}
