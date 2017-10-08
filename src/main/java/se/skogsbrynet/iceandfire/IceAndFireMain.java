package se.skogsbrynet.iceandfire;

import se.skogsbrynet.iceandfire.model.Book;
import se.skogsbrynet.iceandfire.model.Character;
import se.skogsbrynet.iceandfire.model.House;
import se.skogsbrynet.iceandfire.service.CharacterSearcher;
import se.skogsbrynet.iceandfire.service.UrlService;
import se.skogsbrynet.iceandfire.tmp.CharacterTask;

/**
 * Starting-point for IceAndFire
 */
public class IceAndFireMain {
    public static void main(String[] args) {
        System.out.println("Hello, World -> IceAndFire!");

        CharacterSearcher.performSearch("Petyr Baelish");
        System.out.println(CharacterTask.getCharactersResult().get(0).getName());
    }
}
